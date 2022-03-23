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
import com.example.aseman.PrefrenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentApiService {
    private Context context;

    public DepartmentApiService(Context context) {
        this.context = context;
    }
    public void getAllDepartments(final DepartmentApiService.onAllDepartmentsRecived onAllDepartmentsRecived ){

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
                        onAllDepartmentsRecived.onRecived(departments);
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

    public interface onAllDepartmentsRecived{
        void onRecived(List<Department> departments);
    }
}
