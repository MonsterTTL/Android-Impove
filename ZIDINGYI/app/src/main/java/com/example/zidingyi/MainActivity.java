package com.example.zidingyi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.zidingyi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MyFloat myFloat;
    CustomView customView;
    ScrollerCustomView scrollerCustomView;

    GestureDetector mGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        //binding.scrollerCustomView2.smoothScrollTo(50,0); //通过scroller 配合 computeScroll实现平滑滚动
        //但是是整个画布移动

        //属性动画
//        ObjectAnimator am1 = ObjectAnimator.ofFloat(binding.customView,"translationX",
//                0.f,200.0f,0f);
//        ObjectAnimator am2 = ObjectAnimator.ofFloat(binding.customView,"rotationX",
//                0.0f,90.0f,0.0f);
//        ObjectAnimator am3 = ObjectAnimator.ofFloat(binding.customView,"scaleX",
//                1.0f,2.0f);
//        //动画集
//        AnimatorSet set = new AnimatorSet();
//        set.setDuration(1000);
//        set.play(am1).with(am3).after(am2);//动画am1和am3同时先执行，然后执行am2动画
//        set.start();
//
//        //通过PropertyValuesHolder实现同时多个动画
//        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX",1.0f,1.5f);
//        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("rotationX",0.0f,
//                90.0f,0.0f);
//        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha",1.0f,
//                3.0f,1.0f);
//        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(binding.myfloat,holder1,holder2,holder3);
//        animator.setDuration(2000).start();
//
//        Animator animator1 = AnimatorInflater.loadAnimator(this,R.animator.scale);
//        animator1.setTarget(binding.scrollCustomView);
//        animator1.start();
        //binding.myfloat.setAnimation(AnimationUtils.loadAnimation(this,R.anim.translate));
        binding.myfloat.smoothScrollTo(-600,0);
        int mindp = ViewConfiguration.get(getApplicationContext()).getScaledTouchSlop();
        Log.d(TAG, "mindp: "+mindp);
        //binding.customView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.translate));
    }

}