package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.finz.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ValidProcessActivity extends BaseActivity {

    public static final String KEY_ICON = "icon";
    public static final String KEY_TITLE = "title";
    public static final String KEY_TEXT = "text";

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        icon.setImageDrawable(ContextCompat.getDrawable(this, getIntent().getIntExtra(KEY_ICON,R.drawable.img_valid_process)));
        title.setText(getString(getIntent().getIntExtra(KEY_TITLE, R.string.str_valid_process)));
        text.setText(getString(getIntent().getIntExtra(KEY_TEXT, R.string.str_request_send)));
    }

    @OnClick(R.id.finish)
    public void backHome() {
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_valid_process;
    }


}
