package com.example.dagger2demo.myEngines;

import javax.inject.Inject;

public class GasolineEngine extends Engine{
    @Override
    public String run() {
        return "汽油发动机-工作!";
    }
}
