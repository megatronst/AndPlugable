package com.i2nexted.andplugble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preProcess();
        setContentView();
        initParam();
        initView();
        initData();
        setListener();
    }
    // 添加一些预处理
    protected void preProcess(){}
    // 设置activity的contentview
    protected abstract void setContentView();
    // 初始化控件
    protected abstract void initView();
    // 为对应控件设置时间监听
    protected  void setListener(){}
    // 初始化所需的变量
    protected  void initParam(){}
    // 初始化所需数据
    protected  void initData(){}
    // 打开activity
    protected void startActivity(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
