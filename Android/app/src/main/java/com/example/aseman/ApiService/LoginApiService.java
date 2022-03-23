package com.example.aseman.ApiService;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aseman.PrefrenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginApiService {
    private Context context;

    public LoginApiService(Context context) {
        this.context = context;
    }

    public void getToken(final JSONObject jsonObject, final onTokenRecived onTokenRecived) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://asemantile.ir/api/api-token-auth/", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onTokenRecived.onTokenRecived(true);
                        try {
                            String Stoken = response.getString("token");
                            PrefrenceManager.getIstance(context).setToken(Stoken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onTokenRecived.onTokenRecived(false);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void getUserId(final getUserInfo getUserInfo) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "https://asemantile.ir/api/get-user?username=" + PrefrenceManager.getIstance(context).getUsername(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PrefrenceManager.getIstance(context).setUserId(response.getInt("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getUserInfo.onUserInfoRecived(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                getUserInfo.onUserInfoRecived(false);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "token " + PrefrenceManager.getIstance(context).getToken());
                return headers;
            }
        };


        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }



    public interface onTokenRecived {
        void onTokenRecived(boolean success);
    }

    public interface getUserInfo {
        void onUserInfoRecived(boolean success);
    }

}
