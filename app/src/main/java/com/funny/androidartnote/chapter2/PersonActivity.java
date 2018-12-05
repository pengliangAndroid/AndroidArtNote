package com.funny.androidartnote.chapter2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.funny.androidartnote.R;
import com.funny.androidartnote.chapter2.model.Person;

import java.util.List;

public class PersonActivity extends AppCompatActivity {
    private static final String TAG = "PersonActivity";

    IPersonManager personManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Intent intent = new Intent(this,PersonService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                personManager = PersonManagerImpl.asInterface(service);
                try {
                    service.linkToDeath(deathRecipient,0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        },BIND_AUTO_CREATE);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean alive = personManager.asBinder().isBinderAlive();
                Log.i(TAG,"alive："+ alive);
                try {
                    personManager.addPerson(new Person(1,"item"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Person> list = personManager.getPersonList();
                    Log.i(TAG,"list.size()="+list.size());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.i(TAG,"deathRecipient");
            personManager.asBinder().unlinkToDeath(deathRecipient,0);
        }
    };
}
