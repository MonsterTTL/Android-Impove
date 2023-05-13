package com.example.mvvmdemo;

import androidx.databinding.BindingConversion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    @BindingConversion
    public static String convertData(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
