package com.example.ipcdemo3contentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ipcdemo3contentprovider.RoomDataBas.Book;
import com.example.ipcdemo3contentprovider.RoomDataBas.DataBaseDao;
import com.example.ipcdemo3contentprovider.RoomDataBas.DemoDataBase;
import com.example.ipcdemo3contentprovider.RoomDataBas.User;
import com.example.ipcdemo3contentprovider.cps.BookProvider;
import com.example.ipcdemo3contentprovider.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BookProvider";
    private static int Uid = 1;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);


        Thread.currentThread().setName("MainActivity's UI thread!"); //为了防止搞混两个进程中的主线程
        Log.d(TAG, "Activity's currentThread is "+Thread.currentThread().getName());
        Uri uri = Uri.parse("content://com.example.cps.BookProvider");//将String转为Uri
        //Uri格式为content:// + authorities

        binding.btQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = getContentResolver().query(BookProvider.USER_CONTENT_URI,null,
                        null,null,null);
                while(cursor.moveToNext()){
                    Log.d(TAG, "Man's name is:"+cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    Log.d(TAG, "Id is:"+cursor.getInt(cursor.getColumnIndexOrThrow("id")));

                }
            }
        });

        binding.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edName.getText().toString();
                Book book = new Book(Uid,name);
                User user = new User(Uid,name,true);
                Uid++;
                ContentValues values = new ContentValues();
                values.put("book_id",book.uid);
                values.put("book_name",book.name);
                values.put("user_id",user.uid);
                values.put("user_name",user.name);
                values.put("user_isMale",user.isMaile);
                getContentResolver().insert(BookProvider.USER_CONTENT_URI,values);
                getContentResolver().insert(BookProvider.BOOK_CONTENT_URI,values);


            }
        });

        binding.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().delete(BookProvider.BOOK_CONTENT_URI,null,null);
            }
        });

    }

    private void initId(){

    }
}