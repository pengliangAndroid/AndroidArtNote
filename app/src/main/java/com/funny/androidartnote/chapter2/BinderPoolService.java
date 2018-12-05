package com.funny.androidartnote.chapter2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author pengl
 */
public class BinderPoolService extends Service {
    private static final String TAG = "BinderPoolService";

    private IBinderPool binderPool = new BinderPool.BinderPoolImpl();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binderPool.asBinder();
    }
}
