package com.i2nexted.andplugble;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.i2nexted.andplugble.proxys.BinderHelper;
import com.i2nexted.andplugble.proxys.BinderHookHandler;

public class HookSysServiceActivity extends BaseActivity {
    private EditText etTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_hook_sys_service);
    }

    @Override
    protected void preProcess() {
        BinderHelper.hookClipboardService();
    }

    @Override
    protected void initView() {
        etTest = (EditText) findViewById(R.id.et_test);
    }

}
