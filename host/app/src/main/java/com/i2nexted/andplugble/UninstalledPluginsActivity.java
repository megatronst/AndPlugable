package com.i2nexted.andplugble;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.i2nexted.andplugble.Adapter.Adapter_plubin;
import com.i2nexted.andplugble.Adapter.common.Adapter_uninstalled;
import com.i2nexted.andplugble.bean.PluginBean;
import com.i2nexted.andplugble.utils.FileUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class UninstalledPluginsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView lvPluginList;
    private ImageView imgOtherApk;



    private Adapter_uninstalled mAdapter;
    private List<ApplicationInfo> pluginBeens;
    private List<String> apkPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uninstalled_plugins);
        initListview();
        initData();
        getApks();

    }

    private void initData() {
        getPlugins();
    }

    private void initListview() {
        pluginBeens = new ArrayList<>();
        apkPath = new ArrayList<>();
        mAdapter = new Adapter_uninstalled(this,pluginBeens, R.layout.item_uninstalled);
        lvPluginList = (ListView) findViewById(R.id.lv_plugin_list);
        lvPluginList.setAdapter(mAdapter);
        lvPluginList.setOnItemClickListener(this);
        imgOtherApk = (ImageView) findViewById(R.id.img_other_apk);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        loadApk(apkPath.get(position), pluginBeens.get(position).packageName);
    }

    private void loadApk(String apkPath, String packageName){
        try {
            String dexPath = apkPath;
            String dexOutPath = getDir("apk2", 0).getAbsolutePath();
            DexClassLoader dexClassLoader = new DexClassLoader(dexPath, dexOutPath, null, ClassLoader.getSystemClassLoader());
//            Class clazz = dexClassLoader.loadClass(packageName + ".R$drawable");
            Class clazz = dexClassLoader.loadClass(packageName + ".R$layout");
            Field resFied = clazz.getDeclaredField("activity_main");
//            int resId = resFied.getInt(R.drawable.class);
            int resId = resFied.getInt(R.layout.class);
            Resources resource = getApkResource(apkPath);
            if (resource != null){
//                ((ImageView)findViewById(R.id.img_other_apk)).setImageDrawable(resource.getDrawable(resId));
//                setContentView(resource.getLayout(resId));

                XmlResourceParser xmlResourceParser = resource.getXml(resId);
                View view = LayoutInflater.from(this).inflate(xmlResourceParser, null);
                setContentView(view);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    private void getPlugins(){
        List<ApplicationInfo> applicationInfos = new ArrayList<>();
        File dir = new File(FileUtil.getDir(FileUtil.CLASS_DIR) + "/");
        if (dir != null){
            File[] files = dir.listFiles();
            if (files != null && files.length>0){
                for (int i=0; i<files.length;i++){
                    if (files[i].getPath().endsWith(".apk")){
                        applicationInfos.add(getApplicationInfo(UninstalledPluginsActivity.this, files[i].getPath()));
                        pluginBeens.clear();
                        pluginBeens.addAll(applicationInfos);
                        apkPath.add(files[i].getAbsolutePath());
                        if (mAdapter != null){
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }
    private ApplicationInfo getApplicationInfo(Context context, String archiveFilePath){
        PackageManager packageManager = getPackageManager();
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
        if (packageInfo != null){
            applicationInfo = packageInfo.applicationInfo;
        }
        return applicationInfo;
    }

    private void getApks(){
        AndPermission.with(this)
                .permission(Permission.STORAGE)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        Toast.makeText(UninstalledPluginsActivity.this, "granted", Toast.LENGTH_SHORT).show();
                        getPlugins();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        Toast.makeText(UninstalledPluginsActivity.this, "not granted", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }

    private Resources getApkResource(String apkName){
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAsset = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAsset.invoke(assetManager, apkName);
            Resources resource = this.getResources();
            Resources mRsource = new Resources(assetManager, resource.getDisplayMetrics(), resource.getConfiguration());
            return mRsource;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
