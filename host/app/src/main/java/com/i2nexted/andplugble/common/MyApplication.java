package com.i2nexted.andplugble.common;

import android.app.Application;
import android.content.Context;

import com.i2nexted.andplugble.utils.FileUtil;

/**
 * Created by Administrator on 2017/12/4.
 */

public class MyApplication extends Application {
    private static Context instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initFileUtil();
    }

    public static Context getInstance(){
        return instance;
    }

    private void initFileUtil(){
        FileUtil.init(this);
    }
}
