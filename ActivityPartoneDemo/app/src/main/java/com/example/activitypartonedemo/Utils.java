package com.example.activitypartonedemo;

import androidx.fragment.app.Fragment;

public class Utils {

    private BlankFragment myFragment = null;

    public Utils(BlankFragment myFragment){
        this.myFragment = myFragment;
    }

    public void invokeMethod(){
        myFragment.myMethod();
    }
}
