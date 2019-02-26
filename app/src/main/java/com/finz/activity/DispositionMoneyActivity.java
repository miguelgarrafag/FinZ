package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.finz.R;
import com.finz.dialogFragment.ExampleDialog;

import butterknife.BindView;
import dagger.android.AndroidInjection;

public class DispositionMoneyActivity extends BaseActivity {

    @BindView(R.id.btn_pay)
    Button btnPay;

    @BindView(R.id.edt_account_number)
    EditText edtAccountNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        btnPay.setOnClickListener(v -> {
            final ExampleDialog exampleDialog = new ExampleDialog(DispositionMoneyActivity.this, edtAccountNumber.getText().toString());
            exampleDialog.show();
            exampleDialog.setClickCallback(response -> {
                if (response) {
                    startActivity(new Intent(DispositionMoneyActivity.this, InformationDispositionActivity.class));
                    finish();
                } else {
                    exampleDialog.dismiss();
                }
            });
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_disposition_money;
    }
}
