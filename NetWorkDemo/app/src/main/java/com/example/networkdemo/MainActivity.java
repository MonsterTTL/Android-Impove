package com.example.networkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import NetWorkUtils.UrlConnManager;
import NetWorkUtils.VolleyDemo;

public class MainActivity extends AppCompatActivity {
    
    Button button;
    ImageView imageView;
    NetworkImageView networkImageView;
    RequestQueue mQueue;
    private static final String TAG = "UrlConnManager";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.image_view);
        networkImageView = findViewById(R.id.n_imageview);
        //一个Application对应一个Queue -- 要是频繁的话可以指定一个Activity对应一个请求队列
        mQueue = Volley.newRequestQueue(getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Execute");
                //VolleyDemo.StringRequestDemo(mQueue);
                //VolleyDemo.JsonRequestDemo(mQueue);
                //VolleyDemo.ImageRequestDemo(mQueue,imageView);
                VolleyDemo.ImageLoaderDemo(mQueue,imageView);
                VolleyDemo.NetWkPIC_Demo(mQueue,networkImageView);

            }
        });




    }

    private void Volley_NetImageTest(){


    }


}