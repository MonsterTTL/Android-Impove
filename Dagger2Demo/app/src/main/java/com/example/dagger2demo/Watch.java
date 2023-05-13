package com.example.dagger2demo;

import android.util.Log;

import javax.inject.Inject;

public class Watch {
    private static final String TAG = "Watch";

    @Inject//表示依赖器可以使用这个构造
    public Watch(){

    }
    public void work(){
        Log.d(TAG, "work: "+"手表工作");
    }
}
