package com.example.mvvmdemo;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ObSwordsMan extends BaseObservable {
    private String name;
    private String level;

    public ObSwordsMan(String name,String level){
        this.name = name;
        this.level = level;
    }

    @Bindable
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
        notifyPropertyChanged(BR.name);//这里的字段就是加了@Bindable生成的
    }

    @Bindable //加个Bindable说明会生成对应字段
    public String getLevel(){
        return level;
    }

    public void setLevel(String level){
        this.level = level;
        notifyPropertyChanged(BR.level);
    }
}
