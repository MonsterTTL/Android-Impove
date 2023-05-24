package com.example.zidingyi;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VelocityTrackerView extends View implements GestureDetector.OnGestureListener {
    private static final String TAG = "VelocityTrackerView";
    VelocityTracker velocityTracker;
    GestureDetector gestureDetector;

    public VelocityTrackerView(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context,this);
    }

    public VelocityTrackerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VelocityTrackerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VelocityTrackerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                velocityTracker = VelocityTracker.obtain();//获取速度追踪对象
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);//计算1000ms内的平均速度
                int xVelocity = (int) velocityTracker.getXVelocity();
                int yVelocity = (int) velocityTracker.getYVelocity();
                Log.d(TAG, "xVelocity is : "+ xVelocity);
                Log.d(TAG, "yVelocity is : "+ yVelocity);
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.clear();
                velocityTracker.recycle();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
