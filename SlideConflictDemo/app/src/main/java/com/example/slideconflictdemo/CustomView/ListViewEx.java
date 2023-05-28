package com.example.slideconflictdemo.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class ListViewEx extends ListView {
    private static final String TAG = "ListViewEx";
    private HorizontalScrollViewEx2 mHorizontalScrollView;
    //记录上一次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;


    public ListViewEx(Context context) {
        super(context);
    }

    public ListViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:{
                mHorizontalScrollView.requestDisallowInterceptTouchEvent(true);//先不允许父容器拦截
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if(Math.abs(deltaX) > Math.abs(deltaY)){
                    mHorizontalScrollView.requestDisallowInterceptTouchEvent(false);//如果是横向滑动的话，就允许父布局拦截了
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                break;
            }
            default:
                break;
        }
        mLastY = y;
        mLastX = x;
        return super.dispatchTouchEvent(ev);//这是为了不影响ListView原本的功能
    }
}
