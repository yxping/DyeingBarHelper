package com.yxp.dyeingbarhelper;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by yanxing on 15/12/18.
 */
public class SystemBarConfig {
    public final static String TAG = "SystemBarConfig";
    private Resources res;
    private Context mContext;
    private boolean isPortrait;

    public SystemBarConfig(Context context) {
        this.mContext = context;
        this.res = context.getResources();
        this.isPortrait = (res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否bar是否通过代码或xml设置了透明
     *
     * @return
     */
    public boolean[] isBarTranslucent() {
        boolean isStatusBarTranslucent = false;
        boolean isNavigationBarTranslucent = false;
        // 以下方法在需要在api 19中使用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 判断是否在theme里面定义了bar得透明
            int[] attrs = new int[]{android.R.attr.windowTranslucentStatus,
                    android.R.attr.windowTranslucentStatus};
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
            try {
                isStatusBarTranslucent = typedArray.getBoolean(0, false);
                isStatusBarTranslucent = typedArray.getBoolean(1, false);
            } finally {
                typedArray.recycle();
            }

            // 判断是否在代码中设置了bar透明
            Window window = ((Activity) mContext).getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if ((bits & layoutParams.flags) != 0) {
                isStatusBarTranslucent = true;
            }
            bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            if ((bits & layoutParams.flags) != 0) {
                isNavigationBarTranslucent = true;
            }
        }
        return new boolean[]{isStatusBarTranslucent, isNavigationBarTranslucent};
    }

    /**
     * 在Android l以上判断是否有NavigationBar的方法,通过查看phoneWindowManager源码可以知道这个方法
     *
     * @return
     */
    public boolean hasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = mContext.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            Log.w(TAG, e);
        }

        return hasNavigationBar;
    }

    /**
     * 获取Status Bar的高度
     */
    public int getStatusBarHeight() {
        String key = "status_bar_height";
        return getInternalDimensionSize(key);
    }

    /**
     * 获取Navigation Bar的高度
     *
     * @return
     */
    public int getNavigationBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavigationBar()) {
                String key;
                if (isPortrait) {
                    key = "navigation_bar_height";
                } else {
                    key = "navigation_bar_height_landscape";
                }
                return getInternalDimensionSize(key);
            }
        }
        return 0;
    }

    /**
     * 获取Navigation Bar的宽度
     *
     * @return
     */
    public int getNavigationBarWidth() {
        String key = "navigation_bar_width";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavigationBar()) {
                return getInternalDimensionSize(key);
            }
        }
        return 0;
    }

    /**
     * 获取指定资源的值
     *
     * @param key
     * @return
     */
    private int getInternalDimensionSize(String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public boolean isNavigationBarAtBottom() {
        return isPortrait;
    }
}
