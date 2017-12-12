package com.i2nexted.andplugble.proxys;

import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/12/8.
 */

public class AMSHookHelper {
    public static void hookAms(){
        try {
            // 获取defalt对象
            Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
            Field myDefault = activityManagerNative.getDeclaredField("gDefault");
            myDefault.setAccessible(true);
            Object obj = myDefault.get(null);
            // 获取到activitymanager binder对象
            Class<?> singlton = Class.forName("android.util.Singleton");
            Field instance = singlton.getDeclaredField("mInstance");
            instance.setAccessible(true);
            Object rawActivityManager = instance.get(obj);
            //创建新的activitymanager 代理对象
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManagerInterface},
                    new IActivityManagerHandler(rawActivityManager));
            // 替换instance
            instance.set(obj, proxy);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
