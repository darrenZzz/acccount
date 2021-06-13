package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;



public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, "myAccountBook", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists user_tb(id integer primary key autoincrement," +
                "userName text not null," +
                "pwd text not null)");
        db.execSQL("create table if not exists account_tb(id integer primary key autoincrement," +
                "userName text not null," +
                "pwd text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
