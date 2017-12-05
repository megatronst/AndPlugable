package com.i2nexted.andplugble.Adapter.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.i2nexted.andplugble.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class Adapter_uninstalled extends CommonListViewAdapter<ApplicationInfo> {
    PackageManager pm;
    public Adapter_uninstalled(Context context, List<ApplicationInfo> list, int layoutId) {
        super(context, list, layoutId);
        pm = context.getPackageManager();
    }

    @Override
    public void updateItemView(Context context, CommonListViewHolder viewHolder, ApplicationInfo itemData, int type, int position) {
        ((ImageView)(viewHolder.getView(R.id.img_icon))).setImageDrawable(pm.getApplicationIcon(itemData));
        ((TextView)viewHolder.getView(R.id.tv_app_name)).setText(pm.getApplicationLabel(itemData));
    }
}
