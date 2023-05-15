package com.example.ipcdemo2;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.ipcdemo2.aidl.Book;
import com.example.ipcdemo2.aidl.IonNewBookArrived;

public class Observers extends IonNewBookArrived.Stub {

    private static final String TAG = "Observers";
    public int Id;

    public Observers (int Id){
        this.Id = Id;
    }

    @Override
    public void onNewBookArrived(Book newBook) throws RemoteException {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        Log.d(TAG, "My Id is:"+Id);
        Log.d(TAG, "curThread:"+Thread.currentThread().getName());
    }

}
