package com.i2nexted.andplugble.proxys;

import android.content.Context;
import android.widget.Toast;

import com.i2nexted.andplugble.R;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/12/8.
 */

public class IActivityManagerHandler implements InvocationHandler {
    private Object base;
    private Context context;
    public IActivityManagerHandler(Object base, Context context) {
        this.base = base;
        this.context = context;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Toast.makeText(context, R.string.text_activity_hooked, Toast.LENGTH_SHORT).show();
        return method.invoke(base,args);
    }
}
