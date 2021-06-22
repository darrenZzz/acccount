package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class AddActivity extends AppCompatActivity{

    private static final String TAG = "AddActivity";
    EditText inp;
    RadioButton inRB;
    RadioButton outRB;
    EditText remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        inp = findViewById(R.id.inp);
        inRB = findViewById(R.id.inButton);
        outRB = findViewById(R.id.outButton);
        remark = findViewById(R.id.remark);

    }

    public void submit(View btn){
        //获得用户输入金额数值
        String str = inp.getText().toString();
        String type = null;
        double value = 0.0;
        Log.i(TAG,"click: str=" + str);
        if(str==null || str.length()==0){
            //提示
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }else{
            value = Double.valueOf(str).doubleValue();
            //获取单选按钮中的值
            //参考自csdn文章"android studio 如何实现RadioBotton和RadioGroup来实现单选按钮的选择"
            //链接：https://blog.csdn.net/qq_34110501/article/details/70476830
            if (inRB.isChecked()){
                type = "income";
            }
            if (outRB.isChecked()){
                type = "outgo";
            }
            String remarks = remark.getText().toString();
            AccountItem item = new AccountItem();
            item.setValue(value);
            item.setType(type);
            item.setRemarks(remarks);
            //向数据库中添加数据
            DBManager dbManager = new DBManager(AddActivity.this);
            dbManager.add(item);
        }

        //重新跳转到主页面
        Intent hello = new Intent(this,MainActivity.class);
        startActivity(hello);

    }

    public void returnClick(View btn){
        //关闭当前界面
        finish();
        //重新跳转到主页面
        Intent hello = new Intent(this,MainActivity.class);
        startActivity(hello);

    }
}