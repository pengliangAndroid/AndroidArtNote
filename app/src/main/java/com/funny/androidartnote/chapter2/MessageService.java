package com.funny.androidartnote.chapter2;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessageService extends Service {
    private static final String TAG = "MessageService";

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                Log.i(TAG,msg.getData().getString("msg"));
                Messenger messenger = msg.replyTo;

                Message message = Message.obtain();
                message.what = 1;
                try {
                    messenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            super.handleMessage(msg);
        }
    };

    private Messenger messenger = new Messenger(handler);

    public MessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.funny.ex.permission.ACCESS_MESSAGE_SERVICE");
        if(check == PackageManager.PERMISSION_DENIED)
            return null;
        return messenger.getBinder();
    }
}
