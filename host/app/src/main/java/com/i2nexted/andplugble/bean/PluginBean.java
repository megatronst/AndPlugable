package com.i2nexted.andplugble.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/12/5.
 */

public class PluginBean implements Parcelable {
    private String packageName;
    private String label;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeString(this.label);
    }

    public PluginBean() {
    }

    protected PluginBean(Parcel in) {
        this.packageName = in.readString();
        this.label = in.readString();
    }

    public static final Parcelable.Creator<PluginBean> CREATOR = new Parcelable.Creator<PluginBean>() {
        @Override
        public PluginBean createFromParcel(Parcel source) {
            return new PluginBean(source);
        }

        @Override
        public PluginBean[] newArray(int size) {
            return new PluginBean[size];
        }
    };
}
