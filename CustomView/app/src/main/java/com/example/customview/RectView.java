package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
//自定义View 一个双色矩形
public class RectView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mFColor = Color.RED;
    private int mSColor = Color.YELLOW;
    public RectView(Context context) {
        super(context);
        initDraw();
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.RectView);
        //提取RectView属性集合的所有属性参数
        //提取出相应的属性颜色
        mFColor = mTypedArray.getColor(R.styleable.RectView_left_rect_color,Color.RED);
        mSColor = mTypedArray.getColor(R.styleable.RectView_right_rect_color,Color.YELLOW);

        mTypedArray.recycle();//获取完资源后要及时回收
        initDraw();
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw();
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initDraw();
    }

    private void initDraw(){
        mPaint.setStrokeWidth(1.5f);
    }

    //未考虑padding的情况
    /**
     * 在绘制中没有考虑padding
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        //canvas提供了绘制的方法，Paint提供了如何绘制
        canvas.drawRect(0,0,width/2,height,mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(width/2,0,width,height,mPaint);
    }**/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int padLeft = getPaddingLeft();
        int padRight = getPaddingRight();
        int padTop = getPaddingTop();
        int padBottom = getPaddingBottom();
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0,0,width,height,mPaint);
        width = width-padRight-padRight;
        height = height-padBottom;
        mPaint.setColor(mFColor);
        //canvas提供了绘制的方法，Paint提供了如何绘制
        canvas.drawRect(0+padLeft,0+padTop,padLeft+width/2,height,mPaint);
        mPaint.setColor(mSColor);
        canvas.drawRect(width/2+padLeft,0+padTop,padLeft+width,height,mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//超类中的该方法将会实现exactly 和 MatchParent效果

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);//获取父级期望的模式
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);//获取父级期望的尺寸
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //如果期望的宽和高的测量模式都是AT模式 -- 对应俩wrap-content
        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(600,600);//用来设置宽和高的
        }else if (widthSpecMode == MeasureSpec.AT_MOST){//若只有宽是AT模式
            setMeasuredDimension(600,heightSpecSize);
        }else if(heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,600);
        }
    }
}
