package com.example.ipcdemo;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    private static final String TAG = "ProcessTest";
    @Override
    public void onCreate() {
        super.onCreate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            String process = getProcessName();
            Log.d(TAG, "myApplication's ProcessName:"+process);
        }


    }
}
