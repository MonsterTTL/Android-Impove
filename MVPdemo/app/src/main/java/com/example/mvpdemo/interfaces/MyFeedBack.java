package com.example.mvpdemo.interfaces;

public interface MyFeedBack<T> {
    void onSuccess(T t);
    void onFailed();
}
