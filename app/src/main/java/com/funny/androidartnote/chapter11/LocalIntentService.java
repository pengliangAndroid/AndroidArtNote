package com.funny.androidartnote.chapter11;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pengl
 */

public class LocalIntentService extends IntentService {
    private static final String TAG = "LocalIntentService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate" );
    }

    public LocalIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SystemClock.sleep(3000);

        Log.i(TAG,"msg=" + intent.getStringExtra("data"));
        Log.i(TAG,"threadName=" + Thread.currentThread().getName());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.i(TAG,"date=" + dateFormat.format(new Date()));
    }
}
