package com.example.dagger2demo.myComponent;

import com.example.dagger2demo.ApplicationScope;
import com.example.dagger2demo.activitys.MainActivity;
import com.example.dagger2demo.activitys.SecondActivity;

import dagger.Component;

@ApplicationScope
@Component(modules = GsonModule.class,dependencies = SwordmanComponent.class)
public interface ActivityComponent {

    void inject(MainActivity activity);
    void inject(SecondActivity activity);
}
