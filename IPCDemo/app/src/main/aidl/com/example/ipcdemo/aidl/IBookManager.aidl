package com.example.ipcdemo.aidl;

import com.example.ipcdemo.aidl.Book;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}