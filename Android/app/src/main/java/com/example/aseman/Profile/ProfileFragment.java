package com.example.aseman.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aseman.ApiService.ProfileApiService;
import com.example.aseman.ApiService.ReportApiService;
import com.example.aseman.ApiService.UsersApiService;
import com.example.aseman.MainActivity;
import com.example.aseman.Objects.Department;
import com.example.aseman.Objects.Report;
import com.example.aseman.Objects.Score;
import com.example.aseman.Objects.ScoringField;
import com.example.aseman.Objects.User;
import com.example.aseman.PrefrenceManager;
import com.example.aseman.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    BarChart barChart;
    TextView fullName,departmentName,countUmu,txtDetail,txtStatus;
    List<User> allUsers;
    List<Report> allReports;
    List<Score> allScores;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.profile_fragment,container,false);
        configureView(view);
        //first set
        fullName.setText(PrefrenceManager.getIstance(getContext()).getFullName());
        departmentName.setText(PrefrenceManager.getIstance(getContext()).getDepartmentName());


        allUsers=new ArrayList<>();
        allReports=new ArrayList<>();
        allScores=new ArrayList<>();




        //barChart
        barChart=view.findViewById(R.id.barchart);
        getAllUser();




        return view;
    }


    private void configureView(View view) {
        getUserInformation();
        fullName=view.findViewById(R.id.txt_profile_fullname);
        departmentName=view.findViewById(R.id.txt_profile_department_name);
        countUmu=view.findViewById(R.id.count_umu);
        txtDetail=view.findViewById(R.id.txt_detail);
        txtStatus=view.findViewById(R.id.txt_status);
    }
    private void getAllUser(){
        UsersApiService apiService=new UsersApiService(getContext());
        apiService.getAllUsers(new UsersApiService.onAllUsersRecived() {
            @Override
            public void onRecived(List<User> users) {
                if(PrefrenceManager.getIstance(getContext()).is_main_manager()) {
                    countUmu.setText(users.size()-1 +" نفر تحت مدیریت شما هستند");
                    txtDetail.setText("جزئیات واحد های تحت مدیریت");
                    txtStatus.setText("وضعیت کلی افراد تحت مدیریت شما");
                    allUsers=users;
                }
                else if(PrefrenceManager.getIstance(getContext()).is_manager()){
                    int count=0;
                    for(User user:users){
                        if(user.getDepartment().getId()==PrefrenceManager.getIstance(getContext()).getDepartmentId()){
                            count++;
                            allUsers.add(user);
                        }

                    }
                    countUmu.setText(count-1 +" نفر تحت مدیریت شما هستند");
                    txtDetail.setText("جزئیات واحد های تحت مدیریت");
                    txtStatus.setText("وضعیت کلی افراد تحت مدیریت شما");
                    allUsers=users;
                }
                else{

                    countUmu.setText(0 +" نفر تحت مدیریت شما هستند");
                    txtDetail.setText("جزئیات واحد های تحت مدیریت");
                    txtStatus.setText("وضعیت کلی افراد تحت مدیریت شما");
                }


                //modify barChart
                if(allUsers.size()!=0){
                    getallScores();
                }


            }
        });
    }
    private void getallScores(){
        ReportApiService apiService=new ReportApiService(getContext());
        apiService.getScores(new ReportApiService.onScoreRecived() {
            @Override
            public void onRecived(List<Score> scores) {
                for(Score score:scores){
                    for(User user:allUsers){
                        if(user.getId()==score.getUserId()){
                            allScores.add(score);
                        }
                    }
                }
                if(allUsers.size()!=0){
                    getAllFields();
                }

            }
        });

    }
    private void getAllFields(){
        ReportApiService apiService=new ReportApiService(getContext());
        apiService.getFields(new ReportApiService.onFieldRecived() {
            @Override
            public void onRecived(List<ScoringField> scoringFields) {

                for(ScoringField scoringField:scoringFields){
                    int sum=0;
                    int count=0;
                    for(Score score:allScores){
                        if(score.getFieldId()==scoringField.getId()){
                            sum+=score.getPoint();
                            count++;
                        }

                    }
                    if(count!=0){
                        barChart.addBar(new BarModel(scoringField.getFieldName(),sum/count, 0xFF123456));
                    }




                }

                barChart.startAnimation();

            }
        });
    }

    private void getUserInformation(){
        ProfileApiService apiService=new ProfileApiService(getContext());
        apiService.getProfile(new ProfileApiService.getProfile() {
            @Override
            public void onUserInfoRecived(boolean success) {
                if(success){
                    getDepartementInfo();
                }
                else{
                    Toast.makeText(getContext(), "خطا در دریافت اطلاعات از سرور!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void getDepartementInfo(){
        ProfileApiService apiService=new ProfileApiService(getContext());
        apiService.getDepartment(new ProfileApiService.onDepartmentRecived() {
            @Override
            public void onRecived(List<Department> departments) {
                for (Department department:departments){
                    if(department.getId()==PrefrenceManager.getIstance(getContext()).getDepartmentId()){
                        PrefrenceManager.getIstance(getContext()).setDepartmentName(department.getName());
                        departmentName.setText(PrefrenceManager.getIstance(getContext()).getDepartmentName());
                        fullName.setText(PrefrenceManager.getIstance(getContext()).getFullName());
                    }
                }
            }
        });
    }
}
