package com.funny.androidartnote.chapter11;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.funny.androidartnote.R;

public class HandlerThreadActivity extends AppCompatActivity {
    private static final String TAG = "HandlerThreadActivity";

    HandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);

        handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i(TAG,"msg.what=" + msg.what);
                Log.i(TAG,"threadName=" + Thread.currentThread().getName());
            }
        };
        handler.sendEmptyMessage(1000);

        Intent intent = new Intent(this,LocalIntentService.class);
        intent.putExtra("data","test1");
        startService(intent);

        intent.putExtra("data","test2");
        startService(intent);

        intent.putExtra("data","test3");
        startService(intent);
    }



}
