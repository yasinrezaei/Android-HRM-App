package com.example.aseman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aseman.ApiService.ProfileApiService;
import com.example.aseman.Authentication.LoginActivity;
import com.example.aseman.ManagerDaillyReports.ManagerDailyReportActivity;
import com.example.aseman.ManagerDaillyReports.ReportDialog;
import com.example.aseman.Objects.Department;
import com.example.aseman.Profile.ProfileFragment;
import com.example.aseman.Ticket.TicketActivity;
import com.example.aseman.Ticket.TicketDialog;
import com.example.aseman.UnderManagmentUser.UnderManagementUserActivity;
import com.example.aseman.UnderManagmentUser.UserScoringActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button btnNewTicket;
    ExtendedFloatingActionButton fabNewReport,fabNewScoring;
    private PrefrenceManager prefrenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefrenceManager=PrefrenceManager.getIstance(MainActivity.this);



        configureView();



        getSupportFragmentManager().beginTransaction().add(R.id.container,new ProfileFragment(),"ProfileFragment").commit();


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        //new ticket
        btnNewTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicketDialog ticketDialog = new TicketDialog(MainActivity.this);
                ticketDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ticketDialog.show();
            }
        });


        //new Report
        fabNewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportDialog reportDialog=new ReportDialog(MainActivity.this);
                reportDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                reportDialog.show();
            }
        });

        //new Scoring
        fabNewScoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, UserScoringActivity.class);
                startActivity(intent);
            }
        });







    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(navigationView)){
            drawerLayout.closeDrawer(navigationView);
        }
        else{
            super.onBackPressed();
        }
    }



    private void configureView() {
        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);
        btnNewTicket=findViewById(R.id.btn_new_ticket);
        fabNewReport=findViewById(R.id.fab_new_daily_report);
        fabNewScoring=findViewById(R.id.fab);
    }



    // select navigation item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.tickets_item:
                drawerLayout.closeDrawer(navigationView);
                Intent intent=new Intent(MainActivity.this,TicketActivity.class);
                startActivity(intent);
                break;
            case R.id.manager_daily_reports_item:
                drawerLayout.closeDrawer(navigationView);
                Intent intent1=new Intent(MainActivity.this,ManagerDailyReportActivity.class);
                startActivity(intent1);
                break;
            case R.id.under_management_users_item:
                drawerLayout.closeDrawer(navigationView);
                Intent intent2=new Intent(MainActivity.this,UnderManagementUserActivity.class);
                startActivity(intent2);
                break;
            case R.id.under_management_manager_reports_item:
                Toast.makeText(MainActivity.this, "در آپدیت های بعدی ... ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.system_status_item:
                Toast.makeText(MainActivity.this, "در آپدیت های بعدی ...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.exit_item:
                prefrenceManager.clearSharedPref();
                Intent intent3=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent3);
                finish();
                break;
        }


        return true;
    }
}