package com.example.aseman.ManagerDaillyReports;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.aseman.ApiService.DailyReportApiService;
import com.example.aseman.Objects.ManagerDailyReport;
import com.example.aseman.PrefrenceManager;
import com.example.aseman.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ReportDialog extends Dialog implements
        View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button send, cancel;
    TextView text;
    public ReportDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_report_dialog);
        send = (Button) findViewById(R.id.btn_yes_daily_report);
        cancel = (Button) findViewById(R.id.btn_no_daily_report);
        text= (TextView) findViewById(R.id.txt_new_daily_report);
        send.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yes_daily_report:
                try {
                    sendReport(PrefrenceManager.getIstance(getContext()).getUserId(),text.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismiss();
                break;
            case R.id.btn_no_daily_report:
                dismiss();
                break;
        }
        dismiss();
    }

    private void sendReport(int userId, String text) throws JSONException {
        DailyReportApiService apiService=new DailyReportApiService(getContext());
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("text",text);
        jsonObject.put("answer"," ");
        jsonObject.put("report_date"," ");
        jsonObject.put("sender",userId);
        apiService.postReport(jsonObject);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
