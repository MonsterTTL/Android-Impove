package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.Image;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleBar extends RelativeLayout {

    private ImageView iv_titlebar_left;
    private ImageView iv_titlebar_right;
    private TextView tv_titlebar_title;
    private RelativeLayout layout_titlebar_rootlayout;
    private int mColor = Color.BLUE;
    private int mTextColor = Color.WHITE;
    private String mTitle = "Title";
    public TitleBar(Context context) {
        super(context);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.TitleBar);
        mColor = mTypedArray.getColor(R.styleable.TitleBar_title_bg,Color.BLUE);
        mTextColor = mTypedArray.getColor(R.styleable.TitleBar_title_text_color,Color.WHITE);
        mTitle = mTypedArray.getString(R.styleable.TitleBar_title_text);
        mTypedArray.recycle();
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.titlebar_layout,this,true);
        iv_titlebar_left = findViewById(R.id.iv_titlebar_left);
        iv_titlebar_right = findViewById(R.id.iv_titlebar_right);
        tv_titlebar_title = findViewById(R.id.it_titlebar_title);
        layout_titlebar_rootlayout = findViewById(R.id.layout_titlebar_rootlayout);
        //设置背景颜色
        layout_titlebar_rootlayout.setBackgroundColor(mColor);
        //设置标题的字体颜色
        tv_titlebar_title.setTextColor(mTextColor);
        setTitle(mTitle);
    }

    public void setTitle(String titleName){//设置标题内容
        if(!TextUtils.isEmpty(titleName)){
            tv_titlebar_title.setText(titleName);
        }
    }

    //设置左右图标的监听器
    public void setLeftListener(OnClickListener onClickListener){
        iv_titlebar_left.setOnClickListener(onClickListener);
    }

    public void setRightListener(OnClickListener onClickListener){
        iv_titlebar_right.setOnClickListener(onClickListener);
    }
}
