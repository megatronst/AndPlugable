package com.i2nexted.andplugble.proxys.impl;

import android.widget.Toast;

import com.i2nexted.andplugble.common.MyApplication;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
        private Object baseObj;

//        public MyInvocationHandler(Object baseObj) {
//            this.baseObj = baseObj;
//        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("doThings".equals(method.getName())){
                Toast.makeText(MyApplication.getInstance(), "person dynamic proxy..", Toast.LENGTH_SHORT).show();
            }else if ("bark".equals(method.getName())){
                Toast.makeText(MyApplication.getInstance(), "dog dynamic proxy...", Toast.LENGTH_SHORT).show();
            }
//            if (proxy instanceof IPerson){
//                Toast.makeText(ProxyPatternActivity.this, "person dynamic proxy..", Toast.LENGTH_SHORT).show();
//            }
//
//            if (proxy instanceof IDog){
//                Toast.makeText(ProxyPatternActivity.this, "dog dynamic proxy...", Toast.LENGTH_SHORT).show();
//            }

            return null;
        }
    }