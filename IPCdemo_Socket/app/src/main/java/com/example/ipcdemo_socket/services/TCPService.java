package com.example.ipcdemo_socket.services;

import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPService extends Service {
    private static final String TAG = "TCPService";
    private boolean mIsServiceDestroy = false;
    private String[] mDefinedMessages = new String[]{
            "你好啊",
            "What's your name？",
            "Weather is Good,shy",
            "Do you know that,I can talk with others in the same time",
            "Laugh!"
    };


    public TCPService() {
    }

    @Override
    public void onCreate() {
        new TCPServer("服务端线程").start();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TCPServer extends HandlerThread{ //不断检测是否有新的客户端接入，如果接入就调用
        //responseClient方法对接入的客户端进行处理

        public TCPServer(String name) {
            super(name);
        }

        public TCPServer(String name, int priority) {
            super(name, priority);
        }

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try{
                serverSocket = new ServerSocket(6666);
            }catch (IOException e){
                e.printStackTrace();
                return;
            }

            while(!mIsServiceDestroy){
                try{
                    final Socket client = serverSocket.accept();
                    Log.d(TAG, "accept");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        private void responseClient(Socket client) throws IOException{

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
            out.println("欢迎来到聊天室");
            while(!mIsServiceDestroy){
                String str = in.readLine();
                System.out.println("msg from client:"+ str);
                if(str == null){
                    break;
                }
                //随机挑选一句信息发送
                int i  = new Random().nextInt(mDefinedMessages.length);
                String msg = mDefinedMessages[i];

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                out.println(msg);
                System.out.println("Send: "+msg);
            }
            //关闭各种流和客户端
            out.close();
            in.close();
            client.close();
        }
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroy = true;
        super.onDestroy();
    }
}