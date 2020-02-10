package com.akshayjadhav.retrofit.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.akshayjadhav.retrofit.models.User;

public class SharedPreferencesManager {

    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static SharedPreferencesManager mInstance;
    private Context mContext;

    private SharedPreferencesManager(Context mContext){
        this.mContext = mContext;
    }


    public static synchronized SharedPreferencesManager getInstance(Context mContext){
        if(mInstance == null){
            mInstance = new SharedPreferencesManager(mContext);
        }
        return  mInstance;
    }

    public void saveUser(User user){
         SharedPreferences sharedPreferences =  mContext
                 .getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id",user.getId());
        editor.putString("email",user.getEmail());
        editor.putString("name",user.getName());
        editor.putString("school",user.getSchool());

        editor.apply();
    }


    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences =  mContext
                .getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        if(sharedPreferences.getInt("id",-1) != -1){
            return  true;
        }
        return false;
    }

    public User getUser(){
        SharedPreferences sharedPreferences =  mContext
                .getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        User user = new User(
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("name",null),
                sharedPreferences.getString("school",null)
        );
        return user;
    }

    public void clear(){
        SharedPreferences sharedPreferences =  mContext
                .getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
