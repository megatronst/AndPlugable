package com.i2nexted.andplugble.proxys;

import android.os.Handler;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/12/12.
 */

public class ActivityThreadCallBackHookHelper {

    public static void hookHandlerCallbackd(){
        try {
            // 获取ActivityThread
            Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
            Method currentThread = activityThreadClazz.getDeclaredMethod("currentActivityThread");
            currentThread.setAccessible(true);
            Object  activityThread = currentThread.invoke(null);
            // 获取当前程序activitythread的Handler
            Field mHField = activityThreadClazz.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler realHandler = (Handler) mHField.get(activityThread);
            // 获取handler的mCallback并修改其值
            Field callbackField = Handler.class.getDeclaredField("mCallback");
            callbackField.setAccessible(true);
            callbackField.set(realHandler, new ActivityThreadHandlerCallback(realHandler));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
