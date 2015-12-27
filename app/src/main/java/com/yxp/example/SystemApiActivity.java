package com.yxp.example;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by yanxing on 15/12/27.
 */
public class SystemApiActivity extends Activity {
    private TextView mText;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        mText = (TextView) findViewById(R.id.text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.GREEN);
            getWindow().setNavigationBarColor(Color.GREEN);
            mText.setText("看,轻而易举的实现了translucent bar的效果,但是目前可进行定制的能力不强");
        } else {
            mText.setText("系统api要求21以上");
        }

    }
}
