package com.example.zidingyi;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class ScrollerCustomView extends View {
    Scroller mScroller;
    public ScrollerCustomView(Context context) {
        super(context);
        mScroller = new Scroller(context);//初始化Scroller
    }

    public ScrollerCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);//初始化Scroller
    }

    public ScrollerCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScrollerCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //运用scroller滚动的流程:
    //           请求重绘
    //
    //计算
    @Override
    public void computeScroll() {
        super.computeScroll();
        //用于计算滚动量，当返回值为true时，说明滚动尚未完成；否则说明滚动已经完成
        if(mScroller.computeScrollOffset()){
            ((View)getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();//不断进行重新绘制
        }
    }

    public void smoothScrollTo(int destX,int destY){
        int scrollX = getScrollX();
        int delta = destX-scrollX;
        mScroller.startScroll(scrollX,0,delta,0,2000);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
