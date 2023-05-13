package com.example.mvpdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;

import com.example.mvpandmvvmdemo.R;
import com.example.mvpandmvvmdemo.databinding.ActivityMainBinding;
import com.example.mvpdemo.middler.ActivityUtils;
import com.example.mvpdemo.middler.IpInfoPresenter;
import com.example.mvpdemo.middler.IpInfoTask;
import com.example.mvpdemo.uis.IpInfoFragment;

public class MainActivity extends AppCompatActivity {

    private IpInfoPresenter ipInfoPresenter;
    ActivityMainBinding myBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        IpInfoFragment fragment = IpInfoFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),fragment,R.id.fragmentCont);
        IpInfoTask task = IpInfoTask.getInstance();
        task.setHandler(new Handler(getMainLooper()));//OKhttp不能再回调中自动更新，用Handler实现主线程中运行

        ipInfoPresenter = new IpInfoPresenter(fragment,task);
        fragment.setPresenter(ipInfoPresenter);

    }
}