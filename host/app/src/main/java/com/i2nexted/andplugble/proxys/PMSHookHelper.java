package com.i2nexted.andplugble.proxys;

import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/12/8.
 */

public class PMSHookHelper {
    public static void hookPms(Context context){

        try {

            // 获取到原始的packagemanager
            Class<?> atClass = Class.forName("android.app.ActivityThread");
            Method currentThread = atClass.getDeclaredMethod("currentActivityThread");
            currentThread.setAccessible(true);
            Object thread = currentThread.invoke(null);
            Field threadPms = atClass.getDeclaredField("sPackageManager");
            threadPms.setAccessible(true);
            Object oriPMS = threadPms.get(thread);
            // 创建新的pms
            Class<?> ipackageManager = Class.forName("android.content.pm.IPackageManager");
            Object newPms = Proxy.newProxyInstance(ipackageManager.getClassLoader(), new Class[]{ipackageManager}, new PMSHandler(oriPMS, context));
            // 替换ContexImpl中的PackageManager
//            Class<?> contextImpl = Class.forName("android.app.ApplicationPackageManager");
            Object appPms = context.getPackageManager();
            Field pmsFiled = appPms.getClass().getDeclaredField("mPM");
            pmsFiled.setAccessible(true);
            pmsFiled.set(appPms, newPms);
            // 替换ActivityThread中的PackageManager
            threadPms.set(thread, newPms);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
