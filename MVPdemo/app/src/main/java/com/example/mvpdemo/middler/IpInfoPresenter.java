package com.example.mvpdemo.middler;

import android.os.CountDownTimer;

import com.example.mvpdemo.interfaces.IpInfoContract;
import com.example.mvpdemo.interfaces.MyFeedBack;
import com.example.mvpdemo.interfaces.NetTask;
import com.example.mvpdemo.modules.IpInfo;

public class IpInfoPresenter implements IpInfoContract.Presenter, MyFeedBack<IpInfo> {

    private NetTask netTask;//处理业务的一个类
    private IpInfoContract.View addTaskView;//绑定的View，就是Presenter会通知addTaskView进行UI的刷新

    //为Presenter绑定网络业务和需要刷新的UI
    public IpInfoPresenter(IpInfoContract.View addTaskView,NetTask netTask){
        this.addTaskView = addTaskView;
        this.netTask = netTask;
    }

    @Override
    public void getIpInfo(String ip) {
        netTask.execute(ip,this);
    }

    @Override
    public void onSuccess(IpInfo ipInfo) {
        if(addTaskView.isActive()){
            addTaskView.showLoading();
            new CountDownTimer(5000,1000){
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    addTaskView.hideLoading();
                    addTaskView.setIpInfo(ipInfo);
                }
            }.start();
        }
    }

    @Override
    public void onFailed() {
        if(addTaskView.isActive()){
            addTaskView.showError();
            addTaskView.hideLoading();
        }
    }
}
