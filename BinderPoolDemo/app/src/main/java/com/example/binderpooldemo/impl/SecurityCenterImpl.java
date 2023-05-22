package com.example.binderpooldemo.impl;

import com.example.binderpooldemo.aidl.ISecurityCenter;

public class SecurityCenterImpl extends ISecurityCenter.Stub {

    private static final char SECRET_CODE = '^';//异或加密


    public String encrypt(String content) {
        char[] chars = content.toCharArray();
        for(int i = 0;i < chars.length;i++){
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }


    public String decrypt(String key) {
        return encrypt(key);
    }
}
