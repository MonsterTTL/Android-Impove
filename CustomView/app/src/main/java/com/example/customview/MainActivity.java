package com.example.customview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TitleBar mTitleBar;
    ListView iv_one;
    ListView iv_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitleBar = findViewById(R.id.mTitleBar);
        iv_one = findViewById(R.id.iv_one);
        iv_two = findViewById(R.id.iv_two);
        mTitleBar.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "LeftClick", Toast.LENGTH_SHORT).show();
            }
        });

        mTitleBar.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("右侧点击事件")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .create();
                alertDialog.show();
            }
        });
        ArrayList<String> helper = new ArrayList<>();
        String[] arr1;
        for(int i = 0;i < 15 ;i++){
            helper.add(String.valueOf(i));
        }
        arr1 = helper.toArray(new String[0]);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,
                arr1);

        String[] arr2;
        helper.clear();
        for(int i = 0;i < 15;i++){
            helper.add(String.valueOf((char)('a'+i)));
        }
        arr2 = helper.toArray(new String[0]);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,
                arr2);

        iv_one.setAdapter(adapter1);
        iv_two.setAdapter(adapter2);

    }
}