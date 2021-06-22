package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final String TAG = "MainActivity";
    private ArrayList<HashMap<String,String>> listItems;
    Handler handler = null;
    MyAdapter adapter;
    ArrayList<HashMap<String,String>> res;
    TextView balance;
    TextView income;
    TextView outgo;
    ListView listView;
    int idValue;
    double bValue = 10000, inValue = 0, outValue = 0;
    DBManager dbManager = new DBManager(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.mylist);

        Thread t = new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 8) {
                    res = (ArrayList<HashMap<String, String>>) msg.obj;
                    adapter = new MyAdapter(MainActivity.this,
                            android.R.layout.simple_list_item_1, res);
                    listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        balance = findViewById(R.id.balanceValue);
        income = findViewById(R.id.incomeValue);
        outgo = findViewById(R.id.outgoValue);

        for (AccountItem item : dbManager.findAllByType("income")) {
            inValue = inValue + item.getValue();
        }
        for (AccountItem item : dbManager.findAllByType("outgo")) {
            outValue = outValue + item.getValue();
        }
        income.setText(String.valueOf(inValue));
        outgo.setText(String.valueOf(outValue));
        bValue = bValue + inValue - outValue;
        balance.setText(String.valueOf(bValue));

    }

    public void add(View btn){
        Log.i("open", "add: ");
        Intent hello = new Intent(this,AddActivity.class);
        startActivity(hello);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId()==R.id.refresh){
            finish();
            Intent hello = new Intent(this,MainActivity.class);
            startActivity(hello);
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void run() {
        Log.i(TAG, "run: ");
        final ArrayList<HashMap<String,String>> ret = new ArrayList<>();
        for(AccountItem accountItem : dbManager.listAll()){
            Log.i(TAG, "onCreate: "+accountItem.getType());
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("id", String.valueOf(accountItem.getId()));
            map.put("ItemTitle", accountItem.getType());//标题文字
            map.put("ItemDetail", "¥"+accountItem.getValue());//详情描述
            ret.add(map);
        }
        Message msg = handler.obtainMessage(8);
        msg.obj = ret;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //从ListView中获取选中数据
        HashMap<String,String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
        idValue = Integer.parseInt(map.get("id"));
        Log.i(TAG, "onItemClick: " + idValue);

        //打开DetailActivity
        Log.i("open", "openRateActivity2: ");
        Intent hello = new Intent(this, DetailActivity.class);
        //加入传递的参数
        hello.putExtra("idValue",idValue);


        startActivity(hello);



    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemLongClick: 长按事件处理");
        //对话框提示
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("请确认是否删除当前数据")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "onClick: 对话框事件处理");
                        //删除操作
                        dbManager.delete(idValue);
                        Log.i(TAG, "onClick: "+idValue);
                    }
                }).setNegativeButton("否",null);
        builder.create().show();
        return true;
    }

}