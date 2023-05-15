package com.example.ipcdemo2.aidl;

import com.example.ipcdemo2.aidl.Book;

interface IonNewBookArrived {
    void onNewBookArrived(in Book newBook);
}