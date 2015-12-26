package com.yxp.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.yxp.dyeingbarhelper.DyeingBarHelper;
import com.yxp.dyeingbarhelper.TintingUtil;

import java.util.ArrayList;

/**
 * Created by yanxing on 15/12/25.
 */
public class ViewPagerActivity extends Activity {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private ArrayList<View> viewList;
    private View view1, view2, view3;
    private DyeingBarHelper helper;
    private boolean isInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        // 设置状态栏和导航栏为透明,否则无法达到效果
        DyeingBarHelper.setBarTranslucent(this);

        helper = new DyeingBarHelper(this);

        initViewPager();
    }

    public void initViewPager() {
        viewList = new ArrayList<>();
        view1 = LayoutInflater.from(this).inflate(R.layout.view_layout1, viewPager);
        view2 = LayoutInflater.from(this).inflate(R.layout.view_layout2, viewPager);
        view3 = LayoutInflater.from(this).inflate(R.layout.view_layout3, viewPager);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setBarColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isInit) {
                    viewPager.setCurrentItem(0);
                    setBarColor(0);
                    isInit = true;
                }
            }
        });


    }

    private void setBarColor(int position) {
        int color = TintingUtil.getInstance().getMaxColorFromView(viewList.get(position), 30);
        helper.setStatusBarColor(color);
        helper.setNavigationBarColor(color);
    }
}
