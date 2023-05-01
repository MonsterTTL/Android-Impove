package com.example.annotations_lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


    @Retention(RetentionPolicy.CLASS)//保留策略：编译时,说明在运行时我们无法用反射获取信息
    @Target(ElementType.FIELD)//表示修饰字段
    public @interface BindView{
        int value() default 1;
    }

