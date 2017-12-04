package com.i2nexted.andplugble;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.i2nexted.andplugble.classloader.FileSystemClassLoader;
import com.i2nexted.andplugble.utils.FileUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnLoadClassFile;
    private Button btnInvokeNormalFunc;
    private FileSystemClassLoader classLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileUtil.getDir(FileUtil.CLASS_DIR);
        classLoader = new FileSystemClassLoader("com.i2nexted.andplugble.TestClass");
        btnLoadClassFile = (Button) findViewById(R.id.btn_invoke_static_func);
        btnLoadClassFile.setOnClickListener(this);
        btnInvokeNormalFunc = (Button) findViewById(R.id.btn_invoke_normal_func);
        btnInvokeNormalFunc.setOnClickListener(this);
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
        }
    }

    private void invokeNormalMethod(){
        try {
            Class<?> clazz = classLoader.loadClass("com.i2nexted.andplugble.TestClass");
            Object obj = clazz.newInstance();
            Method normalMethod = clazz.getMethod("normalMethod", android.content.Context.class);
            normalMethod.invoke(obj, this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        try {
            Class<?> clazz = classLoader.loadClass("com.i2nexted.andplugble.TestClass");
            Object obj = clazz.newInstance();
            Method normalMethod = clazz.getMethod("staticMethod", android.content.Context.class);
            normalMethod.invoke(obj, this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
}
