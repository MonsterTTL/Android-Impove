package com.example.dagger2demo.myEngines;

import com.example.dagger2demo.EngineInterface;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EngineModule {

    @Provides //Inject标注无法为抽象类指定，这时只能通过Module+Providers
    //@Named("Gasoline")
    @EngineInterface.Gasoline
    public Engine provideGasolineEngine(){
        return new GasolineEngine();
    }

    @Provides
    //@Named("Diesel") //多个相同的依赖提供，用Named限定符来区分
    @EngineInterface.Diesel
    public Engine provideDieselEngine(){
        return new DieselEngine();
    }

    @Singleton
    @Provides
    public Gson provideGson(){
        return new Gson();
    }

}
