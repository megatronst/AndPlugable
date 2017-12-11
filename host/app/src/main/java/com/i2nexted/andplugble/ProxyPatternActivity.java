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

public class ProxyPatternActivity extends BaseActivity{
    private Button btnStaticProxy;
    private Button btnDynamicProxy;
    private Button btnUseModifiedInsrumentation;
    private Button btnUseModifiedActivityInstrumentation;
    private Button btnAidlEg;
    private Button btnHookSysService;
    private Button btnHookAms;
    private Button btnHookPmg;
    private Button btnHookActivityLifeCercle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_proxy_pattern);
    }

    @Override
    protected void preProcess() {
        HookHelper.attachActivityContext(this);
    }

    @Override
    protected void initView() {
        btnStaticProxy = (Button) findViewById(R.id.btn_static_proxy);
        btnDynamicProxy = (Button) findViewById(R.id.btn_dynamic_proxy);
        btnUseModifiedInsrumentation = (Button) findViewById(R.id.btn_use_modified_insrumentation);
        btnUseModifiedActivityInstrumentation = (Button) findViewById(R.id.btn_use_modified_activity_instrumentation);
        btnAidlEg = (Button) findViewById(R.id.btn_aidl_eg);
        btnHookSysService = (Button) findViewById(R.id.btn_hook_sys_service);
        btnHookAms = (Button) findViewById(R.id.btn_hook_ams);
        btnHookPmg = (Button) findViewById(R.id.btn_hook_pmg);
        btnHookActivityLifeCercle = (Button) findViewById(R.id.btn_hook_activity_life_cercle);
    }

    @Override
    protected void setListener() {
        btnStaticProxy.setOnClickListener(this);
        btnDynamicProxy.setOnClickListener(this);
        btnUseModifiedInsrumentation.setOnClickListener(this);
        btnUseModifiedActivityInstrumentation.setOnClickListener(this);
        btnAidlEg.setOnClickListener(this);
        btnHookSysService.setOnClickListener(this);
        btnHookAms.setOnClickListener(this);
        btnHookPmg.setOnClickListener(this);
        btnHookActivityLifeCercle.setOnClickListener(this);
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
                break;
            case R.id.btn_hook_activity_life_cercle:
                testLifeCercle();
                break;
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
        startActivity(InstalledPluginsActivity.class);
    }

    private void ipcEgxample(){
        startActivity(IPCActivity.class);
    }

    private void hookSysService(){
        startActivity(HookSysServiceActivity.class);
    }

    private void hookAMS(){
        startActivity(HookAmsActivity.class);
    }

    private void hookPMS(){
        startActivity(HookPMSActivity.class);
    }

    private void testLifeCercle(){
        startActivity(NotRegistedActivity.class);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
//        HookHelper.attachApplicationContext();
    }
}
