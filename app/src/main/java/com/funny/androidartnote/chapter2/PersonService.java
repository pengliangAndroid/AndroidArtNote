package com.funny.androidartnote.chapter2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.funny.androidartnote.chapter2.model.Person;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PersonService extends Service {
    private static final String TAG = "PersonService";

    public CopyOnWriteArrayList<Person> arrayList = new CopyOnWriteArrayList<>();

    private IPersonManager personManager = new PersonManagerImpl(){
        @Override
        public void addPerson(Person person) {
            Log.i(TAG,"threadName:" + Thread.currentThread().getName());
            Log.i(TAG,"addPerson:" + person.toString());

            arrayList.add(person);
        }

        @Override
        public List<Person> getPersonList() {
            Log.i(TAG,"threadName:" + Thread.currentThread().getName());
            Log.i(TAG,"getPersonList:" + arrayList.size());

            return arrayList;
        }
    };

    public PersonService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return personManager.asBinder();
    }
}
