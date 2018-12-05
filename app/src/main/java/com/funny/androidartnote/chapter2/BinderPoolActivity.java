package com.funny.androidartnote.chapter2;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.funny.androidartnote.R;
import com.funny.androidartnote.chapter2.model.Person;

import java.util.List;

public class BinderPoolActivity extends AppCompatActivity {
    private static final String TAG = "BinderPoolActivity";

    BinderPool binderPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);

        binderPool = BinderPool.getInstance(this);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IBinder bookBinder = binderPool.queryBinder(BinderPool.BOOK_BINDER_NAME);
                IBinder personBinder = binderPool.queryBinder(BinderPool.PERSON_BINDER_NAME);
                IBookManager bookManager = IBookManager.Stub.asInterface(bookBinder);
                IPersonManager personManager = PersonManagerImpl.asInterface(personBinder);
                try {
                    bookManager.addBook(new Book(1,"Java"));
                    bookManager.addBook(new Book(2,"Ios"));

                    personManager.addPerson(new Person(1,"Jake"));
                    personManager.addPerson(new Person(2,"Rose"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IBinder bookBinder = binderPool.queryBinder(BinderPool.BOOK_BINDER_NAME);
                IBinder personBinder = binderPool.queryBinder(BinderPool.PERSON_BINDER_NAME);
                IBookManager bookManager = IBookManager.Stub.asInterface(bookBinder);
                IPersonManager personManager = PersonManagerImpl.asInterface(personBinder);
                try {
                    List<Book> list = bookManager.getBookList();
                    List<Person> personList = personManager.getPersonList();
                    Log.i(TAG,"list:"+list.size());
                    Log.i(TAG,"personList:"+personList.size());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
