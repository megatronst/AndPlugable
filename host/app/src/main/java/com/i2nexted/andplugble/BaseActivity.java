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
        setContentView();
        preProcess();
        initParam();
        initView();
        initData();
        setListener();
    }
    protected void preProcess(){}
    protected abstract void setContentView();
    protected abstract void initView();
    protected  void setListener(){}
    protected  void initParam(){}
    protected  void initData(){}
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
