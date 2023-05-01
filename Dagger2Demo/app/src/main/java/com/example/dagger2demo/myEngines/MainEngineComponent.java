package com.example.dagger2demo.myEngines;

import com.example.dagger2demo.MainActivity;

import dagger.Component;
import dagger.Module;

@Component(modules = {EngineModule.class})
public interface MainActivityEngineComponent {
    public void inject(MainActivity activity);
}
