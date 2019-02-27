package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;

import com.finz.R;

import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class ValidProcessActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_back_home)
    public void backHome() {
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_valid_process;
    }


}
