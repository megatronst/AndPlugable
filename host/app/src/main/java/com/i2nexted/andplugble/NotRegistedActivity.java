package com.i2nexted.andplugble;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.i2nexted.andplugble.proxys.AMSHookHelper;

public class NotRegistedActivity extends BaseActivity {
    private Button openUnregistedAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void preProcess() {
        AMSHookHelper.hookAms(this);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_not_registed);
    }

    @Override
    protected void initView() {
        openUnregistedAct = (Button) findViewById(R.id.open_unregisted_act);
    }

    @Override
    protected void setListener() {
        openUnregistedAct.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.open_unregisted_act:
                // 此处的TargetActivity.class是没有在AndroidManifest.xml文件中注册过的
                // 由于AMSHookHelper.hookAms(this);的执行现在打开这种没有注册的activity不会再包activity not found的错误了
                startActivity(new Intent(NotRegistedActivity.this, TargetActivity.class));
                break;
        }
    }
}
