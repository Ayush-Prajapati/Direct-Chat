package com.ayush.directchat.Utility;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageUtil {

    private final String STORAGE = "com.ayush.directchat";
    private SharedPreferences preferences;
    private final Context context;

    public StorageUtil(Context context) {
        this.context = context;
    }

    public void setMode(Boolean mode){
        preferences = context.getSharedPreferences(STORAGE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("night_mode",mode);
        editor.apply();
    }
    public Boolean getMode(){
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return preferences.getBoolean("night_mode",false);
    }

    public void setIsDemo(Boolean demo){
        preferences = context.getSharedPreferences(STORAGE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_demo",demo);
        editor.apply();
    }
    public Boolean getIsDemo(){
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return preferences.getBoolean("is_demo",true);
    }

}
