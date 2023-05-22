package com.example.binderpooldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.binderpooldemo.aidl.ICompute;
import com.example.binderpooldemo.aidl.ISecurityCenter;
import com.example.binderpooldemo.impl.BinderPool;
import com.example.binderpooldemo.impl.ComputeImpl;
import com.example.binderpooldemo.impl.SecurityCenterImpl;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();

    }


    private void doWork(){
        BinderPool binderPool = BinderPool.getInstance(MainActivity.this);//初始化连接池
        IBinder security = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        ISecurityCenter securityCenter = (ISecurityCenter) SecurityCenterImpl.asInterface(security);
        String msg = "helloworld-安卓";
        try{
            String password = securityCenter.encrypt(msg);
            Log.d(TAG, "encrypt: "+password);
            Log.d(TAG, "decrypt: "+securityCenter.decrypt(password));
        }catch (Exception e){
            e.printStackTrace();
        }

        ICompute compute = ComputeImpl.asInterface(binderPool.queryBinder(BinderPool.BINDER_COMPUTE));
        try{
            Log.d(TAG, "3+5= "+compute.add(3,5));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}