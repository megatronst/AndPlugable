package com.i2nexted.andplugble;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.i2nexted.andplugble.proxys.PMSHookHelper;

public class HookPMSActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnHookPms;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_pms);

        btnHookPms = (Button) findViewById(R.id.btn_hook_pms);
        btnHookPms.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_hook_pms:
                getPackageManager().getInstalledPackages(0);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        PMSHookHelper.hookPms(newBase);
        super.attachBaseContext(newBase);
    }
}
