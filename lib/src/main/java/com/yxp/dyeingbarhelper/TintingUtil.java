package com.yxp.dyeingbarhelper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

/**
 * 取出一个view的背景颜色
 * Created by yanxing on 15/12/23.
 */
public class TintingUtil {
    public final static String TAG = "TintingUtil";
    private static TintingUtil util;
    public final static int DEFAULT_DIS = 8;

    public static TintingUtil getInstance() {
        if (util == null) {
            synchronized (TintingUtil.class) {
                if (util == null) {
                    util = new TintingUtil();
                }
            }
        }
        return util;
    }

    /**
     * 获取控件颜色的平均值
     * @param view
     * @param dis 间隔取点,影响方法耗时
     * @return
     */
    public int getAverColorFromView(View view, int dis) {
        Bitmap bitmap = createBitmapFromView(view);
        if (bitmap == null) {
            return 0;
        }
        if (dis < 1) {
            dis = DEFAULT_DIS;
        }

        int x = bitmap.getWidth();
        int y = bitmap.getHeight();
        int num = 0;
        int color = 0;
        for (int i = 0; i < x; i += dis) {
            for (int j = 0; j < y; j += dis) {
                color += bitmap.getPixel(i, j);
                num++;
            }
        }
        return color / num;
    }

    /**
     * 获取提供的view中占有面积最多的颜色
     * @param view
     * @param dis  间隔取点,影响方法耗时
     * @return
     */
    public int getMaxColorFromView(View view, int dis) {
        Bitmap bitmap = createBitmapFromView(view);
        if (bitmap == null) {
            return 0;
        }
        if (dis <= 0) {
            dis = DEFAULT_DIS;
        }
        SparseArray<Integer> array = new SparseArray<>();
        int color = 0;
        int x = bitmap.getWidth();
        int y = bitmap.getHeight();
        for (int i = 0; i < x; i += dis) {
            for (int j = 0; j < y; j += dis) {
                color = bitmap.getPixel(i, j);
                if (array.get(color) == null) {
                    array.put(color, 1);
                } else {
                    array.put(color, array.get(color) + 1);
                }
            }
        }
        int max = -1;
        for (int i = 0; i < array.size(); i++) {
            if (max < array.get(array.keyAt(i))) {
                max = array.get(array.keyAt(i));
                color = array.keyAt(i);
            }

        }
        if (max > 0) {
            return color;
        }
        return 0;
    }

    /**
     * 从view中去创造一个bitmap
     * @param view
     * @return
     */
    protected Bitmap createBitmapFromView(View view) {

        if (view instanceof ImageView) {
            Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable != null && drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }
        }

        // 有可能为0, 比如在activity正在onCreate时,不建议进行此操作
        int width = view.getWidth();
        int height = view.getHeight();

        Bitmap bitmap = createBitmapSafely(width, height, Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            canvas.setBitmap(null);
            canvas = null;
        }
        return bitmap;
    }

    /**
     *
     * @param width
     * @param height
     * @param config
     * @param times  create bitmap的次数,决定于当前的内存
     * @return
     */
    protected Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int times) {
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            if (times > 0) {
                System.gc();
                return createBitmapSafely(width, height, config, --times);
            }
        } catch (Exception e) {
            Log.e(TAG, "view width or height shuold not < 0");
        }
        return null;
    }
}
