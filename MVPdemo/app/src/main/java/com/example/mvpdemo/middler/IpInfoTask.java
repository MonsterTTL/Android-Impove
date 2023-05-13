package com.example.mvpdemo.middler;

import android.os.Handler;

import com.example.mvpdemo.interfaces.MyFeedBack;
import com.example.mvpdemo.interfaces.NetTask;
import com.example.mvpdemo.modules.IpInfo;
import com.example.mvpdemo.modules.ipInfoDB;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.google.gson.*;

import java.io.IOException;

public class IpInfoTask implements NetTask<String> {
    private static IpInfoTask INSTANCE = null;
    private static final String HOST = "https://restapi.amap.com/v3/ip?ip=";
    private static final String KEY = "89d778e83b5bf9581e747a7f143c389d";
    private MyFeedBack feedBack;

    private IpInfoTask(){

    }

    private Handler mHandler;

    //DCL
    public static IpInfoTask getInstance(){
        if(INSTANCE == null){
            synchronized (IpInfoTask.class){
                if(INSTANCE == null){
                    INSTANCE = new IpInfoTask();
                }
            }
        }
        return INSTANCE;
    }

    public void setHandler(Handler mHandler){
        this.mHandler = mHandler;
    }

    //回调方法
    @Override
    public void execute(String input, MyFeedBack feedBack) {
        Request.Builder builder = new Request.Builder().url(HOST+input+"&output=json&key="+KEY).method("GET",null);
        Request mRequest = builder.build();
        OkHttpClient client = new OkHttpClient();
        Call myCall = client.newCall(mRequest);
        myCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                feedBack.onFailed();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //返回的数据直接给转化了
                Gson gson = new Gson();
                ipInfoDB db = gson.fromJson(response.body().string(),ipInfoDB.class);
                IpInfo info = new IpInfo();
                info.setMyData(db);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        feedBack.onSuccess(info);
                    }
                });

            }
        });
    }
}
