package com.iflytek.voicedemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class MainActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        initView();
        requestPermissions();
    }

    private void initView() {
        TextView tipTv = (TextView) findViewById(R.id.tip);
        String buf = "当前APPID为：" +
                getString(R.string.app_id) + "\n" +
                getString(R.string.example_explain);
        tipTv.setText(buf);
        // 语音转写
        findViewById(R.id.iatBtn).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, IatDemo.class));
        });
        // 语法识别
        findViewById(R.id.asrBtn).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AsrDemo.class));
        });
        // 语义理解
        findViewById(R.id.nlpBtn).setOnClickListener(v -> {
            showTip("请登录：http://www.xfyun.cn/ 下载aiui体验吧！");
        });
        // 语音合成
        findViewById(R.id.ttsBtn).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, TtsDemo.class));
        });
        // 语音评测
        findViewById(R.id.iseBtn).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, IseDemo.class));
        });
        // 人脸识别
        findViewById(R.id.faceBtn).setOnClickListener(v -> {
            showTip("请登录：http://www.xfyun.cn/ “人脸识别”下载体验吧！");
        });
    }

    private Toast mToast;

    private void showTip(final String str) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
        mToast.show();
    }

    private void requestPermissions() {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA
                }, 0x0010);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
