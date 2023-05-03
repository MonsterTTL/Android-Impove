package com.example.mvpandmvvmdemo.interfaces;

public interface MyFeedBack<T> {
    void onSuccess(T t);
    void onFailed();
}
