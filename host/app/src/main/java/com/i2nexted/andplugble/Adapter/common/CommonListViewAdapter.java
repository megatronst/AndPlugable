package com.i2nexted.andplugble.Adapter.common;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public abstract class CommonListViewAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> list;
    private ViewGroup parent;
    private SparseArray<Integer> layoutIds;

    /**
     * 只有一种布局时的构造方法
     */
    public CommonListViewAdapter(Context context, List<T> list, int layoutId) {
        this.list = list;
        this.context = context;
        this.layoutIds = new SparseArray<>();
        this.layoutIds.put(0, layoutId);
    }

    public CommonListViewAdapter() {
    }

    /**
     * 有多种布局时的构造方法
     */
    public CommonListViewAdapter(Context context, List<T> list, SparseArray<Integer> layoutIds) {
        this.list = list;
        this.context = context;
        this.layoutIds = layoutIds;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return layoutIds.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.parent = parent;
        int type = getItemViewType(position);
        CommonListViewHolder viewHolder = CommonListViewHolder.get(context, convertView, parent, layoutIds.get(type));
        if (list.size() > 0) {
            updateItemView(context, viewHolder, list.get(position), type, position);
        }

        return viewHolder.getConvertView();
    }

    public abstract void updateItemView(Context context, CommonListViewHolder viewHolder, T itemData, int type, int position);

    public ViewGroup getParent() {
        return parent;
    }

    protected void setList(List<T> list) {
        this.list = list;
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    protected void setLayoutId(int layoutId) {
        this.layoutIds = new SparseArray<>();
        this.layoutIds.put(0, layoutId);
    }
}
