package com.finz.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finz.adapter.SliderPagerAdapter;
import com.finz.R;
import com.finz.rest.RestListListener;
import com.finz.rest.slider.RestSlider;
import com.finz.rest.slider.entity.Slider;
import com.finz.util.UtilCore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjection;

public class SliderActivity extends BaseActivity {

    private static final String TAG = SliderActivity.class.getSimpleName();

    @Inject
    RestSlider restSlider;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.dotLayout)
    LinearLayout layoutDot;

    @BindView(R.id.btn_next)
    Button btnNext;

    private List<Slider> sliders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        restSliders();
    }

    private void restSliders() {
        if (!UtilCore.UtilNetwork.isNetworkAvailable(this)) {
            showToastConnection();
            return;
        }
        restSlider.list(new RestListListener<Slider>() {
            @Override
            public void onSuccess(List<Slider> slider) {
                sliders.addAll(slider);
                init();
            }

            @Override
            public void onError(int statusCode, String message) {
                validateErrorResponse(TAG, statusCode, message,
                        null, null, null,
                        null, null);
            }
        });
    }

    private void init(){
        SliderPagerAdapter pagerAdapter = new SliderPagerAdapter(this, sliders);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int i, float v, int i1) {}
            @Override public void onPageScrollStateChanged(int i) {}
            @Override public void onPageSelected(int i) {
                if (i == sliders.size() - 1) btnNext.setText(getString(R.string.finish));
                else btnNext.setText(getString(R.string.next));
                setDotStatus(i);
            }
        });

        setDotStatus(0);
        btnNext.setOnClickListener(v -> {
            int currentPage = viewPager.getCurrentItem() + 1;
            if (currentPage < sliders.size())
                viewPager.setCurrentItem(currentPage);
            else {
                prefs.setSliders(true);
                startActivity(new Intent(SliderActivity.this, MenuActivity.class));
                finish();
            }
        });
    }

    private void setDotStatus(int page) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(30,0,30,0);
        layoutDot.removeAllViews();
        TextView[] dotstv = new TextView[sliders.size()];
        for (int i = 0; i < dotstv.length; i++) {
            dotstv[i] = new TextView(this);
            dotstv[i].setText(Html.fromHtml("&#8226"));
            dotstv[i].setLayoutParams(params);
            layoutDot.addView(dotstv[i]);
            if (i == page) {
                dotstv[i].setTextColor(Color.parseColor("#ffffff"));
                dotstv[i].setTextSize(35);
            }else {
                dotstv[i].setTextColor(Color.parseColor("#cadcff"));
                dotstv[i].setTextSize(25);
            }
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_slider;
    }
}
