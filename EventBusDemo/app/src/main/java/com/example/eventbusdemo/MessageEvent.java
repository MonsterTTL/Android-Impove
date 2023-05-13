package com.example.eventbusdemo;

//自定义的Event事件
public class MessageEvent {
    private String message;
    public MessageEvent(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}

