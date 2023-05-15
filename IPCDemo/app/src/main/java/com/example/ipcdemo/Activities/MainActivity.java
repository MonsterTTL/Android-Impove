package com.example.ipcdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ipcdemo.R;
import com.example.ipcdemo.UserManager;
import com.example.ipcdemo.aidl.Book;
import com.example.ipcdemo.databinding.ActivityMainBinding;
import com.example.ipcdemo.parcelables.ARealMan;
import com.example.ipcdemo.serializableClass.User;
import com.example.ipcdemo.services.MYIPCService;
import com.example.ipcdemo.services.mBookManagerImpl;
import com.example.ipcdemo.services.mIBookManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private static final String TAG = "IPCService";

    //private IBookManager binder;
    private mIBookManager binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        int pid = Process.myPid();int stackId = getTaskId();
        init();
        Log.d(TAG, "Main'Pid:"+pid + " Main's TaskId:"+stackId);
        UserManager.sUserId = 2;
        Log.d(TAG, "Main's UserManagerId:"+UserManager.sUserId);

    }


    private void init(){
        binding.bt1Start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        binding.bt1Start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("man",new ARealMan("cxk",24));
                startActivity(intent);
            }
        });

        binding.bt1Start3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        binding.btSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    User user = new User(0,"XuMingYang",true);
                    File file = new File(getExternalFilesDir(null),"myCache.txt");
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                    out.writeObject(user);
                    out.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        binding.btAntiSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File file = new File(getExternalFilesDir(null),"myCache.txt");
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream inputStream = new ObjectInputStream(fis);
                    User newUser = (User) inputStream.readObject();
                    if(newUser != null){
                        Toast.makeText(MainActivity.this, newUser.userName, Toast.LENGTH_SHORT).show();
                    }
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        binding.bt1Binding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MYIPCService.class);
                bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.d(TAG, "onServiceConnected: "+name);
                        Book first;
                        binder = mBookManagerImpl.asInterface(service);
                        try {
                            binder.addBook(new Book(99,"周世纪"));
                            first = binder.getBookList().get(0);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(MainActivity.this, first.bookName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                }, Context.BIND_AUTO_CREATE);
                Log.d(TAG, "onClick: "+"finish");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Main");
    }
}

