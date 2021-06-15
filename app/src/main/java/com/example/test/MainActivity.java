package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Runnable{

    private static final String TAG = "MainActivity";
    private ArrayList<HashMap<String,String>> listItems;
    Handler handler = null;
    MyAdapter adapter;
    ArrayList<HashMap<String,String>> res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.mylist);

        Thread t = new Thread(this);t.start();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                if(msg.what == 8){
                    res = (ArrayList<HashMap<String,String>>)msg.obj;
                    adapter = new MyAdapter(MainActivity.this,
                            android.R.layout.simple_list_item_1,res);
                    listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };



    }

    public void add(View btn){
        Log.i("open", "add: ");
        Intent hello = new Intent(this,AddActivity.class);
        startActivity(hello);
    }

    @Override
    public void run() {
        Log.i(TAG, "run: ");
        final ArrayList<HashMap<String,String>> ret = new ArrayList<>();
        DBManager dbManager = new DBManager(MainActivity.this);
        for(AccountItem accountItem : dbManager.listAll()){
            Log.i(TAG, "onCreate: "+accountItem.getType());
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("ItemTitle", accountItem.getType());//标题文字
            map.put("ItemDetail", "¥"+accountItem.getValue());//详情描述
            ret.add(map);
        }
        Message msg = handler.obtainMessage(8);
        msg.obj = ret;
        handler.sendMessage(msg);
    }
}