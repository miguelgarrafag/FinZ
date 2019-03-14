package com.finz.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.finz.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SliderPresentationActivity extends BaseActivity {

    public static final String TAG = SliderPresentationActivity.class.getSimpleName();

    public static final String KEY_IMAGE = "image";
    public static final String KEY_ICON = "icon";
    public static final String KEY_TITLE = "title";
    public static final String KEY_TEXT = "text";

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title.setText(getString(getIntent().getIntExtra(KEY_TITLE, R.string.disposition_money)));
        text.setText(getString(getIntent().getIntExtra(KEY_TEXT, R.string.splash_disposition_text)));

        image.setImageDrawable(ContextCompat.getDrawable(this, getIntent().getIntExtra(KEY_IMAGE, R.drawable.ic_disposition_splash)));
        icon.setImageDrawable(ContextCompat.getDrawable(this, getIntent().getIntExtra(KEY_ICON, R.drawable.img_profile_disposition)));
    }

    @OnClick(R.id.next)
    void onClickNext(){
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_slider_presentation;
    }
}
