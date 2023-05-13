package com.example.ipcdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ipcdemo.R;
import com.example.ipcdemo.UserManager;
import com.example.ipcdemo.databinding.ActivitySecondBinding;
import com.example.ipcdemo.parcelables.ARealMan;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding binding;
    Intent intent;
    private static final String TAG = "ProcessTest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        init();
        int pid = Process.myPid();int taskId = getTaskId();
        Log.d(TAG, "Second's Pid:"+pid+" Second's TaskId:"+taskId);
        Log.d(TAG, "Second's UserManagerId:"+ UserManager.sUserId);
        intent = getIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ARealMan man = intent.getParcelableExtra("man");
        Toast.makeText(this,man.name , Toast.LENGTH_SHORT).show();

    }

    private void init(){
        binding.bt2Start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.bt2Start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

        binding.bt2Start3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }
}