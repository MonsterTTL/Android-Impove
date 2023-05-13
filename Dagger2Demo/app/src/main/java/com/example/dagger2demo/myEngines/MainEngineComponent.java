package com.example.dagger2demo.myEngines;

import com.example.dagger2demo.activitys.MainActivity;
import com.example.dagger2demo.myComponent.SwordmanComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {EngineModule.class},dependencies = SwordmanComponent.class)
public interface MainEngineComponent {
    public void inject(MainActivity activity);

}
