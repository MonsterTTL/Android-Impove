package com.example.ipcdemo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ipcdemo2.aidl.Book;
import com.example.ipcdemo2.aidl.BookManager;
import com.example.ipcdemo2.databinding.ActivityMainBinding;
import com.example.ipcdemo2.services.BookManagerService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    BookManager manager;
    int Bookcounter = 0;
    int obCounter = 0;
    private static final String TAG = "BookManager";
    private mServiceConnection mServiceConnection = new mServiceConnection();
    private mDeathPro mDeathPro = new mDeathPro();//死亡代理
    ArrayList<Observers> obList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.currentThread().setName("MainActivity's UI  Thread!");
        Log.d(TAG, "onCreate: + Main CurThread: "+Thread.currentThread().getName());
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.btBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookManagerService.class);
                bindService(intent,mServiceConnection ,BIND_AUTO_CREATE);
            }
        });

        binding.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    manager.addBook(new Book("default"+Bookcounter,"author"+Bookcounter));
                    Bookcounter++;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        binding.btAddOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //if(!obList.isEmpty())
                    //    Log.d(TAG, "last is:"+obList.get(obList.size()-1).Id);
                    Observers ob = new Observers(obCounter);
                    obList.add(ob);
                    manager.registerListener(ob);
                    obCounter++;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        binding.btGetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            manager.getBookList();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        binding.btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "接下来是客户端中的数据:");
                Log.d(TAG, "current:"+Thread.currentThread().getName());
                for(int i = 0;i < obList.size();i++){
                    Log.d(TAG, "My Id is: "+obList.get(i).Id);
                }
                //unbindService(mServiceConnection);
                //checkCallingOrSelfPermission() -- 监察权限用的
            }
        });

        binding.btUnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0;i < obList.size();i++){
                    try {
                        manager.unregisterListener(obList.get(i));
                    } catch (RemoteException e) {
                        Log.d(TAG, "注销出错啦！！");
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }

    private class mServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            manager = BookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(mDeathPro,0); //设置一个死亡代理
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            Toast.makeText(MainActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "连接已丢失", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onServiceDisconnected: 连接已丢失");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        for(int i = 0;i < obList.size();i++){
            try {
                manager.unregisterListener(obList.get(i));
            } catch (RemoteException e) {
                Log.d(TAG, "注销出错啦！！");
                throw new RuntimeException(e);
            }
        }

    }

    private class mDeathPro implements IBinder.DeathRecipient{ //给Binder设置死亡代理

        @Override
        public void binderDied() {
            if(manager == null){
                return;
            }
            manager.asBinder().unlinkToDeath(this,0);
            manager = null;
            Log.d(TAG, "死亡代理触发");
            Intent intent = new Intent(MainActivity.this,BookManagerService.class);
            bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
            //重新连接
        }
    }
}