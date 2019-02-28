package com.finz.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finz.adapter.MyPagerAdapter;
import com.finz.R;

import butterknife.BindView;
import dagger.android.AndroidInjection;

public class SliderActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.dotLayout)
    LinearLayout layoutDot;

    @BindView(R.id.btn_next)
    Button btnNext;

    private MyPagerAdapter pagerAdapter;
    private int[] layouts;
    private TextView[] dotstv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        layouts = new int[]{R.layout.slider_1, R.layout.slider_2, R.layout.slider_3, R.layout.slider_4};
        pagerAdapter = new MyPagerAdapter(layouts, getApplicationContext());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == layouts.length - 1) {
                    btnNext.setText(getString(R.string.finish));
                } else {
                    btnNext.setText(getString(R.string.next));
                }
                setDotStatus(i);
            }
        });

        setDotStatus(0);

        btnNext.setOnClickListener(v -> {
            int currentPage = viewPager.getCurrentItem() + 1;
            if (currentPage < layouts.length) {
                viewPager.setCurrentItem(currentPage);
            } else {
                startActivity(new Intent(SliderActivity.this, MenuActivity.class));
                finish();
            }
        });

    }

    private void setDotStatus(int page) {
        layoutDot.removeAllViews();
        dotstv = new TextView[layouts.length];
        for (int i = 0; i < dotstv.length; i++) {
            dotstv[i] = new TextView(this);
            dotstv[i].setText(Html.fromHtml("&#8226"));
            dotstv[i].setTextSize(30);
            layoutDot.addView(dotstv[i]);
            if (i == page) {
                dotstv[i].setTextColor(Color.parseColor("#ffffff"));
            } else {
                dotstv[i].setTextColor(Color.parseColor("#a9b4bb"));
            }
        }
        if (dotstv.length > 0) {

        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }
}
