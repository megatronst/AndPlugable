package com.i2nexted.andplugble;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.i2nexted.andplugble.proxys.AMSHookHelper;
import com.i2nexted.andplugble.proxys.HookHelper;
import com.i2nexted.andplugble.proxys.impl.MyInvocationHandler;
import com.i2nexted.andplugble.proxys.impl.PersonImpl;
import com.i2nexted.andplugble.proxys.impl.PersonProxy;
import com.i2nexted.andplugble.proxys.interfaces.IDog;
import com.i2nexted.andplugble.proxys.interfaces.IPerson;

import java.lang.reflect.Proxy;

public class ProxyPatternActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnStaticProxy;
    private Button btnDynamicProxy;
    private Button btnUseModifiedInsrumentation;
    private Button btnUseModifiedActivityInstrumentation;
    private Button btnAidlEg;
    private Button btnHookSysService;
    private Button btnHookAms;
    private Button btnHookPmg;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_pattern);
        HookHelper.attachActivityContext(this);

        initView();
    }

    private void initView() {
        btnStaticProxy = (Button) findViewById(R.id.btn_static_proxy);
        btnStaticProxy.setOnClickListener(this);
        btnDynamicProxy = (Button) findViewById(R.id.btn_dynamic_proxy);
        btnDynamicProxy.setOnClickListener(this);
        btnUseModifiedInsrumentation = (Button) findViewById(R.id.btn_use_modified_insrumentation);
        btnUseModifiedInsrumentation.setOnClickListener(this);
        btnUseModifiedActivityInstrumentation = (Button) findViewById(R.id.btn_use_modified_activity_instrumentation);
        btnUseModifiedActivityInstrumentation.setOnClickListener(this);
        btnAidlEg = (Button) findViewById(R.id.btn_aidl_eg);
        btnAidlEg.setOnClickListener(this);
        btnHookSysService = (Button) findViewById(R.id.btn_hook_sys_service);
        btnHookSysService.setOnClickListener(this);
        btnHookAms = (Button) findViewById(R.id.btn_hook_ams);
        btnHookAms.setOnClickListener(this);
        btnHookPmg = (Button) findViewById(R.id.btn_hook_pmg);
        btnHookPmg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_static_proxy:
                testStaticProxy("static proxy");
                break;
            case R.id.btn_dynamic_proxy:
                testDynamicProxy("haha");
                break;
            case R.id.btn_use_modified_insrumentation:
                openActivityByApplication();
                break;
            case R.id.btn_use_modified_activity_instrumentation:
                openActivity();
                break;
            case R.id.btn_aidl_eg:
                ipcEgxample();
                break;
            case R.id.btn_hook_sys_service:
                hookSysService();
                break;
            case R.id.btn_hook_ams:
                hookAMS();
                break;
            case R.id.btn_hook_pmg:
                hookPMS();
        }
    }

    private void testStaticProxy(String str){
        IPerson person = new PersonImpl();
        PersonProxy personProxy = new PersonProxy(person);
        Toast.makeText(this, personProxy.doThings(str), Toast.LENGTH_SHORT).show();
    }

    boolean isSwitched = false;
    private void testDynamicProxy(String str){
        if (isSwitched){
            IPerson person = (IPerson) Proxy.newProxyInstance(IPerson.class.getClassLoader(), new Class[]{IPerson.class}, new MyInvocationHandler());
            person.doThings("something");
        }else {
            IDog dog = (IDog)Proxy.newProxyInstance(IDog.class.getClassLoader(), new Class[]{IDog.class}, new MyInvocationHandler());
            dog.bark();
        }
        isSwitched = !isSwitched;
    }

    private void openActivityByApplication(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(getApplicationContext(), InstalledPluginsActivity.class);
        getApplicationContext().startActivity(intent);
    }

    private void openActivity(){
        startActivity(new Intent(ProxyPatternActivity.this, InstalledPluginsActivity.class));
    }

    private void ipcEgxample(){
        startActivity(new Intent(ProxyPatternActivity.this, IPCActivity.class));
    }

    private void hookSysService(){
        startActivity(new Intent(ProxyPatternActivity.this, HookSysServiceActivity.class));
    }

    private void hookAMS(){
        startActivity(new Intent(ProxyPatternActivity.this, HookAmsActivity.class));
    }

    private void hookPMS(){
        startActivity(new Intent(ProxyPatternActivity.this, HookPMSActivity.class));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
//        HookHelper.attachApplicationContext();
    }
}
