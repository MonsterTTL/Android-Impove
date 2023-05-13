package com.example.matlabtest;

import androidx.appcompat.app.AppCompatActivity;

import android.net.InetAddresses;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static String address;
    static boolean isFinish = false;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        address = getIPAddress();
        Log.d(TAG, "onCreate: "+address);
        Log.d(TAG, "onCreate: "+"准备完毕");

    }

    @Override
    protected void onResume() {
        super.onResume();
        new UDP_Re().start();

    }

    public String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    class UDP_Re extends Thread{

        DatagramPacket packet;
        DatagramSocket socket;
        int len = 2;
        int reCounter = 0;
        @Override
        public void run() {
            try {
                Log.d(TAG, "run: "+"等待接收");
                socket = new DatagramSocket(1234);
                packet = new DatagramPacket(new byte[1024],1024);
                while(true){

                    socket.receive(packet);
                    byte[] arr = packet.getData();
                    len = packet.getLength();
                    reCounter++;
                    Log.d(TAG, "run: "+reCounter);
                }
                //Log.d(TAG, "接收数据数为："+reCounter);
            } catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}