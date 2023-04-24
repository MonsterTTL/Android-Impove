package com.example.eventbusdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executors;

public class SecondActivity extends AppCompatActivity {

    TextView tv_message;
    Button bt_message;
    Button bt_sticky;
    Button mes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv_message = findViewById(R.id.tv_message1);
        bt_message = findViewById(R.id.bt_message1);
        bt_sticky = findViewById(R.id.bt_sticky);
        mes = findViewById(R.id.mes_event);
        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("欢迎来到，德莱联盟"));
                finish();
            }
        });

        bt_sticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new MessageEvent("粘性事件"));
                finish();
            }
        });

        mes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new String("另一个事件"));
                finish();
            }
        });
    }
}