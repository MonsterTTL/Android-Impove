package com.example.mvpandmvvmdemo.interfaces;

public interface NetTask<T> {
    void execute(T input,MyFeedBack feedBack);
}
