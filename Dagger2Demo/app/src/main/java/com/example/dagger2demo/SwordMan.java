package com.example.dagger2demo;

import javax.inject.Inject;

public class SwordMan {

    @Inject
    public SwordMan(){

    }

    public String fighting(){
        return "欲为大树，莫于草争";
    }
}
