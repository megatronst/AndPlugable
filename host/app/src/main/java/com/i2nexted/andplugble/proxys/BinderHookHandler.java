package com.i2nexted.andplugble.proxys;

import android.content.ClipData;
import android.os.IBinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/12/7.
 * 用户hook系统的服务binder
 */

public class BinderHookHandler implements InvocationHandler{
    // 原始的binder对象
    private Object mBase;

    public BinderHookHandler(Object mBase,Class<?> stubClass) {
        try {
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
            this.mBase = asInterfaceMethod.invoke(null, mBase);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getPrimaryClip".equals(method.getName())){
            return ClipData.newPlainText(null,"you've been hacked");
        }

        if ("hasPrimaryClip".equals(method.getName())){
            return true;
        }

        return method.invoke(mBase, args);
    }
}
