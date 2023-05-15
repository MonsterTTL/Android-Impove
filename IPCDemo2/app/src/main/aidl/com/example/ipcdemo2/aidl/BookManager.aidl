package com.example.ipcdemo2.aidl;

import com.example.ipcdemo2.aidl.Book;
import com.example.ipcdemo2.aidl.IonNewBookArrived;

interface BookManager {
    void addBook(in Book book);
    List<Book> getBookList();
    boolean registerListener(IonNewBookArrived listener);
    boolean unregisterListener(IonNewBookArrived listener);
}