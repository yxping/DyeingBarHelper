package com.yxp.dyeingbarhelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by yxp on 15/12/18.
 */
public class DyeingBarHelper {

    public final static int DEFAULT_BAR_COLOR = 0x99000000;

    private Context mContext;
    private View mStatusBarView;
    private View mNavigationBarView;
    private boolean isNavigationBarAvailable = false;
    private boolean isStatusBarAvailable = false;
    private SystemBarConfig mConfig;

    public DyeingBarHelper(Context context) {
        this.mContext = context;
        init();
    }

    /**
     * 此方法提供给自定义status bar和navigation bar的样式
     *
     * @param context
     * @param statusBarView
     * @param navBarView
     */
    public DyeingBarHelper(Context context, View statusBarView, View navBarView) {
        this.mStatusBarView = statusBarView;
        this.mNavigationBarView = navBarView;
        this.mContext = context;
        init();
    }

    private void init() {

        mConfig = new SystemBarConfig(mContext);

        // 判断是否设置了status bar透明和navigation bar透明
        boolean[] result = mConfig.isBarTranslucent();
        isStatusBarAvailable = result[0];
        isNavigationBarAvailable = result[1];

        // 如果有,判断是否设置了透明,若没有则设置透明度
//        this.setBarTranslucent();

        // 判断是否有Navigation Bar底部导航栏
        isNavigationBarAvailable = mConfig.hasNavigationBar();

        // 获取窗口的最顶层View: decorViewGroup
        ViewGroup decorViewGroup = (ViewGroup) ((Activity) mContext).getWindow().getDecorView();

        // 获取Status Bar,设置Status Bar的View
        if (isStatusBarAvailable) {
            setStatusBarView(decorViewGroup);
        }

        // 获取Navigation Bar,设置Navigation Bar的view
        if (isNavigationBarAvailable) {
            setNavigationBarView(decorViewGroup);
        }
    }

    /**
     * 设置状态栏透明
     */
    public static void setBarTranslucent(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }


    /**
     * 设置顶部Status Bar的view
     *
     * @param decorViewGroup
     */
    private void setStatusBarView(ViewGroup decorViewGroup) {
        if (mStatusBarView == null) {
            mStatusBarView = new View(mContext);
        }
        FrameLayout.LayoutParams params;
        params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                mConfig.getStatusBarHeight());
        params.gravity = Gravity.TOP;
        // 如果是横屏,则应该减去右边navigation bar的宽度,防止挡住navigation bar
        if (!mConfig.isNavigationBarAtBottom()) {
            params.rightMargin = mConfig.getNavigationBarWidth();
        }
        mStatusBarView.setLayoutParams(params);
        mStatusBarView.setBackgroundColor(DEFAULT_BAR_COLOR);
        decorViewGroup.addView(mStatusBarView);
    }

    /**
     * 设置底部Navigation Bar的view
     *
     * @param decorViewGroup
     */
    private void setNavigationBarView(ViewGroup decorViewGroup) {
        if (mNavigationBarView == null) {
            mNavigationBarView = new View(mContext);
        }
        FrameLayout.LayoutParams params;
        if (mConfig.isNavigationBarAtBottom()) {
            params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mConfig.getNavigationBarHeight());
            params.gravity = Gravity.BOTTOM;
        } else {
            params = new FrameLayout.LayoutParams(mConfig.getNavigationBarWidth(), FrameLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.RIGHT;
        }
        mNavigationBarView.setLayoutParams(params);
        mNavigationBarView.setBackgroundColor(DEFAULT_BAR_COLOR);
        decorViewGroup.addView(mNavigationBarView);
    }

    /**
     * 通过resId,设置Status Bar的背景
     *
     * @param resId
     */
    public void setStatusBarRes(int resId) {
        if (isStatusBarAvailable) {
            mStatusBarView.setBackgroundResource(resId);
        }
    }

    /**
     * 通过resId,设置Navigation Bar的背景
     *
     * @param resId
     */
    public void setNavigationBarRes(int resId) {
        if (isNavigationBarAvailable) {
            mNavigationBarView.setBackgroundResource(resId);
        }
    }

    /**
     * 设置Status Bar的颜色
     *
     * @param color
     */
    public void setStatusBarColor(int color) {
        if (isStatusBarAvailable) {
            mStatusBarView.setBackgroundColor(color);
        }
    }

    /**
     * 设置Navigation Bar的颜色
     *
     * @param color
     */
    public void setNavigationBarColor(int color) {
        if (isNavigationBarAvailable) {
            mNavigationBarView.setBackgroundColor(color);
        }
    }

    /**
     * 设置Status填充的view是否可见
     *
     * @param visibility
     */
    public void setStatusBarViewVisibility(int visibility) {
        if (isStatusBarAvailable) {
            mStatusBarView.setVisibility(visibility);
        }
    }

    /**
     * 设置Navigation bar填充的view是否可见
     *
     * @param visibility
     */
    public void setNavigationBarViewVisibility(int visibility) {
        if (isNavigationBarAvailable) {
            mNavigationBarView.setVisibility(visibility);
        }
    }

    /**
     * 设置status bar的透明度
     *
     * @param alpha
     */
    public void setStatusBarViewAlpha(float alpha) {
        if (isStatusBarAvailable) {
            mStatusBarView.setAlpha(alpha);
        }
    }

    /**
     * 设置Navigation bar的透明度
     *
     * @param alpha
     */
    public void setNavigationBarViewAlpha(float alpha) {
        if (isNavigationBarAvailable) {
            mNavigationBarView.setAlpha(alpha);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setStatusBarViewBackground(Drawable drawable) {
        if (isStatusBarAvailable) {
            mStatusBarView.setBackground(drawable);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setNavigationBarViewBackground(Drawable drawable) {
        if (isStatusBarAvailable) {
            mStatusBarView.setBackground(drawable);
        }
    }

    /**
     * 获取status bar填充的view
     *
     * @return
     */
    public View getStatusBarView() {
        return mStatusBarView;
    }

    /**
     * 获取navigation bar填充的view
     *
     * @return
     */
    public View getNavigationBarView() {
        return mNavigationBarView;
    }

}
