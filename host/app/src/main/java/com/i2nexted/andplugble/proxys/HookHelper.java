package com.i2nexted.andplugble.proxys;

import android.app.Activity;
import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/12/6.
 */

public class HookHelper {

    public static void attachApplicationContext(){
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentInstrumentation = clazz.getDeclaredMethod("currentActivityThread");
            Object activityThread = currentInstrumentation.invoke(clazz);
            Field instrumenttation = clazz.getDeclaredField("mInstrumentation");
            instrumenttation.setAccessible(true);
            instrumenttation.set(activityThread, new EvilInstumentation((Instrumentation) instrumenttation.get(activityThread)));
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

    public static void attachActivityContext(Activity activity){
        try {
            Class clazz = Class.forName("android.app.Activity");
            Field instrumentation = clazz.getDeclaredField("mInstrumentation");
            instrumentation.setAccessible(true);
            instrumentation.set(activity, new EvilInstumentation((Instrumentation)instrumentation.get(activity)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
