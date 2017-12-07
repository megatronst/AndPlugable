package com.i2nexted.andplugble.proxys;

import android.content.Context;
import android.os.IBinder;

import com.i2nexted.andplugble.proxys.interfaces.BinderProxyHookHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/7.
 */

public class BinderHelper {

    public static void hookClipboardService(){
        try {
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            Method getServiceMethod = serviceManager.getDeclaredMethod("getService", String.class);
            IBinder rawBinder = (IBinder) getServiceMethod.invoke(null, Context.CLIPBOARD_SERVICE);
            IBinder hookedBinder = (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(), new Class[]{IBinder.class}, new BinderProxyHookHandler(rawBinder));
            Field field = serviceManager.getDeclaredField("sCache");
            field.setAccessible(true);
            Map<String, IBinder> binderMap = (Map<String, IBinder>) field.get(null);
            binderMap.put(Context.CLIPBOARD_SERVICE, hookedBinder);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
