package com.example.ipcdemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.ipcdemo.aidl.Book;
import com.example.ipcdemo.aidl.IBookManager;

import java.util.ArrayList;
import java.util.List;

public class MYIPCService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder binder = new MyBinder();
    private static final String TAG = "IPCService";
    public MYIPCService() {

    }
    
    public void test2(){
        Log.d(TAG, "test2: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service has been Created!");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new BookBinder().asBinder();
    }

    public class MyBinder extends Binder{
        public void Test(){
            Log.d(TAG, "Test: "+"success");
        }
        
        public MYIPCService getService(){
            return MYIPCService.this;
        }
    }

    public class BookBinder extends IBookManager.Stub{

        private List<Book> mList = new ArrayList<>();

        public BookBinder() {
            super();
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            synchronized (mList){
                return mList;
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (mList){
                mList.add(book);
            }
        }


    }

}

