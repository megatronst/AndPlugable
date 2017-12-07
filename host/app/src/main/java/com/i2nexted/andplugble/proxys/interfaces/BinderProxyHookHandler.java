package com.i2nexted.andplugble.proxys.interfaces;

import android.os.IBinder;
import android.os.IInterface;

import com.i2nexted.andplugble.proxys.BinderHookHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/12/7.
 */

public class BinderProxyHookHandler implements InvocationHandler {
    IBinder base;
    Class<?>  stub;
    Class<?> iinterface;

     public BinderProxyHookHandler(IBinder base) {
        this.base = base;
        try {
            this.stub = Class.forName("android.content.IClipboard$Stub");
            this.iinterface = Class.forName("android.content.IClipboard");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
     }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())){
            Class[] classes = new Class[]{IBinder.class, IInterface.class, iinterface};
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), classes, new BinderHookHandler(base, stub));
        }
        return method.invoke(base, args);
    }
}
