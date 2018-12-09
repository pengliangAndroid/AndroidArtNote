package com.funny.androidartnote.chapter4;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;

import com.funny.androidartnote.R;

/**
 * @author pengl
 */

public class AnimActivity extends Activity {

    int lastX,lastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        final Button btnStart = findViewById(R.id.btn_start);
        /*btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*ObjectAnimator.ofFloat(btnStart,"translationX",0,200).setDuration(1000)
                        .start();*//*

                //Toast.makeText(AnimActivity.this,"test",Toast.LENGTH_SHORT).show();

            }
        });*/
        final int touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        btnStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int deltaX = x - lastX;
                        int deltaY = y - lastX;
                        if(deltaX > touchSlop || deltaY > touchSlop) {
                            ObjectAnimator.ofFloat(btnStart, "translationX", lastX, x)
                                    .start();
                            ObjectAnimator.ofFloat(btnStart, "translationY", lastY, y)
                                    .start();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }

                lastX = x;
                lastY = y;
                return true;
            }
        });
    }


    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }*/


}
