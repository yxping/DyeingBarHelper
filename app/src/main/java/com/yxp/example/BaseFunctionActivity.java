package com.yxp.example;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.yxp.dyeingbarhelper.DyeingBarHelper;

/**
 * Created by yanxing on 15/12/25.
 */
public class BaseFunctionActivity extends Activity {
    private Button mRedBtn;
    private Button mGreenBtn;
    private Button mBlueBtn;
    private SeekBar mSeekBar;
    private DyeingBarHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mRedBtn = (Button) findViewById(R.id.red_btn);
        mGreenBtn = (Button) findViewById(R.id.green_btn);
        mBlueBtn = (Button) findViewById(R.id.blue_btn);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);

        setListener();

        // 设置状态栏和导航栏为透明,否则无法达到效果
        DyeingBarHelper.setBarTranslucent(this);
        mHelper = new DyeingBarHelper(this);

        mSeekBar.setProgress(0);
        mHelper.setStatusBarViewAlpha(0);
        mHelper.setNavigationBarViewAlpha(0);
    }

    public void setListener() {
        mRedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.setStatusBarColor(Color.RED);
                mHelper.setNavigationBarColor(Color.RED);
            }
        });
        mGreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.setStatusBarColor(Color.GREEN);
                mHelper.setNavigationBarColor(Color.GREEN);
            }
        });
        mBlueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.setStatusBarColor(Color.BLUE);
                mHelper.setNavigationBarColor(Color.BLUE);
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mHelper.setStatusBarViewAlpha(progress / 255.0f);
                mHelper.setNavigationBarViewAlpha(progress / 255.0f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
