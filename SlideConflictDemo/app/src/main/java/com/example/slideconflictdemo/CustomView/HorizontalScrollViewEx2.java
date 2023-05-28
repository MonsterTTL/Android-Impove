package com.example.slideconflictdemo.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

//这是采取内部拦截法的
public class HorizontalScrollViewEx2 extends LinearLayout {//HorizontalScrollViewEx的修改，这里只包含修改的内容
    private int mLastX;
    private int mLastY;
    private Scroller mScroller;

    public HorizontalScrollViewEx2(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HorizontalScrollViewEx2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(){
        mScroller = new Scroller(getContext());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int action = ev.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            mLastX = x;
            mLastY = y;
            if(!mScroller.isFinished()){
                mScroller.abortAnimation();
                return true;
            }
            return false;
        }else{
            return true;//除了Down以外的事件，如果父容器还可以拦截的话，说明子View允许父容器拦截了，返回true进行拦截
        }

    }
}
