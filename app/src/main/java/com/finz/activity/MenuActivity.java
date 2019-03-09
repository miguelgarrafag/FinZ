package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;

import com.finz.R;

import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class MenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        if(!prefs.getSliders()){
            startActivity(new Intent(this, SliderActivity.class));
            finish();
        }
    }

    @OnClick(R.id.button_disposition)
    public void disposition() {
        startActivity(new Intent(this, DispositionMoneyActivity.class));
    }

    @OnClick(R.id.btn_change_money)
    public void change() {
        /*startActivity(new Intent(this, ChangeMoneyActivity.class));*/
        showToastLong(R.string.str_soon);
    }

    @OnClick(R.id.btn_credit_evaluation)
    public void evaluation() {
        startActivity(new Intent(this, CreditEvaluationActivity.class));
    }

    @OnClick(R.id.img_profile)
    public void profile() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_menu;
    }


}
