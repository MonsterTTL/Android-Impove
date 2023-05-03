package com.example.mvpandmvvmdemo.interfaces;

//这是一个简易的登录的契约接口
public interface LoginContract {


    interface Presenter{
        void verify();
    }

    interface BindView{
        void FailedToLogin();
        void Success();
    }
}
