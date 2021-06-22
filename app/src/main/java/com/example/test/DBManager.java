package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void add(AccountItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", item.getType());
        values.put("value", item.getValue());
        values.put("remarks", item.getRemarks());
        db.insert(TBNAME, null, values);
        db.close();
    }

    public void addAll(List<AccountItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (AccountItem item : list) {
            ContentValues values = new ContentValues();
            values.put("type", item.getType());
            values.put("value", item.getValue());
            values.put("remarks", item.getRemarks());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(AccountItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", item.getType());
        values.put("value", item.getValue());
        values.put("remarks", item.getRemarks());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public List<AccountItem> listAll(){
        List<AccountItem> accountList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            accountList = new ArrayList<AccountItem>();
            while(cursor.moveToNext()){
                AccountItem item = new AccountItem();
                int index = cursor.getColumnIndex("id");
                Log.i("db", "listAll: index=" + index);
                int id = cursor.getInt(index);
                Log.i("db", "listAll: id=" + id);
                item.setId(id);
                item.setType(cursor.getString(cursor.getColumnIndex("type")));
                item.setValue(cursor.getDouble(cursor.getColumnIndex("value")));
                item.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));

                accountList.add(item);
            }
            cursor.close();
        }
        db.close();
        return accountList;

    }

    public AccountItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        AccountItem item = null;
        if(cursor!=null && cursor.moveToFirst()){
            item = new AccountItem();
            item.setId(cursor.getInt(cursor.getColumnIndex("id")));
            item.setType(cursor.getString(cursor.getColumnIndex("type")));
            item.setValue(cursor.getDouble(cursor.getColumnIndex("value")));
            item.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
            cursor.close();
        }
        db.close();
        return item;
    }

    public List<AccountItem> findAllByType(String type){
        List<AccountItem> accountList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "type=?", new String[]{type}, null, null, null);
        AccountItem rateItem = null;
        if(cursor!=null){
            accountList = new ArrayList<AccountItem>();
            while(cursor.moveToNext()) {
                AccountItem item = new AccountItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("id")));
                item.setType(cursor.getString(cursor.getColumnIndex("type")));
                item.setValue(cursor.getDouble(cursor.getColumnIndex("value")));
                item.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));

                accountList.add(item);
            }
            cursor.close();
        }
        db.close();
        return accountList;
    }
}
