package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    TextView value;
    TextView type;
    TextView remarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参值
        p.height = (int) (d.getHeight() * 0.45); // 高度设置为屏幕的0.45
        p.width = (int) (d.getWidth() * 0.5); // 宽度设置为屏幕的0.5
        p.alpha = 1.0f; // 设置本身透明度
        p.dimAmount = 0.0f; // 设置黑暗度
        getWindow().setAttributes(p); // 设置生效
        getWindow().setGravity(Gravity.CENTER); // 设置居中对齐

        //接收数据
        Intent intent = getIntent();
        int id = intent.getIntExtra("idValue",0);
        Log.i(TAG, "onCreate: " + id);

        //从数据库中获取数据
        DBManager dbManager = new DBManager(DetailActivity.this);
        AccountItem item = dbManager.findById(id);

        //获取控件
        value = findViewById(R.id.value);
        type = findViewById(R.id.type);
        remarks = findViewById(R.id.remarks);


        //将数据放入控件
        value.setText("金额: "+item.getValue());
        type.setText("类型: "+item.getType());
        remarks.setText("备注: "+item.getRemarks());


    }

}