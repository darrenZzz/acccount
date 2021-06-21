package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;



public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "myaccountbook.db";
    public static final String TB_NAME = "account_tb";

    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }
    public DBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //账簿表数据
        db.execSQL("create table "+TB_NAME+"(id integer primary key autoincrement," +
                "type text not null," +
                "value real not null," +
                "remarks text)");
        //为账簿表添加初始数据
        db.execSQL("insert into "+TB_NAME+"(type,value,remarks) values('initial value',10000.0,'初始资金')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}
