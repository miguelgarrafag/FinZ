package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.finz.R;

import butterknife.BindView;
import dagger.android.AndroidInjection;

public class MenuActivity extends BaseActivity {

    @BindView(R.id.button_disposition)
    Button btnDiposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        btnDiposition.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, PrincipalActivity.class)));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_menu;
    }


}
