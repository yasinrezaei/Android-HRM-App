package com.example.aseman;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefrenceManager {
    public static PrefrenceManager instance=null;
    private SharedPreferences sharedPreferences=null;
    private SharedPreferences.Editor editor=null;

    public static  PrefrenceManager getIstance(Context context){
        if (instance==null){
            instance=new PrefrenceManager(context);
        }
        return instance;
    }
    private PrefrenceManager(Context context){
        sharedPreferences=context.getSharedPreferences("MyPrefrences",0);
        editor=sharedPreferences.edit();
    }

    //Clear
    public void clearSharedPref(){
        editor.clear();
        editor.apply();
    }

    //Token
    public String getToken(){
        return sharedPreferences.getString("token",null);
    }
    public void setToken(String token){
        editor.putString("token",token);
        editor.apply();
    }

    //fullName
    public String getFullName(){
        return sharedPreferences.getString("fullName",null);
    }
    public void setFullName(String fullName){
        editor.putString("fullName",fullName);
        editor.apply();
    }

    //username
    public String getUsername(){
        return sharedPreferences.getString("username",null);
    }
    public void setUsername(String username){
        editor.putString("username",username);
        editor.apply();
    }

    //UserId
    public int getUserId(){
        return sharedPreferences.getInt("userId",-1);
    }
    public void setUserId(int userId){
        editor.putInt("userId",userId);
        editor.apply();
    }

    //is_manager
    public boolean is_manager(){
        return sharedPreferences.getBoolean("is_manager",false);
    }
    public void set_manager(boolean is_manager){
        editor.putBoolean("is_manager",is_manager);
        editor.apply();
    }
    //is_main_manager
    public boolean is_main_manager(){
        return sharedPreferences.getBoolean("is_main_manager",false);
    }
    public void set_main_manager(boolean is_main_manager){
        editor.putBoolean("is_main_manager",is_main_manager);
        editor.apply();
    }
    //CityId
    public int getCityId(){
        return sharedPreferences.getInt("cityId",-1);
    }
    public void setCityId(int cityId){
        editor.putInt("cityId",cityId);
        editor.apply();
    }

    //DepartmentId
    public int getDepartmentId(){
        return sharedPreferences.getInt("depId",-1);
    }
    public void setDepartmentId(int depId){
        editor.putInt("depId",depId);
        editor.apply();
    }
    public String getDepartmentName(){
        return sharedPreferences.getString("depName",null);
    }
    public void setDepartmentName(String departmentName){
        editor.putString("depName",departmentName);
        editor.apply();
    }




}
