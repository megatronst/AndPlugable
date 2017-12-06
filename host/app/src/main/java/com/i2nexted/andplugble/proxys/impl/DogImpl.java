package com.i2nexted.andplugble.proxys.impl;

import android.widget.Toast;
import com.i2nexted.andplugble.common.MyApplication;
import com.i2nexted.andplugble.proxys.interfaces.IDog;

public class DogImpl implements IDog {

        @Override
        public void bark() {
            Toast.makeText(MyApplication.getInstance(), "wang wang wang", Toast.LENGTH_SHORT).show();
        }
    }