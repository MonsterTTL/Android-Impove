package com.example.mvpandmvvmdemo.middler;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager manager, Fragment fragment,int frameId){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(frameId,fragment);
        transaction.commit();//提交事务
    }
}
