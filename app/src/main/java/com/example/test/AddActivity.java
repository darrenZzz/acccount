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

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参值
        p.height = (int) (d.getHeight() * 0.45); // 高度设置为屏幕的0.45
        p.width = (int) (d.getWidth() * 0.5); // 宽度设置为屏幕的0.5
        p.alpha = 1.0f; // 设置本身透明度
        p.dimAmount = 0.0f; // 设置黑暗度
        getWindow().setAttributes(p); // 设置生效
        getWindow().setGravity(Gravity.CENTER); // 设置居中对齐

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
}