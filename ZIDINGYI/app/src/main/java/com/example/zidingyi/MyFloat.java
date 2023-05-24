package com.example.zidingyi;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyFloat extends FloatingActionButton {
    private static final String TAG = "MyFloat";
    int lastX ;
    int lastY ;
    public MyFloat(@NonNull Context context) {
        super(context);
    }


    public MyFloat(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFloat(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //使用布局参数LayoutParams
    public boolean onTouchEvent(MotionEvent motionEvent){
        //只要有触摸事件，该两行代码就会执行记录下坐标
        int x = (int) motionEvent.getX();//
        int y = (int) motionEvent.getY();//
        //手指的移动是快于绘图重新绘制的
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN://事件按下
                lastX = x;
                lastY = y;
                Log.d(TAG, "DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                //计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                //这方法有问题  -->改进后的方法 将源代码中的 getLeft + offsetX 替换
                ConstraintLayout.LayoutParams layoutParams =
                        (ConstraintLayout.LayoutParams) getLayoutParams();//获取布局参数

                layoutParams.leftMargin = layoutParams.leftMargin + offsetX;
                layoutParams.topMargin = layoutParams.topMargin + offsetY;
                setLayoutParams(layoutParams);
                Log.d(TAG, "MOVE");
                break;
            case MotionEvent.ACTION_UP://事件抬起
                Log.d(TAG, "UP");
                break;
            case MotionEvent.ACTION_BUTTON_PRESS:
                break;
        }

        return true;//表示该事件已被处理并且消费掉了，不再传递给其他的视图
    }
}
