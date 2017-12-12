package com.i2nexted.andplugble;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.i2nexted.andplugble.proxys.AMSHookHelper;
import com.i2nexted.andplugble.proxys.ActivityThreadCallBackHookHelper;

public class NotRegistedActivity extends BaseActivity {
    private Button openUnregistedAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void preProcess() {

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
                // 由于AMSHookHelper.hookAms(this);的执行现在打开这种没有注册的activity不会再报activity not found的错误了
                // TODO: 2017/12/12  此处的TargetActivity必须直接继承自Activity如果继承自AppCompatActivity则会出现NoNameFoundException,具体原因还没有研究
                startActivity(new Intent(NotRegistedActivity.this, TargetActivity.class));
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        AMSHookHelper.hookAms();
        ActivityThreadCallBackHookHelper.hookHandlerCallbackd();

    }
}
