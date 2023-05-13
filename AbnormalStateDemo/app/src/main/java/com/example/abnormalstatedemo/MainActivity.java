package com.example.abnormalstatedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.example.abnormalstatedemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    int lastData = 0;
    private static final String TAG = "MYActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Main+onCreate: ");
        Log.d(TAG, "onCreate: "+getTaskId());
        Log.d(TAG, "onCreate: =====================================");
        //if(savedInstanceState != null){
        //    lastData = savedInstanceState.getInt("lastdata",0);
        //}
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.tvData.setText(String.valueOf(lastData));
        binding.btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastData++;
                binding.tvData.setText(String.valueOf(lastData));
            }
        });

        binding.btSubstract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastData--;
                binding.tvData.setText(String.valueOf(lastData));
            }
        });

        binding.btIn1start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.btIn1start3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity+onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity+onDestroy: "+getTaskId());
    }
}