package com.example.annotationdemo;

import java.lang.reflect.Method;

public class AnnotationProcessor {
    public static void main(String[] args) {
        Method[] methods = AnnotationTest.class.getMethods();
        for(Method method:methods){
            GET get = method.getAnnotation(GET.class);
            System.out.println(get.value());
        }
    }
}
