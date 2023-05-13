package com.example.dagger2demo.myComponent;

import com.example.dagger2demo.SwordMan;

import dagger.Component;

@Component(modules = SwordmanModule.class)
public interface SwordmanComponent {
    SwordMan getSwordman();
}
