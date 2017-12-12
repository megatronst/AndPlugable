package com.i2nexted.andplugble.proxys;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/12/12.
 */

public class ActivityThreadHandlerCallback implements Handler.Callback {
    private final int WHAT_HANDLE_UNREGISTED_ACTIVITY = 100;
    private Handler base;

    public ActivityThreadHandlerCallback(Handler base) {
        this.base = base;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case WHAT_HANDLE_UNREGISTED_ACTIVITY:
                handleLaunchActivity(msg);
                break;
        }
        base.handleMessage(msg);
        return true;
    }

    private void handleLaunchActivity(Message msg){
        try {
            Object act = msg.obj;
            Field intent = act.getClass().getDeclaredField("intent");
            intent.setAccessible(true);
            Intent raw = (Intent)intent.get(act);
            Intent target = raw.getParcelableExtra(IActivityManagerHandler.EXTRA_TARGET_INTENT);
            raw.setComponent(target.getComponent());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
