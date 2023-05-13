package com.example.servicedetaildemo.myService;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyBindService extends IntentService {


    private static final String TAG = "MyBindService";
    public MyBindService() {
        super("MyBindService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "绑定成功---Service");
        return new MyBinder();//返回业务接口给客户端使用
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public static class MyBinder extends Binder{
        //一些服务端的业务接口

        public void Show(Context context){
            Toast.makeText(context, "测试接口Show", Toast.LENGTH_SHORT).show();
        }
    }

    public static class MyConnection implements ServiceConnection{

        public MyBinder mBinder = null;
        public Context mContext = null;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: "+"已经成功连接服务");
            mBinder = (MyBinder) service;
            mBinder.Show(mContext);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: "+"服务连接已丢失");
        }
    }


}