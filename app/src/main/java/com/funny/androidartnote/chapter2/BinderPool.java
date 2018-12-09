package com.funny.androidartnote.chapter2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengl
 */
public class BinderPool {
    private static final String TAG = "BinderPool";

    public static final String BOOK_BINDER_NAME = "book";
    public static final String PERSON_BINDER_NAME = "person";

    private static BinderPool sInstance = null;

    private IBinderPool binderPool;

    private Context context;

    private Map<String,IBinder> cacheBinderMap;


    private BinderPool(Context context){
        this.context = context.getApplicationContext();
        cacheBinderMap = new HashMap<>();

        connectBinderService();
    }

    private void connectBinderService() {
        Intent intent = new Intent(context,BinderPoolService.class);
        context.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
    }

    public void disConnectService(){
        if(serviceConnection != null)
            context.unbindService(serviceConnection);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binderPool = IBinderPool.Stub.asInterface(service);

            try {
                binderPool.asBinder().linkToDeath(deathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG,"binderDied");
            binderPool.asBinder().unlinkToDeath(deathRecipient,0);

            cacheBinderMap.clear();
            binderPool = null;

            connectBinderService();
        }
    };

    public static BinderPool getInstance(Context context){
        if(sInstance == null){
            synchronized (BinderPool.class){
                if(sInstance == null)
                    sInstance = new BinderPool(context);
            }
        }

        return sInstance;
    }

    public IBinder queryBinder(String binderName) {
        IBinder binder = null;

        try {
            binder = cacheBinderMap.get(binderName);
            if(binder == null && binderPool != null) {
                binder = binderPool.queryBinder(binderName);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return binder;
    }

    public static class BinderPoolImpl extends IBinderPool.Stub{

        @Override
        public IBinder queryBinder(String binderName) throws RemoteException {
            if(BOOK_BINDER_NAME.equals(binderName)){
                return new BookManageImpl();
            }else if(PERSON_BINDER_NAME.equals(binderName)){
                return new PersonManagerImpl();
            }
            return null;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }
    }
}
