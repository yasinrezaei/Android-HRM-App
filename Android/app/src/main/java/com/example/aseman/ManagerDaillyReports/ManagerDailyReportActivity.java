package com.example.aseman.ManagerDaillyReports;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aseman.Adaptors.MDailyReportAdaptor;
import com.example.aseman.ApiService.DailyReportApiService;
import com.example.aseman.Objects.ManagerDailyReport;
import com.example.aseman.PrefrenceManager;
import com.example.aseman.R;

import java.util.ArrayList;
import java.util.List;

public class ManagerDailyReportActivity extends AppCompatActivity {
    ArrayList<ManagerDailyReport> managerDailyReportsList;
    RecyclerView recyclerView;
    MDailyReportAdaptor adaptor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_daily_report);
        managerDailyReportsList=new ArrayList<>();
        configureView();


        //get reports
        DailyReportApiService apiService=new DailyReportApiService(ManagerDailyReportActivity.this);
        apiService.getAllDailyReports(new DailyReportApiService.onDailyReportRecived() {
            @Override
            public void onRecived(List<ManagerDailyReport> managerDailyReports) {
                for(ManagerDailyReport managerDailyReport:managerDailyReports){
                    if(managerDailyReport.getSender_id()== PrefrenceManager.getIstance(ManagerDailyReportActivity.this).getUserId()){
                        managerDailyReportsList.add(managerDailyReport);
                    }
                }
                configRecyclerView();
            }
        });




    }

    private void configureView() {
        recyclerView=findViewById(R.id.manager_daily_report_recycler);
    }
    private void configRecyclerView(){
        adaptor=new MDailyReportAdaptor(managerDailyReportsList,this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptor);
    }
}
