package com.example.aseman.Ticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aseman.ApiService.ProfileApiService;
import com.example.aseman.ApiService.TicketApiService;
import com.example.aseman.Objects.User;
import com.example.aseman.PrefrenceManager;
import com.example.aseman.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TicketDetailActivity extends AppCompatActivity {
    TextView txtTitle,txtText,txtSender,isCheck;
    EditText edtAnswer;
    Button btnDoneTicket;
    int id,senderId;
    String title,text,answer,sender;
    boolean isCheckedByReciever;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        configureView();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id=extras.getInt("id");
            title = extras.getString("title");
            text = extras.getString("text");
            senderId = extras.getInt("sender_id");
            answer=extras.getString("answer");
            isCheckedByReciever =extras.getBoolean("check_by_reciever");
            //The key argument here must match that used in the other activity
        }
        ProfileApiService apiService=new ProfileApiService(TicketDetailActivity.this);
        apiService.getAllProfiles(new ProfileApiService.onAllProfilesRecived() {
            @Override
            public void onRecived(List<User> users) {
                for(User user:users){
                    if(user.getId()==senderId){
                        sender =user.getUsername();
                        break;
                    }
                }
                txtSender.setText(sender);
                txtTitle.setText(title);
                txtText.setText(text);
                edtAnswer.setText(answer);

                if(isCheckedByReciever) isCheck.setText("این فعالیت انجام شده است");
                else isCheck.setText("این فعالیت هنوز انجام نشده است");
            }
        });

        btnDoneTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("answer",edtAnswer.getText().toString());
                    jsonObject.put("check_by_reciever",true);
                    jsonObject.put("title",title);
                    jsonObject.put("text",text);
                    jsonObject.put("sender",senderId);
                    jsonObject.put("reciever", PrefrenceManager.getIstance(TicketDetailActivity.this).getUserId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TicketApiService apiService1=new TicketApiService(TicketDetailActivity.this);
                apiService1.updateTicket(id,jsonObject);
                Intent intent=new Intent(TicketDetailActivity.this,TicketActivity.class);
                startActivity(intent);

            }
        });







    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void configureView() {
        txtSender=findViewById(R.id.txt_ticket_sender);
        txtText=findViewById(R.id.txt_ticket_text);
        txtTitle=findViewById(R.id.txt_ticket_title);
        edtAnswer=findViewById(R.id.edt_ticket_answer);
        btnDoneTicket=findViewById(R.id.btn_done_ticket);
        isCheck=findViewById(R.id.txt_is_check);
    }
}
