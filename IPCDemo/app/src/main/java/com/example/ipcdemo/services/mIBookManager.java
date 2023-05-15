package com.example.ipcdemo.services;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.example.ipcdemo.aidl.Book;

import java.util.List;


//自己实现远程调用
public interface mIBookManager extends IInterface {//继承IInterface

    //要实现的接口的标识符
    static final String DESCRIPTOR = "com.example.ipcdemo.services.mIBookManager";

    static final int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;
    static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    public List<Book> getBookList() throws RemoteException;
    public void addBook(Book book) throws RemoteException;

}
