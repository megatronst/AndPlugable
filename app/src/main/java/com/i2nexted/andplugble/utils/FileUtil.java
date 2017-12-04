package com.i2nexted.andplugble.utils;

import android.app.backup.RestoreObserver;
import android.content.Context;
import android.provider.DocumentsContract;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by Administrator on 2017/12/4.
 */

public class FileUtil {
    private static Context mContext;
    public static final String CLASS_DIR = "classDir" +File.separatorChar;
    private static String ROOT = null;

    public static void init(Context context){
        mContext = context;
    }
    public static String getDir(String subDir){
        if (TextUtils.isEmpty(ROOT)){
            ROOT = mContext.getExternalCacheDir().toString() + File.separatorChar;
        }
        File file = new File(ROOT + subDir);
        if (!file.exists()){
            file.mkdir();
        }
        return file.getAbsolutePath() + File.separatorChar;
    }
}
