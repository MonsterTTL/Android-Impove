package com.example.mvvmdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mvvmdemo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    int index = 0;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setMySwordsMan(new ObSwordsMan("走私机","A+"));//设置布局变量里的东西
        binding.setTime(new Date());
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("G1");testArray.add("G2");testArray.add("G3");
        binding.setMyArrayList(testArray);

        ArrayList<SwordsMan> mList = new ArrayList<>();
        initRecycleView(mList);

        binding.setOnClickListener1(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "多此一举"+binding.getMyArrayList().get(index++),
                        Toast.LENGTH_SHORT).show();
                index %= 3;
            }
        });


        binding.btTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.edText.getText().toString();
                binding.getMySwordsMan().setName(content);
            }
        });


    }

    private void initList(List mList){
        mList.add(new SwordsMan("审判者古达","B"));
        mList.add(new SwordsMan("冷冽谷的波尔多","A"));
        mList.add(new SwordsMan("英雄古达","S"));
    }

    private void initRecycleView(List mList){
        initList(mList);
        binding.reList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.reList.setAdapter(new MyAdapter(mList));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Iname",binding.edText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String last = savedInstanceState.getString("Iname");
        binding.getMySwordsMan().setName(last);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(this, "Changes", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }
}