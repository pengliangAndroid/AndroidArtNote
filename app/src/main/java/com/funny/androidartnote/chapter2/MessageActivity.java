package com.funny.androidartnote.chapter2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.funny.androidartnote.R;

public class MessageActivity extends AppCompatActivity {
    private static final String TAG = "MessageActivity";

    Messenger replyMessenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            Log.i(TAG,msg.what+"");
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = new Intent(this,MessageService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Messenger messenger = new Messenger(service);
            Message message = Message.obtain();
            message.what = 0;
            Bundle bundle = new Bundle();
            bundle.putString("msg","hello message");

            message.replyTo = replyMessenger;
            message.setData(bundle);
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");

        unbindService(serviceConnection);
    }
}
