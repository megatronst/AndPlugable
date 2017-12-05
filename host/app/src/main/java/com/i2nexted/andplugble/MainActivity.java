package com.i2nexted.andplugble;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.i2nexted.andplugble.utils.FileUtil;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnLoadClassFile;
    private Button btnInvokeNormalFunc;
    private Button btnOpenActivity;
    private Button btnOpenInstalledActivity;
    private Button btnUseUninstalledApkResource;




    private boolean uninstalledApkOpend = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_invoke_static_func:
                invokeStaticMethod();
                break;
            case R.id.btn_invoke_normal_func:
                invokeNormalMethod();
                break;
            case R.id.btn_open_uninstalled_activity:
                opentUninstalledApkActivity();
                break;
            case R.id.btn_use_installed_apk_recource:
                useInstalledApkResource();
                break;
            case R.id.btn_use_uninstalled_apk_resource:
                useUninstalledApkResource();
                break;
        }
    }

    private void initView(){
        FileUtil.getDir(FileUtil.CLASS_DIR);
        btnLoadClassFile = (Button) findViewById(R.id.btn_invoke_static_func);
        btnLoadClassFile.setOnClickListener(this);
        btnInvokeNormalFunc = (Button) findViewById(R.id.btn_invoke_normal_func);
        btnInvokeNormalFunc.setOnClickListener(this);
        btnOpenActivity = (Button) findViewById(R.id.btn_use_installed_apk_recource);
        btnOpenActivity.setOnClickListener(this);
        btnOpenInstalledActivity = (Button) findViewById(R.id.btn_use_installed_apk_recource);
        btnOpenInstalledActivity.setOnClickListener(this);
        btnUseUninstalledApkResource = (Button) findViewById(R.id.btn_use_uninstalled_apk_resource);
        btnUseUninstalledApkResource.setOnClickListener(this);
    }

    private void invokeNormalMethod(){
        File outDexDir = getDir("dex1",0);
        String dexPath  = FileUtil.getDir(FileUtil.CLASS_DIR) + "testLib.jar";
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, outDexDir.getAbsolutePath(), null, getClassLoader());
        try {
            Class clazz = dexClassLoader.loadClass("com.i2nexted.tetslib.TestClass");
            Object object = clazz.newInstance();
            Method normalMethod = clazz.getMethod("invokeNormalMethod", android.content.Context.class);
            normalMethod.invoke(object, this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void invokeStaticMethod(){
        String dexOutPath = getDir("dex1",0).getAbsolutePath();
        String dexPath = FileUtil.getDir(FileUtil.CLASS_DIR) + "testLib.jar";
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, dexOutPath, null, getClassLoader());
        try {
            Class clazz = dexClassLoader.loadClass("com.i2nexted.tetslib.TestClass");
            Method method = clazz.getMethod("invokeStaticMethod", android.content.Context.class);
            method.invoke(clazz,this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void opentUninstalledApkActivity(){
        Bundle bundle = new Bundle();
        bundle.putBoolean("KEY_START_FROM_OTHER_ACTIVITY", true);

        String dexPath = FileUtil.getDir(FileUtil.CLASS_DIR) + "app-debug.apk";
        String dexOutPath = getDir("apk1", 0).getAbsolutePath();
        loadApk(bundle, dexPath, dexOutPath);
        uninstalledApkOpend = true;
    }

    // 加载未安装的apk
    private void loadApk(Bundle paramBundle, String dexPath, String dexOutPath){
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, dexOutPath, null, ClassLoader.getSystemClassLoader());
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(dexPath, PackageManager.GET_ACTIVITIES);
        if (packageInfo.activities != null && packageInfo.activities.length >0){
            String activityName = packageInfo.activities[0].name;
            try {
                Class clazz = dexClassLoader.loadClass(activityName);
                Constructor constructor = clazz.getConstructor(new Class[]{});
                Object instance = constructor.newInstance(new Object[]{});
                Method localMethodSetActivity = clazz.getDeclaredMethod("setActivity", android.app.Activity.class);
                localMethodSetActivity.setAccessible(true);
                localMethodSetActivity.invoke(instance, this);
                Method localOnCreate = clazz.getDeclaredMethod("onCreate", new Class[]{android.os.Bundle.class});
                localOnCreate.setAccessible(true);
                localOnCreate.invoke(instance, new Object[]{paramBundle});

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void useInstalledApkResource(){
        startActivity(new Intent(MainActivity.this, InstalledPluginsActivity.class));
    }

    private void useUninstalledApkResource(){
        startActivity(new Intent(MainActivity.this, UninstalledPluginsActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (uninstalledApkOpend){
            setContentView(R.layout.activity_main);
            initView();
        }else {
            finish();
        }
    }
}
