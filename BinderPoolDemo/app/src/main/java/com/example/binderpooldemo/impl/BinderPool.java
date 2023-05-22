package com.example.binderpooldemo.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;


import com.example.binderpooldemo.aidl.IBinderPool;
import com.example.binderpooldemo.services.mService;

import java.util.concurrent.CountDownLatch;

public class BinderPool {
    private static final String TAG = "BinderPool";
    public static final int BINDER_NONE = -1;
    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_SECURITY_CENTER = 1;

    private Context mContext ;
    private IBinderPool mIBinderPool ;
    private static volatile BinderPool sInstance;//单例模式
    private CountDownLatch mConnectBinderPoolCountDownLatch;

    private BinderPool(Context context){
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBinderPool = IBinderPool.Stub.asInterface(service); // 绑定远程服务进行Binder池服务
//            try{
//                mIBinderPool.asBinder().linkToDeath();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            Log.d(TAG, "onServiceConnected: "+ "连接成功");
            mConnectBinderPoolCountDownLatch.countDown(); //计数器-1
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //DCL模式
    public static BinderPool getInstance(Context context){
        if(sInstance == null){
            synchronized (BinderPool.class){
                if (sInstance == null){
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    private synchronized void connectBinderPoolService(){
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);//定时器为1
        Intent intent = new Intent(mContext, mService.class);
        mContext.bindService(intent,mServiceConnection,Context.BIND_AUTO_CREATE);
        try{
            mConnectBinderPoolCountDownLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int binderCode){
        IBinder binder = null;
        try{
            if(mIBinderPool != null){
                binder = mIBinderPool.queryBinder(binderCode); //通过Binder池查找binder对象
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return binder;
    }
}
