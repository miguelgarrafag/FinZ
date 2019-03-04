package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;

import com.finz.R;

import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class MenuActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.button_disposition)
    public void disposition() {
        startActivity(new Intent(this, PrincipalActivity.class));
    }

    @OnClick(R.id.btn_change_money)
    public void change() {
        startActivity(new Intent(this, ChangeMoneyActivity.class));
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
