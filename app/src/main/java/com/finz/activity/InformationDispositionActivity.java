package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.finz.R;

import butterknife.BindView;
import dagger.android.AndroidInjection;

public class InformationDispositionActivity extends BaseActivity {

    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        btnNext.setOnClickListener(v -> {
            startActivity(new Intent(InformationDispositionActivity.this, DispositionMoneyActivity.class));
            finish();
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_information_disposition;
    }
}