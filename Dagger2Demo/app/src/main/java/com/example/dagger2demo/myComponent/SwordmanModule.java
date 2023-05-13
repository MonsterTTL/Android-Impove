package com.example.dagger2demo.myComponent;

import com.example.dagger2demo.SwordMan;

import dagger.Module;
import dagger.Provides;

@Module
public class SwordmanModule {

    @Provides
    public SwordMan provideSwordman(){
        return new SwordMan();
    }
}

