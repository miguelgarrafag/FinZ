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
    public void Disposition(){
        startActivity(new Intent(this, PrincipalActivity.class));
    }

    @OnClick(R.id.btn_change_money)
    public void Change(){
        startActivity(new Intent(this, ChangeMoneyActivity.class));
    }

    @OnClick(R.id.btn_credit_evaluation)
    public void Evaluation(){
        startActivity(new Intent(this, CreditEvaluationActivity.class));
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_menu;
    }


}
