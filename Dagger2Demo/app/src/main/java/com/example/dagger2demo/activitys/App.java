package com.example.dagger2demo.activitys;

import android.app.Application;
import android.content.Context;

import com.example.dagger2demo.myComponent.ActivityComponent;
import com.example.dagger2demo.myComponent.DaggerActivityComponent;
import com.example.dagger2demo.myComponent.DaggerSwordmanComponent;

public class App extends Application {
    ActivityComponent activityComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        activityComponent = DaggerActivityComponent.builder().swordmanComponent(DaggerSwordmanComponent.builder().build())
                .build();

    }

    public static App get(Context context){
        return (App) context.getApplicationContext();
    }

    ActivityComponent getActivityComponent(){
        return activityComponent;
    }
}
