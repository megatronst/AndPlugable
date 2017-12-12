package com.i2nexted.andplugble.proxys;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.i2nexted.andplugble.R;
import com.i2nexted.andplugble.StubActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/12/8.
 */

public class IActivityManagerHandler implements InvocationHandler {
    public static final String EXTRA_TARGET_INTENT = "extra_target";
    private Object base;
    public IActivityManagerHandler(Object base) {
        this.base = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("startActivity".equals(method.getName())){
            Intent raw;
            int index = 0;
            for (int i=0;i<args.length -1;i++){
                if (args[i] instanceof Intent){
                    index = i;
                    break;
                }
            }
            raw = (Intent)args[index];
            Intent newIntent = new Intent();

            String targetPackage = "com.i2nexted.andplugble";
            ComponentName componentName = new ComponentName(targetPackage, StubActivity.class.getName());
            newIntent.setComponent(componentName);
            newIntent.putExtra(EXTRA_TARGET_INTENT, raw);
            args[index] = newIntent;
            return method.invoke(base, args);
        }
        return method.invoke(base,args);
    }
}
