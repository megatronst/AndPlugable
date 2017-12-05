package com.i2nexted.andplugble.Adapter.common;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Administrator on 2017/2/20.
 * 所有listview适配器的父类，封装常用方法
 */

public class CommonListViewHolder {
    private final SparseArray<View> mViews;
    private View convertView;
    private Context context;

    public CommonListViewHolder(Context context, ViewGroup parent, int layoutId) {
        this.context = context;
        this.mViews = new SparseArray<>();
        this.convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.convertView.setTag(this);
    }

    /**
     * 获取一个viewholder对象，更具convertView是否初始化通过不同的方式获取viewholder
     */
    public static CommonListViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new CommonListViewHolder(context, parent, layoutId);
        }
        return (CommonListViewHolder) convertView.getTag();
    }

    /**
     * 根据控件的id获取对应的控件对象，若还没有初始化过则初始化后添加入mViews中最后返回
     */
    public <T extends View> T getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = convertView.findViewById(id);
            if (view != null) {
                mViews.put(id, view);
            }

        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 设置TextView的值
     */
    public void setText(int id, String value) {
        if (!TextUtils.isEmpty(value)) {
            TextView textView = getView(id);
            if (textView != null) {
                textView.setText(value);
            }
        }
    }

    /**
     * 设置EditText的值
     */
    public void setEditText(int id, String value) {
        if (!TextUtils.isEmpty(value)) {
            EditText editText = getView(id);
            editText.setText(value);
        }
    }

    /**
     * 设置textView的字体颜色
     */
    public void setTextColor(int id, int color) {
        TextView textView = getView(id);
        textView.setTextColor(color);
    }

    /**
     * 设置ImageView的图片
     */
    public void setImageViewImg(int id, int drawableId) {
        ImageView imageView = getView(id);
        imageView.setImageResource(drawableId);
    }



    /**
     * 设置空间隐藏和显示
     */
    public void setViewVisible(int id, boolean isVisible) {
        View view = getView(id);
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置textVIEW的背景图片
     */
    public void setTextViewBack(int id, int drawableId) {
        TextView textView = getView(id);
        textView.setBackgroundResource(drawableId);
    }

    /**
     * 设置textVIEW的背景颜色
     */
    public void setTextViewBackColor(int id, String color) {
        TextView textView = getView(id);
        textView.setBackgroundColor(Color.parseColor(color));
    }


    /**
     * 设置EditText的提示
     */
    public void setHint(int id, String hintString) {
        EditText editText = getView(id);
        editText.setHint(hintString);
    }

    /**
     * 设置点击事件监听
     */
    public void setOnClickListener(int id, View.OnClickListener listener) {
        if (getView(id) != null && listener != null) {
            getView(id).setOnClickListener(listener);
        }
    }
}
