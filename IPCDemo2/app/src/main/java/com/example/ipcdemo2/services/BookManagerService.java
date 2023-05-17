package com.example.ipcdemo2.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ipcdemo2.Observers;
import com.example.ipcdemo2.aidl.Book;
import com.example.ipcdemo2.aidl.BookManager;
import com.example.ipcdemo2.aidl.IonNewBookArrived;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";
    public static final int UPDATE = 1;
    private String ErrorTest = null;
    private CopyOnWriteArrayList<Book> mList = new CopyOnWriteArrayList<>();//支持并发读写的ArrayList
    private RemoteCallbackList<IonNewBookArrived> mCustomers = new RemoteCallbackList<>();

    private HandlerThread WorkThread = new HandlerThread("BookManagerService's Thread",
            Process.THREAD_PRIORITY_BACKGROUND);
    private Looper mLooper ;
    private mHandler mHandler;
    private class mHandler extends Handler{
        public mHandler(Looper mLooper) {
            super(mLooper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case UPDATE:{
                    try {
                        Book book = msg.getData().getParcelable("newBook");
                        onNewBookArrived(book);
                        return;
                    } catch (RemoteException e) {
                        Log.d(TAG, "处理信息时出错！");
                        throw new RuntimeException(e);
                    }
                }
                default:
                    break;
            }
        }
    };



    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        WorkThread.start();
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        Log.d(TAG, "onCreate: currentThread = " + Thread.currentThread().getName());
        mLooper = WorkThread.getLooper();
        mHandler = new mHandler(mLooper);

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind currentThread:"+Thread.currentThread().getName());
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    //通知订阅者更新了
    private void onNewBookArrived(Book book) throws RemoteException{
        if(book == null){
            Log.d(TAG, "书籍无效");
            return;
        }
        mList.add(book);
        Log.d(TAG, "onNewBook currentThread:"+Thread.currentThread().getName());
//        Log.d(TAG, "onNewBookArrived: Size: "+mCustomers.);
//        Log.d(TAG, "Book Name is:"+book.BookName+" Author is:"+book.Author);
        final int N = mCustomers.beginBroadcast();
        Log.d(TAG, "目前的订阅数："+N);
        for(int i = 0;i < N;i++){
            IonNewBookArrived listener = mCustomers.getBroadcastItem(i);
            if(listener != null){
                listener.onNewBookArrived(book);
            }
        }
        mCustomers.finishBroadcast();
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "服务终止");
        super.onDestroy();
    }
    private mBinder mBinder = new mBinder();
    private class mBinder extends BookManager.Stub{
        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d(TAG, "addBook currentThread:"+Thread.currentThread().getName());
            if(book != null){
                mList.add(book);
                Message message = mHandler.obtainMessage();
                message.what = UPDATE;
                Bundle mBundle = new Bundle(); //Parcelable 先放 Bundle
                mBundle.putParcelable("newBook",book);//Bundle 再放 Message
                message.setData(mBundle);
                mHandler.sendMessage(message);
                //ErrorTest.toLowerCase();


            }
        }
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mList;
        }
        @Override
        public boolean registerListener(IonNewBookArrived listener) throws RemoteException {
//            if(!mCustomers.contains(listener)){
//                //((Observers)listener).Id = 0;
//                mCustomers.add(listener);
//                Log.d(TAG, "用户已添加");
//                //DispAll();
//                return true;
//            }else{
//                Log.d(TAG, "用户已存在");
//                return false;
//            }
            mCustomers.register(listener);
            Log.d(TAG, "register currentThread:"+Thread.currentThread().getName());
            return true;
        }
        @Override
        public boolean unregisterListener(IonNewBookArrived listener) throws RemoteException {
//            if(mCustomers.contains(listener)){
//                mCustomers.remove(listener);
//                Log.d(TAG, "用户已移除");
//                return true;
//            }else{
//                Log.d(TAG, "该用户不存在!");
//                return false;
//            }
            mCustomers.unregister(listener);
            return true;
        }

//        public void DispAll(){
//            Log.d(TAG, "接下来是服务端中的数据:");
//            for(int i = 0;i < mCustomers.size();i++){
//                Log.d(TAG, "My id is:"+((Observers)mCustomers.get(i)).Id);
//            }
//        }
    };


}