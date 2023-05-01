package com.example.dagger2demo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

public class EngineInterface {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Gasoline{

    }


    //两个自定义
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Diesel{

    }
}
