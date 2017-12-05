package com.i2nexted.andplugble.common;

import android.app.Application;

import com.i2nexted.andplugble.utils.FileUtil;

/**
 * Created by Administrator on 2017/12/4.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initFileUtil();
    }

    private void initFileUtil(){
        FileUtil.init(this);
    }
}
