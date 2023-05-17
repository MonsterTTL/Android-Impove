package com.example.ipcdemo3contentprovider.RoomDataBas;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DataBaseDao {

    @Insert
    public void insertUser(User... users);

    @Insert
    public void insertBook(Book... books);

    @Update
    public void updateUser(User... users);

    @Update
    public void updateBook(Book... books);

    @Delete
    public void deleteUser(User... users);

    @Delete
    public void deleteBook(Book... books);

    @Query("SELECT * FROM Users")
    public User[] loadAllUsers();

    @Query("SELECT * FROM Users")
    public Cursor loadAllUsersWithCursor();

    @Query("SELECT * FROM Books")
    public Cursor loadAllBooksWithCursor();

    @Query("SELECT * FROM Books")
    public Book[] loadAllBooks();
}
