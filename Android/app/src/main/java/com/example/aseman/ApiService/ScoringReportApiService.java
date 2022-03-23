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
import com.example.aseman.Objects.Score;
import com.example.aseman.Objects.ScoringField;
import com.example.aseman.Objects.User;
import com.example.aseman.PrefrenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoringReportApiService {
    private Context context;

    public ScoringReportApiService(Context context) {
        this.context = context;
    }




    ////////////get all scores//////
    public void getAllScores(final ScoringReportApiService.onAllScoresRecived onAllScoresRecived ){

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                "https://asemantile.ir/api/scores", null,
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
                                score.setUserId(jsonObject.getInt("user"));
                                score.setFieldId(jsonObject.getInt("field_name"));
                                scores.add(score);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        onAllScoresRecived.onRecived(scores);
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

    public interface onAllScoresRecived{
        void onRecived(List<Score> scores);
    }









    /////post score /////////

    public void postScore(final JSONObject jsonObject){
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://asemantile.ir/api/scores/", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context,"امتیاز با موفقیت ثبت شد",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("report", "onErrorResponse: "+error );
                Toast.makeText(context," امتیاز ثبت نشد!",Toast.LENGTH_SHORT).show();
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
    /////////post scorinf report///////////////

    public void postScoringReport(final JSONObject jsonObject){
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://asemantile.ir/api/reports/", jsonObject,
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
