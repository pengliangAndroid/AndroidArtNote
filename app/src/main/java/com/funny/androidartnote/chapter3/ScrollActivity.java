package com.funny.androidartnote.chapter3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.funny.androidartnote.R;

public class ScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        HorizontalScrollViewEx scrollViewEx = findViewById(R.id.scrollViewEx);
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getResources().getDisplayMetrics().heightPixels;

        //LinearLayout linearLayout = new LinearLayout(this);

        for (int i = 0; i < 3; i++) {


            TextView textView1 = new TextView(this);
            textView1.setText("item" + i);
            textView1.setGravity(Gravity.CENTER);
            int color = Color.RED;
            if(i == 1){
                color = Color.BLUE;
            }else if(i == 2){
                color = Color.CYAN;
            }
            textView1.setBackgroundColor(color);
            textView1.setLayoutParams(new ViewGroup.LayoutParams(widthPixels/3,heightPixels));

            /*int color = Color.RED;
            if(i == 1){
                color = Color.BLUE;
            }else if(i == 2){
                color = Color.CYAN;
            }
            View rootView = getLayoutInflater().inflate(R.layout.item_01, scrollViewEx,false);
            TextView textView = rootView.findViewById(R.id.tv_text);
            textView.setBackgroundColor(color);
            rootView.getLayoutParams().width = widthPixels/3;*/

            scrollViewEx.addView(textView1);
        }

    }
}
