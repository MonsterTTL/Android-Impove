package com.example.servicedetaildemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.servicedetaildemo.databinding.ActivityMainBinding;
import com.example.servicedetaildemo.myService.MyBindService;
import com.example.servicedetaildemo.myService.MyIntentService;
import com.example.servicedetaildemo.myService.MyService;
import com.example.servicedetaildemo.myService.NotificationService;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.btTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntentService.startEventA(MainActivity.this,"这是测试1");
            }
        });

        binding.btTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntentService.startEventB(MainActivity.this,"这是测试2");
            }
        });

        binding.btTest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyService.StartSITA(MainActivity.this);
            }
        });

        binding.btTest4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyService.StartSITB(MainActivity.this);
            }
        });

        binding.btBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyBindService.class);
                MyBindService.MyConnection connection = new MyBindService.MyConnection();
                connection.mContext = MainActivity.this;
                bindService(intent,connection,Context.BIND_AUTO_CREATE);
            }
        });


        binding.btQiantai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationService.class);
                startService(intent);
            }
        });



    }
}