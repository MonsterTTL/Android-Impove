package com.example.servicedetaildemo.myService;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    private static final String EVENT_A = "com.example.myService.event_a";
    private static final String EVENT_B = "com.example.myService.event_b";
    private static final String PARAM = "myService.Param";


    //一般我们不需要重写，如果重写了返回值一般情况下也一定是super.onStartCommand
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "运行在线程: "+Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: "+"Success");
        return super.onBind(intent);
    }

    //留给外部调用的接口
    public static void startEventA(Context context,String param){
        Intent intent = new Intent(context,MyIntentService.class);
        intent.setAction(EVENT_A);
        intent.putExtra(PARAM,param);
        context.startService(intent);
    }

    public static void startEventB(Context context,String param){
        Intent intent = new Intent(context,MyIntentService.class);
        intent.setAction(EVENT_B);
        intent.putExtra(PARAM,param);
        context.startService(intent);
    }


    public MyIntentService() {
        super("MyIntentService");
    }



    @Override //在onHandleIntent处理事件时会自动创建子线程执行耗时任务
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        String param = intent.getStringExtra(PARAM);
        switch (action){
            case EVENT_A:
                onHandleEventA(param);
                Log.d(TAG, "onHandleIntentA运行在: "+Thread.currentThread().getName());
                break;
            case EVENT_B:
                onHandleEventB(param);
                Log.d(TAG, "onHandleIntentB运行在: "+Thread.currentThread().getName());
                break;
            default:
                break;
        }
        return ;
    }

    private void onHandleEventA(String para){
        Log.d(TAG, "onHandleEventA: "+para + "运行在："+Thread.currentThread().getName());
    }

    private void onHandleEventB(String para){
        Toast.makeText(this, para+" 运行在："+Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
    }

}