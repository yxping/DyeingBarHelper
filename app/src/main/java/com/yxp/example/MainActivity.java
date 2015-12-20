package com.yxp.example;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yxp.dyeingbarhelper.DyeingBarHelper;

public class MainActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DyeingBarHelper helper = new DyeingBarHelper(this);
        helper.setStatusBarColor(Color.RED);
        helper.setNavigationBarColor(Color.RED);
    }
}
