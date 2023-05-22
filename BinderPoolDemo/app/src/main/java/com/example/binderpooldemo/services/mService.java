package com.example.binderpooldemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.binderpooldemo.impl.IBinderPoolImpl;

public class mService extends Service {
    private static final String TAG = "mService";
    private Binder mBinderPool = new IBinderPoolImpl();

    public mService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: +Service");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
      return mBinderPool;
    }


}