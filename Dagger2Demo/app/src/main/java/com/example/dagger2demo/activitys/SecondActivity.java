package com.example.dagger2demo.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dagger2demo.R;
import com.example.dagger2demo.SwordMan;
import com.example.dagger2demo.databinding.ActivitySecondBinding;

import javax.inject.Inject;

import dagger.Lazy;


public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding S_Binding;

    @Inject
    Lazy<SwordMan> swordManLazy;//实现懒加载
    SwordMan swordMan = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        S_Binding = DataBindingUtil.setContentView(this,R.layout.activity_second);
        App.get(SecondActivity.this).getActivityComponent().inject(this);
        if(swordMan == null){
            Toast.makeText(this, "暂未初始化", Toast.LENGTH_SHORT).show();
        }
        swordMan = swordManLazy.get();
        //setContentView(R.layout.activity_second);
        S_Binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, swordMan.fighting(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}