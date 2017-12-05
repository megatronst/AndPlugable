package com.i2nexted.archiveapk;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Button btnOpenOtherAct;
    private Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
//
        if (savedInstanceState != null){
            TextView textView = new TextView(this.activity);
            textView.setText("this is the second activity");
            this.activity.setContentView(textView);
        }else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }


//        btnOpenOtherAct = (Button) findViewById(R.id.btn_open_other_act);
//        btnOpenOtherAct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, SecondActivity.class));
//            }
//        });
    }

    private void setActivity(Activity activity){
        this.activity = activity;
        Toast.makeText(activity, "setActivity", Toast.LENGTH_SHORT).show();
    }

}
