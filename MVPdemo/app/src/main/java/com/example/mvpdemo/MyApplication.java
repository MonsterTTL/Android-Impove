package com.example.mvpdemo;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {

    private static Context myContext;

    @Override
    public void onCreate() {
        super.onCreate();
        myContext = getApplicationContext();
    }

    public Context getContext(){
        return myContext;
    }
}