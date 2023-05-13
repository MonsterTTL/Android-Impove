package com.example.abnormalstatedemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    public MutableLiveData<Integer> data = new MutableLiveData<>();

    public MyViewModel() {
        super();
        data.setValue(0);
    }
}
