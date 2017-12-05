package com.i2nexted.andplugble.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.i2nexted.andplugble.R;
import com.i2nexted.andplugble.bean.PluginBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class Adapter_plubin extends BaseAdapter {
    private Context mContext;
    private List<PluginBean> plugins;

    public Adapter_plubin(Context mContext, List<PluginBean> plugins) {
        this.mContext = mContext;
        this.plugins = plugins;
    }

    @Override
    public int getCount() {
        return plugins.size();
    }

    @Override
    public PluginBean getItem(int position) {
        return plugins.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PluginBean pluginBean = plugins.get(position);
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_plugin, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView)convertView.findViewById(R.id.tv_plugin_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(pluginBean.getLabel());
        return convertView;
    }


    public static class ViewHolder{
        public TextView textView;
    }
}
