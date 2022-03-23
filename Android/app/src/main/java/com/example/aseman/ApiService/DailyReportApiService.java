package com.example.aseman.ApiService;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aseman.Objects.City;
import com.example.aseman.Objects.Department;
import com.example.aseman.Objects.ManagerDailyReport;
import com.example.aseman.Objects.User;
import com.example.aseman.PrefrenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyReportApiService {
    private Context context;

    public DailyReportApiService(Context context) {
        this.context = context;
    }


    public void getAllDailyReports(final DailyReportApiService.onDailyReportRecived onDailyReportRecived ){

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                "https://asemantile.ir/api/manger-daily-reports/", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        List<ManagerDailyReport> managerDailyReports = new ArrayList<>();

                        for (int i = 0; i<response.length(); i++){
                            ManagerDailyReport managerDailyReport =new ManagerDailyReport();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                managerDailyReport.setId(jsonObject.getInt("id"));
                                managerDailyReport.setText(jsonObject.getString("text"));
                                managerDailyReport.setAnswer(jsonObject.getString("answer"));
                                managerDailyReport.setDate(jsonObject.getString("report_date"));
                                managerDailyReport.setSender_id(jsonObject.getInt("sender"));

                                managerDailyReports.add(managerDailyReport);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                       onDailyReportRecived.onRecived(managerDailyReports);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getUserGroups", "error: "+error);

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "token " + PrefrenceManager.getIstance(context).getToken());
                return headers;
            }};

        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }

    public interface onDailyReportRecived{
        void onRecived(List<ManagerDailyReport> managerDailyReports);
    }










    /////////post///////////////

    public void postReport(final JSONObject jsonObject){
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://asemantile.ir/api/manger-daily-reports/", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context,"گزارش با موفقیت ارسال شد",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("report", "onErrorResponse: "+error );
                Toast.makeText(context," گزارش ارسال نشد!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "token " + PrefrenceManager.getIstance(context).getToken());
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
