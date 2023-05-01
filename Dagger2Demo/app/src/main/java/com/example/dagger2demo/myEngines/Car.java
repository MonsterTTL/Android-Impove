package com.example.dagger2demo.myEngines;

import com.example.dagger2demo.EngineInterface;

import javax.inject.Inject;
import javax.inject.Named;

public class Car {
    private Engine engine;

    @Inject
    public Car(/*@Named("Diesel")*/ @EngineInterface.Gasoline Engine engine){
        this.engine = engine;
    }

    public String run(){
        return engine.run();
    }
}
