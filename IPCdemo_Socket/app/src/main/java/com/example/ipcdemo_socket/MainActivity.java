package com.example.ipcdemo_socket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.ipcdemo_socket.databinding.ActivityMainBinding;
import com.example.ipcdemo_socket.services.TCPService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    ActivityMainBinding binding ;
    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private PrintWriter mPrintWriter;//用于输出
    private Socket mClientSocket;

    @SuppressLint("HandlerLeak")//抑制内存泄漏的警告
    private Handler mHander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.btSend.setOnClickListener(this);
        mHander = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case MESSAGE_RECEIVE_NEW_MSG:
                        binding.content.setText(binding.content.getText() +
                                (String) msg.obj); //实际上就是在后面附加新的内容
                        break;
                    case MESSAGE_SOCKET_CONNECTED:
                        binding.btSend.setEnabled(true);
                        break;
                    default:
                        break;
                }
            }
        };
        
        Intent intent = new Intent(MainActivity.this, TCPService.class);
        startService(intent);//连接到客户端
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectTCPServer();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        if(mClientSocket != null){
            try{
                mClientSocket.shutdownInput();
                mClientSocket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btSend){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String msg = binding.edText.getText().toString();//获取编辑栏中的内容
                    if(!TextUtils.isEmpty(msg) && mPrintWriter != null){
                        mPrintWriter.println(msg);
                        binding.edText.setText("");//清空输入栏
                        String time = formatDataTime(System.currentTimeMillis());
                        final String showedMsg = "self" + time + ":" + msg + "\n";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.content.setText(binding.content.getText() + showedMsg);
                            }
                        });

                    }
                }
            }).start();
        }
    }

    private String formatDataTime(long time){
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    private void connectTCPServer(){
        Socket socket = null;
        while(socket == null){
            try {
                socket = new Socket("127.0.0.1",6666);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),true);
                mHander.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.d(TAG, "connectTCPServer:  successful");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                Log.d(TAG, "retry....");
            }
        }

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(!MainActivity.this.isFinishing()){
                String msg = br.readLine();
                System.out.println("receive :"+msg);
                if(msg != null){
                    String time = formatDataTime(System.currentTimeMillis());
                    final String showedMsg = "server "+ time +":"+msg;
                    mHander.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,showedMsg).sendToTarget();//发送给Handler

                }
            }
            System.out.println("quit.....");
            mPrintWriter.close();
            br.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}