package com.example.aseman.Ticket;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aseman.Adaptors.TicketAdaptor;
import com.example.aseman.ApiService.ProfileApiService;
import com.example.aseman.ApiService.TicketApiService;
import com.example.aseman.MainActivity;
import com.example.aseman.Objects.Ticket;
import com.example.aseman.Objects.User;
import com.example.aseman.PrefrenceManager;
import com.example.aseman.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketActivity extends AppCompatActivity {
    ArrayList<Ticket> ticketsList;
    RecyclerView recyclerView;
    TicketAdaptor adaptor;
    ImageButton btnTicketInfo;
    Spinner spinner;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);


        configureView();

        btnTicketInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(TicketActivity.this);
                dialog.setCancelable(true);
                dialog.setTitle("بخش درخواست ها").setMessage("در اینجا لیست تمام درخواست هایی که برای شما ثبت شده است نمایش داده میشود \n برای مشاهد جزییات هر درخواست روی آن ضربه بزنید \n برای حذف هر درخواست آن را به سمت راست بکشید");
                dialog.setIcon(R.drawable.important_notif);
                dialog.show();
            }
        });

        recyclerConfig();


        ////////////spinner////////
        ArrayList<String> items=new ArrayList<>();
        items.add("انجام نشده");
        items.add("انجام شده");
        items.add("محول شده");

        ArrayAdapter adapter = new ArrayAdapter(TicketActivity.this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                recyclerConfig();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private void recyclerConfig() {
        TicketApiService ticketApiService=new TicketApiService(TicketActivity.this);
        ticketApiService.getAllTickets(new TicketApiService.onAllTicketsRecived() {
            @Override
            public void onRecived(List<Ticket> tickets) {
                ticketsList=new ArrayList<>();
                if((int)spinner.getSelectedItemId()==0){
                    for(Ticket ticket:tickets){
                        if(ticket.getReciever_id()== PrefrenceManager.getIstance(TicketActivity.this).getUserId() && ticket.getSender_id()!=PrefrenceManager.getIstance(TicketActivity.this).getUserId() && ticket.isCheckByReciever()==false){
                            ticketsList.add(ticket);
                        }
                    }
                }
                else if((int)spinner.getSelectedItemId()==1){
                    for(Ticket ticket:tickets){
                        if(ticket.isCheckByReciever()){
                            ticketsList.add(ticket);
                        }
                    }
                }
                else if((int)spinner.getSelectedItemId()==2){
                    for(Ticket ticket:tickets){
                        if(ticket.getSender_id()== PrefrenceManager.getIstance(TicketActivity.this).getUserId()){
                            ticketsList.add(ticket);
                        }
                    }
                }


                adaptor=new TicketAdaptor(ticketsList,TicketActivity.this);
                adaptor.onItemClickListener(new TicketAdaptor.setOnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent=new Intent(TicketActivity.this,TicketDetailActivity.class);
                        intent.putExtra("title",ticketsList.get(position).getTitle());
                        intent.putExtra("id",ticketsList.get(position).getId());
                        intent.putExtra("text",ticketsList.get(position).getText());
                        intent.putExtra("sender_id",ticketsList.get(position).getSender_id());
                        intent.putExtra("answer",ticketsList.get(position).getAnswer());
                        intent.putExtra("check_by_reciever",ticketsList.get(position).isCheckByReciever());
                        startActivity(intent);

                    }
                });

                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(TicketActivity.this, LinearLayoutManager.VERTICAL, true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adaptor);
                ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
                itemTouchHelper.attachToRecyclerView(recyclerView);
            }
        });
    }


    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        //move items
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        //swiped item
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            //delete this ticket
            ticketsList.remove(viewHolder.getAdapterPosition());
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    };

    private void configureView() {
        btnTicketInfo=findViewById(R.id.btn_tickets_info);
        recyclerView=findViewById(R.id.tickets_recyclerview);
        spinner=findViewById(R.id.spinner_tickets);
    }


}
