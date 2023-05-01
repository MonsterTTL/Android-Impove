package com.example.dagger2demo.db;

import com.google.gson.annotations.SerializedName;

public class Man {
    @SerializedName("name")
    public String name;
    @SerializedName("age")
    public int age;
}
