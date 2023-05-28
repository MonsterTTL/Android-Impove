package com.example.slideconflictdemo.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

//继承了LinearLayout的横向滑动条
public class HorizontalScrollViewEx extends LinearLayout {
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;
    private int mLastX = 0;
    private int mLastY = 0;
    //子元素的相关信息
    private int mChildrenSize = 3;
    private int mChildWidth;
    private int mChildIndex;
    private Scroller mScroller;//弹性滚动对象-仅能滚动内容
    private VelocityTracker mVelocityTracker;
    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HorizontalScrollViewEx(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setInfo(int mChildrenSize,int mChildWidth){
        this.mChildWidth = mChildWidth;
        this.mChildrenSize = mChildrenSize;
    }

    private void init(){//初始化速度追踪器和弹性滚动对象
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();

    }


    //先是外部拦截法
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();//获取触摸点的坐标
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    intercepted = true; //如果还没有完成滚动就要进行拦截
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;//获取滑动差值
                int deltaY = y - mLastYIntercept;
                if(Math.abs(deltaX) > Math.abs(deltaY)){
                    //如果横向滑动距离大于纵向滑动距离-即判定为水平滑动的话-那么由父布局拦截处理了
                    intercepted = true;
                }else{
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;//一次完整的事件序列结束后，重置状态
                break;
            default:
                break;
        }
        //更新状态
        mLastX = x;
        mLastY = y;
        mLastYIntercept = y;
        mLastXIntercept = x;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();//停止滚动动画
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                scrollBy(-deltaX,0);
                break;
            }
            case MotionEvent.ACTION_UP:{
                int scrollX = getScrollX();
                int scrollToChildIndex = scrollX / mChildWidth;
                mVelocityTracker.computeCurrentVelocity(1000);
                float velocityX = mVelocityTracker.getXVelocity();//获取横向滑动速度
                if(Math.abs(velocityX) >= 50){
                    mChildIndex = velocityX > 0 ? mChildIndex - 1:mChildIndex + 1;
                }else{
                    mChildIndex = (scrollX + mChildWidth/2) / mChildWidth;
                }
                mChildIndex = Math.max(0,Math.min(mChildIndex,mChildrenSize-1));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScrollBy(dx,0);
                mVelocityTracker.clear();
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
        mScroller.startScroll(getScrollX(),0,dx,0,1000);
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
