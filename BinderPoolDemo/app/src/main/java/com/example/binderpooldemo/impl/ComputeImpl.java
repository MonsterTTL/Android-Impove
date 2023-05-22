package com.example.binderpooldemo.impl;

import com.example.binderpooldemo.aidl.ICompute;

public class ComputeImpl extends ICompute.Stub {

    public int add(int a, int b) {
        return a+b;
    }
}
