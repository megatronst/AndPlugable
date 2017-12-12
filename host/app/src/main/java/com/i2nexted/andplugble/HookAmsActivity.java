package com.i2nexted.andplugble;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.i2nexted.andplugble.proxys.AMSHookHelper;

public class HookAmsActivity extends BaseActivity{
    private Button btnOpenAnyActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_hook_ams);
    }

    @Override
    protected void initView() {
        btnOpenAnyActivity = (Button) findViewById(R.id.btn_open_any_activity);
    }

    @Override
    protected void setListener() {
        btnOpenAnyActivity.setOnClickListener(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AMSHookHelper.hookAms();
        super.attachBaseContext(newBase);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_open_any_activity:
                // 测试AMS HOOK (调用其相关方法)
                Uri uri = Uri.parse("http://wwww.baidu.com");
                Intent t = new Intent(Intent.ACTION_VIEW);
                t.setData(uri);
                startActivity(t);
                break;
        }
    }
}
