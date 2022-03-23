package com.example.aseman.ApiService;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.aseman.Objects.Department;
import com.example.aseman.Objects.Report;
import com.example.aseman.Objects.Score;
import com.example.aseman.Objects.ScoringField;
import com.example.aseman.PrefrenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportApiService {
    private Context context;

    public ReportApiService(Context context) {
        this.context = context;
    }

    public void getReports(final ReportApiService.onReportRecived onReportRecived ){

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                "https://asemantile.ir/api/reports/", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        List<Report> reports = new ArrayList<>();

                        for (int i = 0; i<response.length(); i++){
                            Report report =new Report();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                report.setId(jsonObject.getInt("id"));
                                report.setAnalyzerId(jsonObject.getInt("analyzer"));
                                report.setUserId(jsonObject.getInt("user"));
                                report.setKeyWord(jsonObject.getString("key_word"));
                                report.setUrgentNeed(jsonObject.getString("urgent_need"));
                                reports.add(report);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        onReportRecived.onRecived(reports);
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


    public interface onReportRecived{
        void onRecived(List<Report> reports);
    }
    ////////////////////
    public void getScores(final ReportApiService.onScoreRecived onScoreRecived ){

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                "https://asemantile.ir/api/scores/", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        List<Score> scores = new ArrayList<>();

                        for (int i = 0; i<response.length(); i++){
                            Score score =new Score();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                score.setId(jsonObject.getInt("id"));
                                score.setPoint(jsonObject.getInt("score"));
                                score.setFieldId(jsonObject.getInt("field_name"));
                                score.setUserId(jsonObject.getInt("user"));
                                scores.add(score);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        onScoreRecived.onRecived(scores);
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


    public interface onScoreRecived{
        void onRecived(List<Score> scores);
    }


    //////////////////////fields
    public void getFields(final ReportApiService.onFieldRecived onFieldRecived ){

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                "https://asemantile.ir/api/fields/", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        List<ScoringField> scoringFields = new ArrayList<>();

                        for (int i = 0; i<response.length(); i++){
                            ScoringField scoringField =new ScoringField();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                scoringField.setId(jsonObject.getInt("id"));
                                scoringField.setFieldName(jsonObject.getString("field_name"));
                                scoringFields.add(scoringField);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        onFieldRecived.onRecived(scoringFields);
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


    public interface onFieldRecived{
        void onRecived(List<ScoringField> scoringFields);
    }
}
