package com.example.aseman.UnderManagmentUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aseman.Adaptors.UnderMangementUserAdaptor;
import com.example.aseman.ApiService.ProfileApiService;
import com.example.aseman.Objects.City;
import com.example.aseman.Objects.Department;
import com.example.aseman.Objects.User;
import com.example.aseman.PrefrenceManager;
import com.example.aseman.R;
import com.example.aseman.Ticket.TicketActivity;

import java.util.ArrayList;
import java.util.List;

public class UnderManagementUserActivity extends AppCompatActivity {
    ArrayList<User> usersList;
    RecyclerView recyclerView;
    UnderMangementUserAdaptor adaptor;
    ImageButton btnUmuInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_management_user);

        configureView();

        btnUmuInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog=new AlertDialog.Builder(UnderManagementUserActivity.this);
                dialog.setCancelable(true);
                dialog.setTitle("بخش افراد تحت مدیریت ").setMessage("در اینجا لیست تمام افرادی که تحت مدیریت شما هستند نمایش داده میشود \n برای مشاهد جزییات عملکردی هر فرد روی آن ضربه بزنید \n ");
                dialog.setIcon(R.drawable.under_management_icon);
                dialog.show();
            }
        });

        usersList=new ArrayList<>();
        ProfileApiService apiService=new ProfileApiService(UnderManagementUserActivity.this);
        apiService.getAllProfiles(new ProfileApiService.onAllProfilesRecived() {
            @Override
            public void onRecived(List<User> users) {
                for(User user:users){
                    if(user.getDepartment().getId()== PrefrenceManager.getIstance(UnderManagementUserActivity.this).getDepartmentId() && user.getId()!=PrefrenceManager.getIstance(UnderManagementUserActivity.this).getUserId()){
                        usersList.add(user);
                    }
                }

                adaptor=new UnderMangementUserAdaptor(usersList,UnderManagementUserActivity.this);
                adaptor.onItemClickListener(new UnderMangementUserAdaptor.setOnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent=new Intent(UnderManagementUserActivity.this,UserDetailActivity.class);
                        intent.putExtra("userId",usersList.get(position).getId());
                        startActivity(intent);
                    }
                });

                LinearLayoutManager layoutManager = new LinearLayoutManager(UnderManagementUserActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adaptor);

            }
        });




    }

    private void configureView() {
        btnUmuInfo=findViewById(R.id.btn_under_management_info);
        recyclerView=findViewById(R.id.under_management_recyclerview);
    }
}
