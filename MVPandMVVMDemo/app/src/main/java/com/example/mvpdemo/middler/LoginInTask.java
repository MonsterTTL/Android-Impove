package com.example.mvpandmvvmdemo.middler;

import com.example.mvpandmvvmdemo.interfaces.LoginTask;

import java.util.AbstractMap;
import java.util.Map;

public class LoginInTask implements LoginTask<String,Integer> {

    private static String DEFAULT_NAME = "user";
    private static Integer DEFAULT_PASSWORD = 123456;
    private String userName;
    private Integer password;

    @Override
    public void Inputs(String username, Integer password) {
        userName = username;
        this.password = password;
    }

    public String getUserName(){
        return userName;
    }

    public Integer getPassword(){
        return password;
    }

    @Override
    public boolean verify() {
        if(userName == null || password == null){
            return false;
        }else if(userName.equals(DEFAULT_NAME) && password.equals(DEFAULT_PASSWORD)){
            return true;
        }else{
            return false;
        }
    }


}
