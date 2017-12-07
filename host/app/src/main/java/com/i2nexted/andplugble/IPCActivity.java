package com.i2nexted.andplugble;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class IPCActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnBind;
    private Button btnUnbind;
    private Button btnRemoteCompute;

    private ICompute binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        initView();

    }

    private void initView() {
        btnBind = (Button) findViewById(R.id.btn_bind);
        btnBind.setOnClickListener(this);
        btnUnbind = (Button) findViewById(R.id.btn_unbind);
        btnUnbind.setOnClickListener(this);
        btnRemoteCompute = (Button) findViewById(R.id.btn_remote_compute);
        btnRemoteCompute.setOnClickListener(this);
    }


    private boolean isDissConnected = true;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = ICompute.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(IPCActivity.this, "disconnetted", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bind:
                Intent intent = new Intent(this, MyService.class);
//                intent.setAction("com.i2nexted.andplugble.ICoupute");
                bindService(intent, connection, BIND_AUTO_CREATE);
                isDissConnected = false;
                break;
            case R.id.btn_unbind:
                if (!isDissConnected){
                    unbindService(connection);
                    isDissConnected = true;
                }else {
                    Toast.makeText(this, "服务已解除绑定", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_remote_compute:
                if (binder == null){
                    Toast.makeText(this, R.string.toast_please_bind_service, Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Toast.makeText(this, "234与123的和为：" + binder.add(234, 123), Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
