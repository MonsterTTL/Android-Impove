package com.example.ipcdemo3contentprovider.cps;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Entity;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.ipcdemo3contentprovider.RoomDataBas.Book;
import com.example.ipcdemo3contentprovider.RoomDataBas.DataBaseDao;
import com.example.ipcdemo3contentprovider.RoomDataBas.DemoDataBase;
import com.example.ipcdemo3contentprovider.RoomDataBas.User;

import java.util.List;

public class BookProvider extends ContentProvider {

    private static final String TAG = "BookProvider";
    public static final String AUTHORITY = "com.example.cps.BookProvider";

    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://"+AUTHORITY
    +"/book");

    public static final Uri USER_CONTENT_URI = Uri.parse("content://"+AUTHORITY
            +"/user");
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);//用于匹配URI的工具类


    static{
        sUriMatcher.addURI(AUTHORITY,"book",BOOK_URI_CODE);//说明AUTHORITY+"/book"的组合uri将会返回BOOK_URI_CODE的值
        sUriMatcher.addURI(AUTHORITY,"user",USER_URI_CODE);
    }
    public DemoDataBase mDataBase ; //数据库
    public DataBaseDao mDao;//数据访问接口

    public BookProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType");
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert");
        String tableName = getTableName(uri);
        if(tableName == null){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        if(tableName.equals("books")){
            Book book = new Book(values.getAsInteger("book_id"),values.getAsString("book_name"));
            mDao.insertBook(book);
        }else{
            User user = new User(values.getAsInteger("user_id"), values.getAsString("user_name"),
                    values.getAsBoolean("user_isMale"));
            mDao.insertUser(user);
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate, currentThread is :"+Thread.currentThread().getName());
        //初始化Room数据库 和 数据访问接口
        mDataBase = Room.databaseBuilder(getContext(),DemoDataBase.class,"DemoData").build();
        mDao = mDataBase.getMyDao();
        return true;
    }

    @Override //这是一个比较死的查找方法，要想更加灵活的话可以修改Dao中的Query语句并配置参数
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query, currentThread is :"+Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if(tableName == null){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        Cursor cursor = null;
        if(selection == null){
            if(tableName.equals("books")){
                try{
                    cursor = mDao.loadAllBooksWithCursor();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                try{
                    cursor = mDao.loadAllUsersWithCursor();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return cursor;

    }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d(TAG, "update");
        return 0;
    }

    private String getTableName(Uri uri){
        String tableName = null;
        switch (sUriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tableName = "books";
                break;
            case USER_URI_CODE:
                tableName = "users";
                break;
            default:
                break;
        }
        return tableName;
    }
}