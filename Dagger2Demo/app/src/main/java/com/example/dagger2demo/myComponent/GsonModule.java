package com.example.dagger2demo.myComponent;

import com.example.dagger2demo.ApplicationScope;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {
    @ApplicationScope
    @Provides
    public Gson provideGson(){
        return new Gson();
    }
}
