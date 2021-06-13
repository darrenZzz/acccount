package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.mylist);

        List<String> list1 = new ArrayList<String>();
        for(int i = 1;i<100;i++){
            list1.add("item"+i);
        }
        ListAdapter adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list1);
        listView.setAdapter(adapter);

    }

    public void add(View btn){
        Log.i("open", "add: ");
        Intent hello = new Intent(this,AddActivity.class);
        startActivity(hello);
    }
}