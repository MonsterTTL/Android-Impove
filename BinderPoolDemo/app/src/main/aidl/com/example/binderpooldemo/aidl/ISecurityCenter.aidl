package com.example.binderpooldemo.aidl;
interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String key);
}