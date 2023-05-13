package com.example.mvpdemo.interfaces;

//业务接口
public interface LoginTask<T,V> {
    void Inputs(T username,V password);
    boolean verify();//验证登录密码和用户名
    T getUserName();
    V getPassword();
}
