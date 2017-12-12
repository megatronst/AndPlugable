package com.i2nexted.andplugble.plug;

import android.content.pm.ApplicationInfo;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/12.
 * 通过hook掉ClassLoader来加载插件中的activity
 */

public class PlugbleActivityUtil {
    /**
     * 获取ActivityThread中的mPackages
     * */
    private static void hookClassLoader() throws IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        // 获取到ActivityThread中存储dex信息的变量mPackages
            // 获取当前app的ActivityThread
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentATMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object currentAT = currentATMethod.invoke(null);
            // 获取当前ActivityThread的mPackages
            Field mPackagesField = activityThreadClass.getDeclaredField("mPackages");
            mPackagesField.setAccessible(true);
            Map rawPackages = (Map)mPackagesField.get(currentAT);

    }

    /**
     *通过反射获取生成ApplicationInfo的方法
     * */
    private static Method getApplicationInfo() throws NoSuchMethodException, ClassNotFoundException {
            Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser.class");
            Class<?> packageParser$PackageClass = Class.forName("android.content.pm.PackageParser$Package");
            Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
            Method getApInfoMethod = packageParserClass.getDeclaredMethod("generateApplicationInfo",
                    new Class[]{packageParser$PackageClass,
                            int.class, packageUserStateClass, int.class});
            return getApInfoMethod;
    }

    /**
     * 构建生成ApplicationInfo方法所需的参数Package参数
     * */
    private static Object getPackage(String apkFile) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> packegeParserClass = Class.forName("android.content.pm.PackageParser.class");
        Object packageParser = packegeParserClass.newInstance();
        Method parseMethod  = packegeParserClass.getDeclaredMethod("parsePackage", new Class[]{File.class, int.class});
        return parseMethod.invoke(packageParser, new File(apkFile), 0);
    }

    /**
     * 构建生成packageUserState
     * */
//    private static Object getPackageUserState(){
//
//    }
}
