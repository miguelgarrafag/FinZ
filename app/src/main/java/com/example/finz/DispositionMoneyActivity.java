package com.example.finz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DispositionMoneyActivity extends AppCompatActivity {
    private Button btnPay;
    private EditText edtAccountNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disposition_money);

        btnPay = findViewById(R.id.btn_pay);
        edtAccountNumber = findViewById(R.id.edt_account_number);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ExampleDialog exampleDialog = new ExampleDialog(DispositionMoneyActivity.this, edtAccountNumber.getText().toString());
                exampleDialog.show();
                exampleDialog.setClickCallback(new ExampleDialog.ClickCallback() {
                    @Override
                    public void onSuccess(boolean response) {
                        if (response) {
                            startActivity(new Intent(DispositionMoneyActivity.this, InformationDispositionActivity.class));
                            finish();
                        } else {
                            exampleDialog.dismiss();
                        }
                    }
                });
            }
        });
    }
}
