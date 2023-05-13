package com.example.dagger2demo.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dagger2demo.R;
import com.example.dagger2demo.SwordMan;
import com.example.dagger2demo.databinding.ActivityMainBinding;
import com.example.dagger2demo.db.Man;
import com.example.dagger2demo.myEngines.Car;



import com.google.gson.Gson;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Inject
    Gson gson;

    @Inject
    Gson gson2;

    @Inject
    SwordMan swordMan;
    //@Inject
    //Car car;


    ActivityMainBinding myBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        App.get(MainActivity.this).getActivityComponent().inject(this);
        onClick();

        if(gson.hashCode() == gson2.hashCode()){
            Toast.makeText(this, "Same", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Different", Toast.LENGTH_SHORT).show();
        }

        myBinding.btTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, swordMan.fighting(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onClick(){
        myBinding.btTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}