package com.example.slideconflictdemo.CustomView;

import android.content.Context;
import android.preference.PreferenceActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class StickyLayout extends LinearLayout {
    private static final String TAG = "StickyLayout";
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;
    private int mLastX = 0;
    private int mLastY = 0;
    //子元素的相关信息

    //特有的状态标志位来判断
    private int mHeaderStatue = Statue_EXPAND; //一开始状态为展开
    private boolean isSticky = true; //ListView是否到达顶部 - 一开始状态为到达顶部

    private static final int Statue_EXPAND = 1;//状态为展开
    private static final int Statue_COLLASPLE = 2;//状态为折叠
    private int mTouSlop;
    public TextView mHeader;
    public ListView mListView;
    private Scroller mScroller;//弹性滚动对象-仅能滚动内容
    private VelocityTracker mVelocityTracker;
    public StickyLayout(Context context) {
        super(context);
        init();
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void init(){//初始化速度追踪器和弹性滚动对象
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        mTouSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }


    //先是外部拦截法
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:{
                intercept = false;
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if(mHeaderStatue == Statue_EXPAND  && isSticky == true){
                    intercept = true;
                }else if(mHeaderStatue == Statue_COLLASPLE && deltaY > 0 && isSticky == true){
                    intercept = true;
                }else if(mHeaderStatue == Statue_COLLASPLE && deltaY < 0 && isSticky == true){
                    intercept = false;
                }

                //判断ListView是否到达顶部了
                if(mListView.getFirstVisiblePosition() == 0){
                    View view  = mListView.getChildAt(0);
                    if(view != null && view.getTop() >= 0){
                        isSticky = true;
                    }else{
                        isSticky = false;
                    }
                }else if(mListView.getFirstVisiblePosition() != 0){
                    isSticky = false;
                }

                break;
            }
            case MotionEvent.ACTION_UP:{
                intercept = false;
                break;
            }
            default:
                break;
        }
        mLastYIntercept = y;
        mLastXIntercept = x;
        mLastY = y;
        mLastX = x;
        Log.d(TAG, "finish ScrollY: "+getScrollY());
        Log.d(TAG, "isColl?: "+ mHeaderStatue);
        Log.d(TAG, "isSticky?: "+isSticky);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                scrollBy(0,-deltaY);
                break;
            }
            case MotionEvent.ACTION_UP:{
                if(getScrollY() < 0){
                    smoothScrollBy(0,-getScrollY());
                }else if(getScrollY() >= 300){
                    smoothScrollBy(0,200-getScrollY());//剩余200dp就判断为折叠了
                    mHeaderStatue = Statue_COLLASPLE;//如果滑动到了一定程度，就判定为折叠了
                }
                if(getScrollY() <= 0){
                    mHeaderStatue = Statue_EXPAND;
                }
                break;
            }
            default:
                break;
        }
        mLastX = x;
        mLastY = y;

        return true;
    }

    private void smoothScrollBy(int dx,int dy){
        mScroller.startScroll(getScrollX(),getScrollY(),dx,dy,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}

