package com.example.mvpandmvvmdemo.middler;

import com.example.mvpandmvvmdemo.interfaces.IpInfoContract;
import com.example.mvpandmvvmdemo.interfaces.LoginContract;
import com.example.mvpandmvvmdemo.interfaces.LoginTask;

public class LoginInPresenter implements LoginContract.Presenter {
    //Presenter类只是一个桥梁，一般不要放业务

    private LoginTask task;
    private LoginContract.BindView addedView;

    @Override
    public void verify() {
        if(task == null || addedView == null){
            addedView.FailedToLogin();
            return;
        }
        if(task.verify()){
            addedView.Success();
        }else{
            addedView.FailedToLogin();
        }
    }

    public void setTask(LoginTask mTask){
        task = mTask;
    }

    public void setView(LoginContract.BindView view){
        addedView = view;
    }

    public LoginTask getTask(){
        return task;
    }
}
