package com.i2nexted.andplugble.proxys;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.i2nexted.andplugble.R;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/12/6.
 */

public class EvilInstumentation extends Instrumentation {
    private Instrumentation mBase;

    public EvilInstumentation(Instrumentation mBase) {
        this.mBase = mBase;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) throws NoSuchMethodException {
        Toast.makeText(who, R.string.toast_about_to_start_a_activity, Toast.LENGTH_SHORT).show();
        Class[] paramClassArr = new Class[]{
                android.content.Context.class,
                android.os.IBinder.class,
                android.os.IBinder.class,
                android.app.Activity.class,
                android.content.Intent.class,
                int.class,
                android.os.Bundle.class};

        Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity", paramClassArr);
        execStartActivity.setAccessible(true);
        try {
            return  (ActivityResult) execStartActivity.invoke(mBase, who, contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(who, R.string.toast_rom_modyfied, Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
