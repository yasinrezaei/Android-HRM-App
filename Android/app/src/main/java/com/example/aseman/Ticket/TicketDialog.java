package com.example.aseman.Ticket;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aseman.ApiService.ProfileApiService;
import com.example.aseman.ApiService.TicketApiService;
import com.example.aseman.ApiService.UsersApiService;
import com.example.aseman.Objects.User;
import com.example.aseman.PrefrenceManager;
import com.example.aseman.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TicketDialog extends Dialog implements
        View.OnClickListener, AdapterView.OnItemSelectedListener {
    Activity activity;
    Button send, cancel;
    Spinner spinner,typeSpinner;
    EditText edtTicketTitle,edtTicketDescription;
    List<User> usersList;

    public TicketDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usersList=new ArrayList<>();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_ticket_dialog);
        edtTicketDescription=findViewById(R.id.edt_dialog_ticket_description);
        edtTicketTitle=findViewById(R.id.edt_dialog_ticket_title);
        send = (Button) findViewById(R.id.btn_yes);
        cancel = (Button) findViewById(R.id.btn_no);
        send.setOnClickListener(this);
        cancel.setOnClickListener(this);


        //////////spinner items////////////////
        spinner=findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayList<String> names=new ArrayList<>();
        ProfileApiService apiService=new ProfileApiService(getContext());
        apiService.getAllProfiles(new ProfileApiService.onAllProfilesRecived() {
            @Override
            public void onRecived(List<User> users) {
                usersList=users;
                for(int i=0;i<users.size();i++){
                    names.add(users.get(i).getUsername());
                }
                ArrayAdapter ad = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, names);
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(ad);
            }
        });

        ///type spinner
        typeSpinner=findViewById(R.id.spinner_type_need);
        ArrayList<String> types=new ArrayList<>();
        types.add("عادی");
        types.add("مهم");
        types.add("فوری");
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        ////////////////////////////////////////////




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                try {
                    sendTicket((int)spinner.getSelectedItemId(),typeSpinner.getSelectedItemId(),edtTicketTitle.getText().toString(),edtTicketDescription.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dismiss();
                break;
            case R.id.btn_no:
                dismiss();
                break;
        }
        dismiss();
    }

    private void sendTicket(int selectedItemId, long itemId, String title, String text) throws JSONException {
        String type;
        if(itemId==0){
            type="Normal";
        }
        else if(itemId==1){
            type="Important";
        }
        else{
            type="Urgent";
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("title",title);
        jsonObject.put("text",text);
        jsonObject.put("answer"," ");
        jsonObject.put("reciever",usersList.get(selectedItemId).getId());
        jsonObject.put("sender", PrefrenceManager.getIstance(getContext()).getUserId());
        jsonObject.put("tag",type);
        jsonObject.put("check_by_reciever",false);
        TicketApiService apiService1=new TicketApiService(getContext());
        apiService1.postTicket(jsonObject);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
