package com.example.annotationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Method;
import com.example.annotations_lib.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_1)
    TextView tv_textView;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    private void Annotas_Test() {
        Method[] methods = AnnotationTest.class.getMethods();
        for(Method method:methods){
            GET get = method.getAnnotation(GET.class);
            if(get != null)
                Log.d(TAG, "onCreate: "+get.value());
        }
    }


}