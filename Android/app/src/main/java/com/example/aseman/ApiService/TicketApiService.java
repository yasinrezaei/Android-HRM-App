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
import com.example.aseman.Objects.ManagerDailyReport;
import com.example.aseman.Objects.Ticket;
import com.example.aseman.PrefrenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketApiService {
    private Context context;

    public TicketApiService(Context context) {
        this.context = context;
    }
    //////////get all tickets //////////
    public void getAllTickets(final TicketApiService.onAllTicketsRecived onAllTicketsRecived ){

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                "https://asemantile.ir/api/tickets", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        List<Ticket> tickets = new ArrayList<>();

                        for (int i = 0; i<response.length(); i++){
                            Ticket ticket =new Ticket();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ticket.setId(jsonObject.getInt("id"));
                                ticket.setTitle(jsonObject.getString("title"));
                                ticket.setText(jsonObject.getString("text"));
                                ticket.setAnswer(jsonObject.getString("answer"));
                                ticket.setCheckByReciever(jsonObject.getBoolean("check_by_reciever"));
                                ticket.setSender_id(jsonObject.getInt("sender"));
                                ticket.setReciever_id(jsonObject.getInt("reciever"));
                                ticket.setCheckByReciever(jsonObject.getBoolean("check_by_reciever"));
                                //set tag of tickets
                                if(jsonObject.getString("tag").equals("Normal")) ticket.setTag("عادی");
                                else if(jsonObject.getString("tag").equals("Urgent")) ticket.setTag("فوری");
                                else ticket.setTag("مهم");
                                tickets.add(ticket);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        onAllTicketsRecived.onRecived(tickets);
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

    public interface onAllTicketsRecived{
        void onRecived(List<Ticket> tickets);
    }



    /////////post///////////////
    public void postTicket(final JSONObject jsonObject){
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://asemantile.ir/api/tickets/", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context,"درخواست با موفقیت ثبت شد",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("report", "onErrorResponse: "+error );
                Toast.makeText(context," درخواست ثبت نشد!",Toast.LENGTH_SHORT).show();
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
    /////////////////update////////
    public void updateTicket(int ticketId,final JSONObject jsonObject){
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, "https://asemantile.ir/api/ticket-detail/"+ticketId, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context,"فعالیت با موفقیت انجام شد",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("report", "onErrorResponse: "+error );
                Toast.makeText(context," خطا در ثبت نتیجه فعالیت!",Toast.LENGTH_SHORT).show();
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
