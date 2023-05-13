package com.example.annotationdemo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AnnotationTest {

    @GET(value = "https://")
    public String getIpMsg(){
        return "";
    }

    @GET(value = "http://")
    public String getIp(){
        return "";
    }
}

