package com.example.activitypartonedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.activitypartonedemo.databinding.ActivityMainBinding;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        //直接调用静态方法
        BlankFragment.myStaticMethod();

        //如果不是静态方法
        Utils utils = new Utils(new BlankFragment());
        utils.invokeMethod();

        binding.btId1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.second");
                intent.setDataAndType(null,"text/plain");

                if(intent.resolveActivity(getPackageManager()) != null){
                    Log.d(TAG, "onClick: "+"进入了");
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}