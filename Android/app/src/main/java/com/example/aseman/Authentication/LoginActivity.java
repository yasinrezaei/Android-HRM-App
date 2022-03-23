package com.example.aseman.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aseman.ApiService.LoginApiService;
import com.example.aseman.ApiService.ProfileApiService;
import com.example.aseman.MainActivity;
import com.example.aseman.Objects.Department;
import com.example.aseman.PrefrenceManager;
import com.example.aseman.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtUsername,edtPassword;
    private PrefrenceManager prefrenceManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefrenceManager=PrefrenceManager.getIstance(LoginActivity.this);
        configureView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();



            }
        });


    }

    private void configureView() {
        btnLogin=findViewById(R.id.btn_login);
        edtUsername=findViewById(R.id.edt_username);
        edtPassword=findViewById(R.id.edt_password);
    }


    private void login() {

        final String username = edtUsername.getText().toString();
        final String password = edtPassword.getText().toString();

        if (username.trim().equals("") || password.trim().equals("")) {
            Toast.makeText(LoginActivity.this, "نام کاربری و رمز عبور را وارد کنید", Toast.LENGTH_SHORT).show();
        } else {
            LoginApiService apiService = new LoginApiService(LoginActivity.this);
            JSONObject requestJsonObject = new JSONObject();
            try {
                requestJsonObject.put("username", username);
                requestJsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            apiService.getToken(requestJsonObject, new LoginApiService.onTokenRecived() {
                @Override
                public void onTokenRecived(boolean success) {

                    if (success) {
                        prefrenceManager.setUsername(username);
                        getUserId();

                    }
                    else{
                        Toast.makeText(LoginActivity.this, "نام کاربری یا رمز عبور شما اشتباه است", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }
    private void getUserId(){
        LoginApiService apiService = new LoginApiService(LoginActivity.this);
        apiService.getUserId(new LoginApiService.getUserInfo() {
            @Override
            public void onUserInfoRecived(boolean success) {
                if(success){
                    Toast.makeText(LoginActivity.this, "id"+prefrenceManager.getUserId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "خطای دریافت اطلاعات از سرور!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentFocus()!=null){
            InputMethodManager imm=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
