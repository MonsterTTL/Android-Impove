package com.example.mvpdemo.interfaces;

public interface NetTask<T> {
    void execute(T input,MyFeedBack feedBack);
}
