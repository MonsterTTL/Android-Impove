package com.example.ipcdemo3contentprovider.RoomDataBas;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class,User.class},version = 2)
public abstract class DemoDataBase extends RoomDatabase {
    public abstract DataBaseDao getMyDao();
}
