package com.example.zidingyi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ScrollCustomView extends View {

    private double LastX;
    private double LastY;
    public ScrollCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScrollCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ScrollCustomView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        double x = event.getX();
        double y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                LastX = x;
                LastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                double offsetX = x - LastX;
                double offsetY = y - LastY;
                ((View)getParent()).scrollBy(-(int) (offsetX),-(int) (offsetY));
                break;
            default:
                break;
        }
        return true;
    }
}
