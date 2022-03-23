package com.example.aseman.UnderManagmentUser;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aseman.ApiService.FieldApiService;
import com.example.aseman.ApiService.ProfileApiService;
import com.example.aseman.ApiService.ReportApiService;
import com.example.aseman.ApiService.ScoringReportApiService;
import com.example.aseman.Objects.Report;
import com.example.aseman.Objects.ScoringField;
import com.example.aseman.Objects.User;
import com.example.aseman.PrefrenceManager;
import com.example.aseman.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserScoringActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    Spinner spinnerUser;
    Spinner spinnerField;
    Context context;
    Button btnSetScore,btnSendScoringReport;
    EditText edtImportantNeed,edtKeyword;
    List<User> usersList;
    List<ScoringField> scoringFieldsList;
    EditText edtScoreNum;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_scoring);

        configureView();


        ////////
        usersList=new ArrayList<>();
        ArrayList<String> names=new ArrayList<>();
        ProfileApiService apiService=new ProfileApiService(UserScoringActivity.this);
        apiService.getAllProfiles(new ProfileApiService.onAllProfilesRecived() {
            @Override
            public void onRecived(List<User> users) {
                for(User user:users){
                    if(user.getDepartment().getId()==PrefrenceManager.getIstance(UserScoringActivity.this).getDepartmentId()){
                        names.add(user.getUsername());
                        usersList.add(user);
                    }

                }

                spinnerUser.setOnItemSelectedListener(UserScoringActivity.this);
                ArrayAdapter ad = new ArrayAdapter(UserScoringActivity.this, android.R.layout.simple_spinner_item, names);
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUser.setAdapter(ad);
            }
        });
        scoringFieldsList=new ArrayList<>();
        ArrayList<String> fieldsName=new ArrayList<>();
        FieldApiService fieldApiService=new FieldApiService(UserScoringActivity.this);
        fieldApiService.getAllFields(new FieldApiService.onAllFieldsRecived() {
            @Override
            public void onRecived(List<ScoringField> fields) {
                for(ScoringField field:fields){
                    fieldsName.add(field.getFieldName());
                    scoringFieldsList.add(field);
                }
                spinnerField.setOnItemSelectedListener(UserScoringActivity.this);
                ArrayAdapter ad2 = new ArrayAdapter(UserScoringActivity.this, android.R.layout.simple_spinner_item, fieldsName);
                ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerField.setAdapter(ad2);
            }
        });


        //send report
        btnSendScoringReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("key_word",edtKeyword.getText().toString());
                    jsonObject.put("urgent_need",edtImportantNeed.getText().toString());
                    jsonObject.put("check_by_manager",false);
                    jsonObject.put("user",usersList.get((int)spinnerUser.getSelectedItemId()).getId());
                    jsonObject.put("analyzer",PrefrenceManager.getIstance(UserScoringActivity.this).getUserId());
                    jsonObject.put("department", PrefrenceManager.getIstance(UserScoringActivity.this).getDepartmentId());
                    ScoringReportApiService apiService1=new ScoringReportApiService(UserScoringActivity.this);
                    apiService1.postScoringReport(jsonObject);

                    edtKeyword.setText("");
                    edtImportantNeed.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });





        //Set Score
        //in ghesmat bug darad !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        btnSetScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ScoringReportApiService scoringReportApiService=new ScoringReportApiService(UserScoringActivity.this);

                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("score",Integer.valueOf(edtScoreNum.getText().toString()));
                    jsonObject.put("field_name",scoringFieldsList.get((int)spinnerField.getSelectedItemId()).getId());
                    jsonObject.put("user",usersList.get((int)spinnerUser.getSelectedItemId()).getId());

                    scoringReportApiService.postScore(jsonObject);
                    edtScoreNum.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });










    }

    private void configureView() {
        spinnerUser=findViewById(R.id.spinner_scoring_user);
        spinnerField=findViewById(R.id.spinner_field_scoring_user);
        btnSetScore=findViewById(R.id.btn_set_score);
        edtImportantNeed=findViewById(R.id.edt_user_scoring_imortant_need);
        edtKeyword=findViewById(R.id.edt_user_scoring_keyword);
        btnSendScoringReport=findViewById(R.id.btn_send_scoring_report);
        edtScoreNum=findViewById(R.id.edt_score_num);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
