package com.finz.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.widget.EditText;

import com.finz.R;
import com.finz.util.UtilCore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class DispositionMoneyLastActivity extends BaseActivity implements Validator.ValidationListener{

    public static final String ARGS_EMAIL = "email";
    public static final String ARGS_TYPE = "type";
    public static final String ARGS_BANK = "bank";
    public static final String ARGS_ACCOUNT = "account";
    public static final String ARGS_AMOUNT = "amount";
    public static final String ARGS_NAME_CARD = "name_card";
    public static final String ARGS_EMAIL_CARD = "email_card";
    public static final String ARGS_PHONE_CARD = "phone_card";
    public static final String ARGS_NUMBER_CARD = "number_card";
    public static final String ARGS_MONTH_CARD = "month_card";
    public static final String ARGS_CSV_CARD = "csv_card";

    @SuppressLint("StaticFieldLeak")
    public static Activity activity;

    @BindView(R.id.name_card)
    @NotEmpty(messageResId = R.string.str_register_validate_card_name)
    TextInputEditText nameCard;

    @BindView(R.id.email)
    @Email(messageResId = R.string.str_register_validate_email_valid)
    @NotEmpty(messageResId = R.string.str_register_validate_email_not_empty)
    TextInputEditText email;

    @BindView(R.id.phone)
    @Length(min = 9, messageResId = R.string.str_validate_phone)
    @NotEmpty(messageResId = R.string.str_register_validate_phone)
    TextInputEditText phone;

    @BindView(R.id.card_number)
    @NotEmpty(messageResId = R.string.str_register_validate_card)
    TextInputEditText cardNumber;

    @BindView(R.id.month)
    @NotEmpty(messageResId = R.string.str_register_validate_month)
    TextInputEditText month;

    @BindView(R.id.csv)
    @NotEmpty(messageResId = R.string.str_register_validate_csv)
    TextInputEditText csv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        activity = this;

        validator.setValidationListener(this);
    }

    @OnClick(R.id.back)
    void OnClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.question)
    void OnClickQuestion(){
        UtilCore.UtilUI.question(this, getString(R.string.email_contact));
    }

    @OnClick(R.id.btn_pay)
    void OnClickPay(){
        validator.validate();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_disposition_money_last;
    }

    @Override
    public void onValidationSucceeded() {
        Intent intent = new Intent(this, DispositionMoneyFinishActivity.class);
        intent.putExtra(ARGS_EMAIL, getIntent().getStringExtra(ARGS_EMAIL));
        intent.putExtra(ARGS_TYPE, getIntent().getStringExtra(ARGS_TYPE));
        intent.putExtra(ARGS_BANK, getIntent().getSerializableExtra(ARGS_BANK));
        intent.putExtra(ARGS_ACCOUNT, getIntent().getStringExtra(ARGS_ACCOUNT));
        intent.putExtra(ARGS_AMOUNT, getIntent().getStringExtra(ARGS_AMOUNT));
        intent.putExtra(ARGS_NAME_CARD, Objects.requireNonNull(nameCard.getText()).toString());
        intent.putExtra(ARGS_EMAIL_CARD, Objects.requireNonNull(email.getText()).toString());
        intent.putExtra(ARGS_PHONE_CARD, Objects.requireNonNull(phone.getText()).toString());
        intent.putExtra(ARGS_NUMBER_CARD, Objects.requireNonNull(cardNumber.getText()).toString());
        intent.putExtra(ARGS_MONTH_CARD, Objects.requireNonNull(month.getText()).toString());
        intent.putExtra(ARGS_CSV_CARD, Objects.requireNonNull(csv.getText()).toString());
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            EditText view = (EditText) error.getView();
            String message = error.getCollatedErrorMessage(this);
            view.setError(message);
        }
    }
}
