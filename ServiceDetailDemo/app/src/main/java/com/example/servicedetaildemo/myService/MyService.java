package com.example.servicedetaildemo.myService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyService extends Service {

    private static final String TAG = "MyService";
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    private static final int SIT_A = 1;
    private static final int SIT_B = 2;
    private static final String SIT_A_STRING = "com.situationA";
    private static final String SIT_B_STRING = "com.situationB";

    private final class ServiceHandler extends Handler{
        public ServiceHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case SIT_A:
                    Toast.makeText(MyService.this, "Situation A", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "handleMessage: "+Thread.currentThread().getName());
                    break;
                case SIT_B:
                    Toast.makeText(MyService.this, "Situation B", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "handleMessage: "+Thread.currentThread().getName());
                    break;
                default:
                    break;
            }

            stopSelf(msg.arg1);//根据启动的Id值来决定是否结束Service
        }
    }

    @Override
    public void onCreate() {
        //设置工作线程的优先级
        HandlerThread handlerThread = new HandlerThread("NormalServiceThread", Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();//启动工作线程

        //绑定Looper和Handler
        serviceLooper = handlerThread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);

    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "服务已启动", Toast.LENGTH_SHORT).show();
        Message message = serviceHandler.obtainMessage();
        message.arg1 = startId;//设置arg1为启动Id
        switch (intent.getAction()){
            case SIT_A_STRING:
                message.what = SIT_A;
                break;
            case SIT_B_STRING:
                message.what = SIT_B;
                break;
            default:
                break;
        }
        serviceHandler.sendMessage(message);//发送Message

        return START_STICKY;
    }

    public static void StartSITA(Context context){
        Intent intent = new Intent(context,MyService.class);
        intent.setAction(SIT_A_STRING);
        context.startService(intent);
    }

    public static void StartSITB(Context context){
        Intent intent = new Intent(context,MyService.class);
        intent.setAction(SIT_B_STRING);
        context.startService(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
    }
}