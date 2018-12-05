package com.funny.androidartnote.chapter2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author pengl
 */
public class BookProvider extends ContentProvider {

    private static final String TAG = "BookProvider";

    private static final String AUTHORITY = "com.funny.ex.provider.BookProvider";

    private static final Uri BOOK_URI = Uri.parse("content://"+ AUTHORITY + "/book");

    private static final Uri USER_URI = Uri.parse("content://"+ AUTHORITY + "/user");


    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int URI_CODE_USER = 1;

    private static final int URI_CODE_BOOK = 2;

    private DBOpenHelper dbOpenHelper;

    private SQLiteDatabase database;

    static {
        uriMatcher.addURI(AUTHORITY,"user",URI_CODE_USER);
        uriMatcher.addURI(AUTHORITY,"book",URI_CODE_BOOK);
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG,Thread.currentThread().getName());
        dbOpenHelper = new DBOpenHelper(getContext());
        database = dbOpenHelper.getWritableDatabase();

        database.execSQL("DELETE FROM " + DBOpenHelper.USER_TABLE_NAME);
        database.execSQL("DELETE FROM " + DBOpenHelper.BOOK_TABLE_NAME);

        database.execSQL("insert into book values(1,'android')");
        database.execSQL("insert into book values(2,'ios')");
        database.execSQL("insert into book values(3,'js')");
        database.execSQL("insert into user values(1,'jake',1)");
        database.execSQL("insert into user values(2,'july',2)");

        return true;
    }

    private String getTabName(Uri uri){
        if(uri.equals(BOOK_URI)){
            return DBOpenHelper.BOOK_TABLE_NAME;
        }else if(uri.equals(USER_URI)){
            return DBOpenHelper.USER_TABLE_NAME;
        }

        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG,"query:" + Thread.currentThread().getName());

        String tableName = getTabName(uri);
        if(tableName == null){
            throw new IllegalArgumentException("Unsupported Uri :" + uri);
        }
        return database.query(tableName,projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG,"getType:" + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG,"insert:" + Thread.currentThread().getName());
        String tableName = getTabName(uri);
        if(tableName == null){
            throw new IllegalArgumentException("Unsupported Uri :" + uri);
        }
        database.insert(tableName,null,values);

        getContext().getContentResolver().notifyChange(uri,null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG,"delete:" + Thread.currentThread().getName());
        String tableName = getTabName(uri);
        if(tableName == null){
            throw new IllegalArgumentException("Unsupported Uri :" + uri);
        }
        int count = database.delete(tableName, selection, selectionArgs);
        if(count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG,"update:" + Thread.currentThread().getName());
        String tableName = getTabName(uri);
        if(tableName == null){
            throw new IllegalArgumentException("Unsupported Uri :" + uri);
        }
        int row = database.update(tableName, values,selection, selectionArgs);
        if(row > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }
}
