package com.yxp.example;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button mSlideBtn;
    private Button mBaseBtn;
    private Button mSystemBtn;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlideBtn = (Button) findViewById(R.id.btn_slide);
        mSlideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
            }
        });

        mBaseBtn = (Button) findViewById(R.id.btn_base);
        mBaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BaseFunctionActivity.class));
            }
        });
        mSystemBtn = (Button) findViewById(R.id.btn_system);
        mSystemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SystemApiActivity.class));
            }
        });
    }

}
