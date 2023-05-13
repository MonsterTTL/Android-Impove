package com.example.servicedetaildemo.myService;

import static android.app.PendingIntent.FLAG_MUTABLE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.servicedetaildemo.MainActivity;
import com.example.servicedetaildemo.R;

public class NotificationService extends Service {

    private static final String TAG = "NotificationService";
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "MyNotificationService";
    NotificationManager manager;
    NotificationChannel channel;
    @Override
    public void onCreate() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID,NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);//创建通知渠道
            Toast.makeText(this, "创建完毕", Toast.LENGTH_SHORT).show();
        }
    }

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Log.d(TAG, "run: ");
                }
            }
        }).start();

        Intent intent1 = new Intent(this, MainActivity.class);
        //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent1, FLAG_MUTABLE|PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("前台服务正在执行")
                .setContentText("测试样例一")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(NOTIFICATION_ID,notification);//提升为前台服务

        return START_STICKY;
    }


}