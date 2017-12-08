package com.i2nexted.andplugble;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.i2nexted.andplugble.Adapter.Adapter_plubin;
import com.i2nexted.andplugble.bean.PluginBean;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.PathClassLoader;

public class InstalledPluginsActivity extends BaseActivity{
    private ListView lvPluginList;
    private ImageView imgOtherApk;
    private Adapter_plubin mAdapter;
    private List<PluginBean> pluginBeens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_installed_plugins);
    }

    @Override
    protected void initParam() {
        pluginBeens = getPlugins();
    }

    @Override
    protected void initView() {
        mAdapter = new Adapter_plubin(this, pluginBeens);
        lvPluginList = (ListView) findViewById(R.id.lv_plugin_list);
        lvPluginList.setAdapter(mAdapter);
        lvPluginList.setOnItemClickListener(this);
        imgOtherApk = (ImageView) findViewById(R.id.img_other_apk);
    }

    private List<PluginBean> getPlugins(){
        List<PluginBean> pluglins = new ArrayList<>();
        PackageManager pm = getPackageManager();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        for(PackageInfo packageInfo:packageInfos){
            String shareId = packageInfo.sharedUserId;
            String packageName = packageInfo.packageName;
            if (!TextUtils.isEmpty(shareId) && shareId.equals("com.i2nexted.andplugble") && !packageName.equals(getPackageName())){
                PluginBean pluginBean = new PluginBean();
                pluginBean.setPackageName(packageName);
                pluginBean.setLabel(pm.getApplicationLabel(packageInfo.applicationInfo).toString());
                pluglins.add(pluginBean);
            }
        }
        return pluglins;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PluginBean plugin = pluginBeens.get(position);
        Context plugnContext = null;
        try {
            plugnContext = this.createPackageContext(plugin.getPackageName(), CONTEXT_IGNORE_SECURITY | CONTEXT_INCLUDE_CODE);
            int resourceId = loadApk(plugin.getPackageName(), plugnContext);
            imgOtherApk.setImageDrawable(plugnContext.getResources().getDrawable(resourceId));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private int loadApk(String packageName, Context pluginContext){
        try {

            PathClassLoader pathclassLoader = new PathClassLoader(pluginContext.getPackageResourcePath(), ClassLoader.getSystemClassLoader());
            Class<?> clazz = Class.forName(packageName + ".R$drawable", true, pathclassLoader);
            Field field = clazz.getDeclaredField("login_logo");
            int resourceId = field.getInt(R.drawable.class);
            return resourceId;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
