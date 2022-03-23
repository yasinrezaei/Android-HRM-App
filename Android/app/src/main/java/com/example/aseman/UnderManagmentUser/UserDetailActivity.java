package com.example.aseman.UnderManagmentUser;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aseman.Adaptors.UserImportantNeedAdaptor;
import com.example.aseman.ApiService.DepartmentApiService;
import com.example.aseman.ApiService.FieldApiService;
import com.example.aseman.ApiService.ProfileApiService;
import com.example.aseman.ApiService.ReportApiService;
import com.example.aseman.ApiService.ScoringReportApiService;
import com.example.aseman.Objects.Department;
import com.example.aseman.Objects.Need;
import com.example.aseman.Objects.Report;
import com.example.aseman.Objects.Score;
import com.example.aseman.Objects.ScoringField;
import com.example.aseman.Objects.User;
import com.example.aseman.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity {
    BarChart barChart;
    ArrayList<Need> needs;
    RecyclerView recyclerView;
    UserImportantNeedAdaptor adaptor;
    TextView userName,depName;
    int userId,depId;
    List<Report> reportsList;
    List<Score> scoresList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId=extras.getInt("userId");
        }
        configureView();
        reportsList=new ArrayList<>();
        scoresList=new ArrayList<>();



        //profile

        ProfileApiService apiService=new ProfileApiService(UserDetailActivity.this);
        apiService.getAllProfiles(new ProfileApiService.onAllProfilesRecived() {
            @Override
            public void onRecived(List<User> users) {
                for(User user:users){
                    if(user.getId()==userId){
                        depId=user.getDepartment().getId();
                        userName.setText(user.getUsername());
                        break;
                    }
                }


            }
        });
        //departments
        DepartmentApiService api=new DepartmentApiService(UserDetailActivity.this);
        api.getAllDepartments(new DepartmentApiService.onAllDepartmentsRecived() {
            @Override
            public void onRecived(List<Department> departments) {
                for(Department department:departments){
                    if(department.getId()==depId){
                        depName.setText(department.getName());
                        break;
                    }
                }
            }
        });


        //////////////bar chart/////
        getScores();

        //needs
        ReportApiService apiService1=new ReportApiService(UserDetailActivity.this);
        apiService1.getReports(new ReportApiService.onReportRecived() {
            @Override
            public void onRecived(List<Report> reports) {
                needs=new ArrayList<>();
                for(Report report:reports){
                    if(report.getUserId()==userId){
                        needs.add(new Need(report.getId(),report.getUrgentNeed()));
                    }
                }
                adaptor=new UserImportantNeedAdaptor(needs,UserDetailActivity.this);
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(UserDetailActivity.this, LinearLayoutManager.VERTICAL, true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adaptor);
            }
        });




    }


    private void getScores(){
        ScoringReportApiService apiService=new ScoringReportApiService(UserDetailActivity.this);
        apiService.getAllScores(new ScoringReportApiService.onAllScoresRecived() {
            @Override
            public void onRecived(List<Score> scores) {
                for(Score score:scores){
                        if(score.getUserId()==userId){
                            scoresList.add(score);
                        }
                }
                if(scoresList.size()!=0){
                    getFields();
                }

            }
        });
    }
    private void getFields(){

        FieldApiService fieldApiService=new FieldApiService(UserDetailActivity.this);
        fieldApiService.getAllFields(new FieldApiService.onAllFieldsRecived() {
            @Override
            public void onRecived(List<ScoringField> fields) {

                for(ScoringField field:fields){
                    int sum=0;
                    int count=0;
                    for(int i=0;i<scoresList.size();i++){
                        if(scoresList.get(i).getFieldId()==field.getId()){
                            sum+=scoresList.get(i).getPoint();
                            count++;
                        }
                    }
                    if(count!=0){
                        barChart.addBar(new BarModel(field.getFieldName(),sum/count, 0xFF123456));
                    }

                }
                barChart.startAnimation();


            }
        });
    }


    private void configureView() {
        barChart=findViewById(R.id.barchart_user);
        recyclerView=findViewById(R.id.user_important_need_recycler);
        userName=findViewById(R.id.user_detail_username);
        depName=findViewById(R.id.user_detail_department);


    }
}
