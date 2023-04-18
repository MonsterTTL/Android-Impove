package com.example.zidingyi;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

public class CustomView extends View {
    private static final String TAG = "CustomView";
    private int lastX;//之前的X坐标
    private int lastY;//之前的Y坐标
    public CustomView(Context context, AttributeSet attrs,int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context,AttributeSet attrs){
        super(context, attrs);
    }

    public CustomView(Context context){
        super(context);
    }

    public boolean onTouchEvent(MotionEvent motionEvent){
        //只要有触摸事件，该两行代码就会执行记录下坐标
        int x = (int) motionEvent.getX();//
        int y = (int) motionEvent.getY();//

        //手指的移动是快于绘图重新绘制的
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN://事件按下
                lastX = x;
                lastY = y;
                Toast.makeText(getContext(), "down", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onTouchEvent: X="+x+" Y="+y);
                break;
            case MotionEvent.ACTION_MOVE:
                //计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                //重新绘图 分别是左，上，右，下相对坐标中绘制
                layout(getLeft()+offsetX,getTop()+offsetY,
                        getRight()+offsetX,getBottom()+offsetY);
                ConstraintLayout.LayoutParams params =
                        (ConstraintLayout.LayoutParams)getLayoutParams(); //疑问：当不添加以下代码时移动myFloat将会出现跳变
                //似乎是调用layout方法时并不会改变布局参数中的数据
                //调用layout()方法不会改变布局参数中的参数。
                //layout()方法是View类的一个方法，用于设置视图的位置。
                // 它需要传入四个参数：左边缘的坐标、上边缘的坐标、右边缘的坐标和下边缘的坐标。
                // 这些坐标的值应该是相对于视图的父视图的坐标系的，而不是相对于屏幕的坐标系。
                //当我们调用layout()方法时，它会根据传入的坐标值，直接改变视图在其父视图中的位置，而不会涉及到布局参数。
                // 布局参数是在视图初始化或调用setLayoutParams()方法时设置的，
                // 它描述了视图在布局中的一些特性，例如视图的宽度、高度、外边距等。这些布局参数不会在调用layout()方法时被修改。
                //需要注意的是，虽然调用layout()方法可以改变视图在其父视图中的位置，但它并不是推荐的方式。
                // 通常情况下，应该通过修改视图的布局参数来控制其位置，以便实现更加灵活和可维护的布局。
                params.leftMargin += offsetX;
                params.topMargin += offsetY;
                setLayoutParams(params);
                break;
            case MotionEvent.ACTION_UP://事件抬起
                Toast.makeText(getContext(), "up", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;//表示该事件已被处理并且消费掉了，不再传递给其他的视图
    }
}
