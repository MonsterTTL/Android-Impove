package com.example.networkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import NetWorkUtils.UrlConnManager;
import NetWorkUtils.VolleyDemo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofitDemo.ReInterface;
import retrofitDemo.ReModel;
import retrofitDemo.RetrofitClient;
import retrofitDemo.SimpleInterface;

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
                /***
                   Volley类的示例代码
                //VolleyDemo.StringRequestDemo(mQueue);
                //VolleyDemo.JsonRequestDemo(mQueue);
                //VolleyDemo.ImageRequestDemo(mQueue,imageView);
                //VolleyDemo.ImageLoaderDemo(mQueue,imageView);
                //VolleyDemo.NetWkPIC_Demo(mQueue,networkImageView);

                 ***/

                //Retrofit 的 示例代码
                //getWeatherRealTime();

                //没用单例模式构造出来的
                simpleForRetrofit();

            }
        });




    }


    private void getWeatherRealTime(){
        Call<ReModel> call = RetrofitClient.getInstance().getInterface().Dynamic_getData("minutely");
        call.enqueue(new Callback<ReModel>() {
            @Override
            public void onResponse(Call<ReModel> call, Response<ReModel> response) {
                ReModel data = response.body();
                Toast.makeText(MainActivity.this, data.timezone, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ReModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void simpleForRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ReInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();//创建Retrofit实体类
        //baseUrl有着格式的规范

        //创建接口实现类
        ReInterface simpleInterface = retrofit.create(ReInterface.class);
        //通过接口实现类返回Call对象
        Call<ReModel> myCall = simpleInterface.getRealtime();

        //通过Call执行请求--与Okhttp不同，Retrofit的回调中可以直接刷新UI，这是它比Okhttp更优秀的一点
        myCall.enqueue(new Callback<ReModel>() {
            @Override
            public void onResponse(Call<ReModel> call, Response<ReModel> response) {
                ReModel data = response.body();//通过response获取序列化后的数据(因为之前已经添加了GsonConvert)
                Toast.makeText(MainActivity.this, data.timezone, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: "+Thread.currentThread().getName());
            }
            @Override
            public void onFailure(Call<ReModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }


}