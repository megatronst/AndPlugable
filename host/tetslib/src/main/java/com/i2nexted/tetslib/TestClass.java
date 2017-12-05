package com.i2nexted.tetslib;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/12/4.
 */

public class TestClass {
    public static void invokeStaticMethod(Context context){
        Toast.makeText(context, "static method", Toast.LENGTH_SHORT).show();
    }

    public static void invokeNormalMethod(Context context){
        Toast.makeText(context, "normal method", Toast.LENGTH_SHORT).show();
    }
}
