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

public class ProfileApiService {
    private Context context;
    private PrefrenceManager prefrenceManager;
    public ProfileApiService(Context context) {
        this.context = context;
    }

    //////////////get all profiles/////////////////////////
    public void getAllProfiles(final ProfileApiService.onAllProfilesRecived onAllProfilesRecived ){

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                "https://asemantile.ir/api/profiles", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        List<User> users = new ArrayList<>();

                        for (int i = 0; i<response.length(); i++){
                            User user =new User();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                user.setId(jsonObject.getInt("id"));
                                user.setUsername(jsonObject.getString("first_name")+" "+jsonObject.getString("last_name"));
                                user.setManager(jsonObject.getBoolean("is_manager"));
                                user.setMainManager(jsonObject.getBoolean("is_main_manager"));
                                user.setCity(new City(jsonObject.getInt("city"),""));
                                user.setDepartment(new Department(jsonObject.getInt("department"),""));
                                users.add(user);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        onAllProfilesRecived.onRecived(users);
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

    public interface onAllProfilesRecived{
        void onRecived(List<User> users);
    }



    /////////////////////////////for own user//////////////////////////////
    public void getProfile(final ProfileApiService.getProfile getUserInfo) {

        prefrenceManager=PrefrenceManager.getIstance(context);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "https://asemantile.ir/api/get-profile?user_id="+prefrenceManager.getUserId(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //Toast.makeText(context, "data"+response, Toast.LENGTH_SHORT).show();
                            PrefrenceManager.getIstance(context).set_manager(response.getBoolean("is_manager"));
                            PrefrenceManager.getIstance(context).setFullName(response.getString("first_name")+" "+response.getString("last_name"));
                            PrefrenceManager.getIstance(context).set_main_manager(response.getBoolean("is_main_manager"));
                            PrefrenceManager.getIstance(context).setCityId(response.getInt("city"));
                            PrefrenceManager.getIstance(context).setDepartmentId(response.getInt("department"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getUserInfo.onUserInfoRecived(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "onErrorResponse: " + error);
                getUserInfo.onUserInfoRecived(false);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "token " + prefrenceManager.getToken());
                return headers;
            }
        };


        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }

    public void getDepartment( final onDepartmentRecived onDepartmentRecived ){

            final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                    "https://asemantile.ir/api/departments", null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            List<Department> departments = new ArrayList<>();

                            for (int i = 0; i<response.length(); i++){
                                Department department =new Department();
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    department.setId(jsonObject.getInt("id"));
                                    department.setName(jsonObject.getString("department_name"));
                                    departments.add(department);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            onDepartmentRecived.onRecived(departments);
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




    public interface onTokenRecived {
        void onTokenRecived(boolean success);
    }

    public interface getProfile {
        void onUserInfoRecived(boolean success);
    }
    public interface onDepartmentRecived{
        void onRecived(List<Department> departments);
    }
}
