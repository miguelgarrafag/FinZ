package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;

import com.finz.R;

import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class CreditEvaluationActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_send_request)
    public void sendRequest() {
        startActivity(new Intent(this, ValidProcessActivity.class));
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_credit_evaluation;
    }


}
