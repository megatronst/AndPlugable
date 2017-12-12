package com.i2nexted.andplugble;

import android.os.Bundle;
import android.widget.Button;

public class TestPluginActActivity extends BaseActivity {
    private Button openPlugAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_test_plugin_act);
    }

    @Override
    protected void initView() {
        openPlugAct = (Button)findViewById(R.id.btn_open_plug_act);
    }

    @Override
    protected void setListener() {
        openPlugAct.setOnClickListener(this);
    }
}
