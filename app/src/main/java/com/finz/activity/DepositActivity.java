package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.finz.R;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class DepositActivity extends BaseActivity {

    @BindView(R.id.btn_saving_account)
    Button btnSavingAccount;

    @BindView(R.id.btn_current_account)
    Button btnCurrentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.btn_accounts)
    public void seeAccounts() {
        startActivity(new Intent(this, ListAccountActivity.class));
    }

    private void setup() {
        btnSavingAccount.setPadding(80, 0, 80, 0);
        btnCurrentAccount.setPadding(80, 0, 80, 0);
    }

    @OnClick(R.id.btn_saving_account)
    public void savingAccount() {
        btnSavingAccount.setBackgroundResource(R.drawable.drawable_border_button_blue);
        btnSavingAccount.setTextColor(getResources().getColor(R.color.colorWhite));
        btnCurrentAccount.setBackgroundResource(R.drawable.drawable_border_button_gray);
        btnCurrentAccount.setTextColor(getResources().getColor(R.color.colorGray));
        setup();
    }

    @OnClick(R.id.btn_current_account)
    public void currentAccount() {
        btnCurrentAccount.setBackgroundResource(R.drawable.drawable_border_button_blue);
        btnCurrentAccount.setTextColor(getResources().getColor(R.color.colorWhite));
        btnSavingAccount.setBackgroundResource(R.drawable.drawable_border_button_gray);
        btnSavingAccount.setTextColor(getResources().getColor(R.color.colorGray));
        setup();
    }

    @OnClick(R.id.btn_finish)
    public void realizeChangeMoney() {
        startActivity(new Intent(this, ValidProcessActivity.class));
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_deposit;
    }


}
