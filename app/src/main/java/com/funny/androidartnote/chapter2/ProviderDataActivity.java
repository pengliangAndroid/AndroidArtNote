package com.funny.androidartnote.chapter2;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.funny.androidartnote.R;

public class ProviderDataActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ProviderDataActivity";

    private  Uri uri = Uri.parse("content://com.funny.ex.provider.BookProvider/user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_data);

        Button btnQuery = findViewById(R.id.btn_query);
        Button btnInsert = findViewById(R.id.btn_insert);
        Button btnUpdate = findViewById(R.id.btn_update);
        Button btnDelete = findViewById(R.id.btn_delete);

        btnQuery.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        getContentResolver().registerContentObserver(uri, false, new ContentObserver(null) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Log.i(TAG,"onChange:" + uri);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_query:
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if(cursor == null)
                    return;

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int sex = cursor.getInt(cursor.getColumnIndex("sex"));

                    Log.i(TAG, "id:" + id + "," + name + "," + sex);
                }
                cursor.close();
                break;
            case R.id.btn_insert:
                ContentValues contentValues = new ContentValues();
                contentValues.put("_id",100);
                contentValues.put("name","test");
                contentValues.put("sex",1);
                getContentResolver().insert(uri,contentValues);
                break;
            case R.id.btn_update:
                contentValues = new ContentValues();
                contentValues.put("name","testUpdate");
                contentValues.put("sex",0);
                getContentResolver().update(uri,contentValues,"_id=?",new String[]{"100"});
                break;
            case R.id.btn_delete:
                getContentResolver().delete(uri,"_id=?",new String[]{"100"});
                break;
        }
    }
}
