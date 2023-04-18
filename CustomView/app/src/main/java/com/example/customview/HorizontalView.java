package com.example.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontalView extends ViewGroup {

    private int lastInterceptX;
    private int lastInterceptY;
    private int lastX;
    private int lastY;
    private int currentIndex = 0;
    private int childWidth = 0;
    private Scroller scroller;

    private VelocityTracker tracker; //速率捕获器，用于帮助测量滑动速度？

    public HorizontalView(Context context) {
        super(context);
        init();
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        scroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = 0;
        View Child;
        for(int i = 0;i < childCount;i++){
            Child = getChildAt(i);
            if(Child.getVisibility() != View.GONE){//若可见度不为GONE
                int width = Child.getMeasuredWidth();//获取测量宽度
                childWidth = width;
                Child.layout(left,0,left+width,Child.getMeasuredHeight());
                left += width;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);//measure子元素

        if(getChildCount() == 0){//若ViewGroup内没有子元素 -- 设置尺寸为0
            setMeasuredDimension(0,0);
        }else if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            //若均为warp属性
            View firstChild = getChildAt(0);
            int childWidth = firstChild.getMeasuredWidth();
            int childHeight = firstChild.getMeasuredHeight();
            setMeasuredDimension(childWidth*getChildCount(),childHeight);
        }else if(widthMode == MeasureSpec.AT_MOST){
            //若宽度模式为wrap模式
            int childWidth = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(childWidth*getChildCount(),heightSize);
        }else if(heightMode == MeasureSpec.AT_MOST){
            int childHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(widthSize,childHeight);
        }
    }

    //拦截触摸事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int)ev.getX();
        int y = (int)ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                if(!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastInterceptX;
                int deltaY = y - lastInterceptY;
                if(Math.abs(deltaX) - Math.abs(deltaY) > 0){ //若水平滑动的距离大于垂直滑动的距离
                    //即若为水平滑动
                    intercept = true; //设置捕获标志位为真

                    //总结一下，就是如果是水平滑动那么就由ViewGroup捕获并且处理
                    //并且根据捕获的机制来说，之后的一系列事件也将由ViewGroup处理
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        lastX = x;
        lastY = y;
        lastInterceptX = x;
        lastInterceptY = y;
        return intercept;
    }

    @Override //弹性滑动
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                scrollBy(-deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                int distance = getScrollX() - currentIndex * childWidth;
                if(Math.abs(distance) > childWidth/2){ // 若滑动的距离已经超过view宽度的1/2
                    if(distance > 0){ //如果是向左滑动--那么当先项+1
                        currentIndex++;
                    }else{
                        currentIndex--;
                    }
                }else{
                    tracker.computeCurrentVelocity(1000);
                    float xV = tracker.getXVelocity();//计算trackerX轴的速度
                    if(Math.abs(xV) > 50){
                        if(xV > 0){ //方向以右边为正，但是它记录的是手指滑动的速度，即若手指往左移，View向右划
                            currentIndex--;
                        }else{
                            currentIndex++;
                        }
                    }
                }
                currentIndex = currentIndex < 0 ? 0: currentIndex > getChildCount() - 1 ?
                        getChildCount() - 1 : currentIndex;//保证当前的坐标没有越界
                smoothScrollTo(currentIndex * childWidth,0);
                tracker.clear();
                break;

        }
        lastX = x;
        lastY = y;
        return super.onTouchEvent(event);
    }

    public void smoothScrollTo(int destX,int destY){
        scroller.startScroll(getScrollX(),getScrollY(),destX - getScrollX(), destY - getScrollY(),
                1000);
        invalidate();

    }

    public void computeScroll(){
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

    public void init(){
        scroller = new Scroller(getContext());
        tracker = VelocityTracker.obtain();
    }
}
