package com.example.eventbusdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private TextView tv_message;
    private Button bt_message;
    private Button bt_subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_message = findViewById(R.id.tv_message);
        bt_message = findViewById(R.id.bt_message);
        bt_subscription = findViewById(R.id.bt_subscription);

        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });

        bt_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MainActivity注册了这条事件总线
                if(!EventBus.getDefault().isRegistered(MainActivity.this)){
                    EventBus.getDefault().register(MainActivity.this);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //通过参数类型来区分应该执行哪个方法

    @Subscribe(threadMode = ThreadMode.MAIN)//选择线程模型，说明事件将在主线程中处理
    public void onMoonEvent(MessageEvent messageEvent){
        tv_message.setText(messageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void ononStickyEvent(MessageEvent messageEvent){
        tv_message.setText(messageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void secondBack(String mes){
        Toast.makeText(this, mes, Toast.LENGTH_SHORT).show();
    }
}