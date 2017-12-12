package com.i2nexted.andplugble;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class TargetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        toast("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        toast("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        toast("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toast("onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        toast("onBackPressed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        toast("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        toast("onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        toast("onRestart");
    }

    private void toast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}
