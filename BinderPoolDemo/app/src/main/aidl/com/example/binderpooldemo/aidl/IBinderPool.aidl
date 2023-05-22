// IBinderPool.aidl
package com.example.binderpooldemo.aidl;

// Declare any non-default types here with import statements

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}