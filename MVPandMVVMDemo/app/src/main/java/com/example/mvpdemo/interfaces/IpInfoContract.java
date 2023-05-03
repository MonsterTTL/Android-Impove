package com.example.mvpandmvvmdemo.interfaces;

import com.example.mvpandmvvmdemo.modules.IpInfo;

//契约接口类
public interface IpInfoContract {

    interface Presenter{
        void getIpInfo(String ip);
    }

    //这个好像是用来刷新UI的
    interface View extends BaseView<Presenter>{
        void setIpInfo(IpInfo ipInfo);//更新UI上的详细信息
        void showLoading();//显示当前正在刷新
        void hideLoading();//隐藏加载
        void showError();//显示加载错误
        boolean isActive();//判断Fragment是否被添加到Activity上

    }
}
