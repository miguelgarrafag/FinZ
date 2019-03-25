package com.finz.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.EditText;

import com.finz.R;
import com.finz.dialogFragment.TypeDialogFragment;
import com.finz.rest.RestListListener;
import com.finz.rest.RestListener;
import com.finz.rest.evaluation.RestEvaluation;
import com.finz.rest.evaluation.entity.Evaluation;
import com.finz.rest.type.RestType;
import com.finz.rest.type.entity.Type;
import com.finz.util.UtilCore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class CreditEvaluationActivity extends BaseActivity implements Validator.ValidationListener {
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;

    @Inject
    RestType restType;

    @Inject
    RestEvaluation restEvaluation;

    @BindView(R.id.name)
    @NotEmpty(messageResId = R.string.str_register_validate_name)
    TextInputEditText name;

    @BindView(R.id.email)
    @Email(messageResId = R.string.str_register_validate_email_valid)
    @NotEmpty(messageResId = R.string.str_register_validate_email_not_empty)
    TextInputEditText email;

    @BindView(R.id.dni)
    @Length(min = 8, messageResId = R.string.str_validate_dni)
    @NotEmpty(messageResId = R.string.str_register_validate_dni)
    TextInputEditText dni;

    @BindView(R.id.phone)
    @NotEmpty(messageResId = R.string.str_register_validate_phone)
    TextInputEditText phone;

    @BindView(R.id.loan_type)
    @NotEmpty(messageResId = R.string.str_evaluate_validate_loan_type)
    TextInputEditText loan_type;

    @BindView(R.id.loan_amount)
    @NotEmpty(messageResId = R.string.str_evaluate_validate_loan_amount)
    TextInputEditText loan_amount;

    FragmentManager fm;
    TypeDialogFragment dialogFragment;

    private int idType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        activity = this;
        validator.setValidationListener(this);
        showSplashIntro();
        loadType();
    }

    private void loadType() {
        restType.loadType(prefs.getToken().getAccessToken(), new RestListListener<Type>() {
            @Override
            public void onSuccess(List<Type> result) {
                fm = getSupportFragmentManager();
                dialogFragment = TypeDialogFragment.newInstance(result);
            }

            @Override
            public void onError(int statusCode, String message) {
                Log.e("Error", "Error?");
            }
        });
    }

    private void showSplashIntro() {
        Intent intent = new Intent(this, SliderPresentationActivity.class);
        intent.putExtra(SliderPresentationActivity.KEY_IMAGE, R.drawable.ic_evaluation_splash);
        intent.putExtra(SliderPresentationActivity.KEY_ICON, R.drawable.img_credit_evaluation);
        intent.putExtra(SliderPresentationActivity.KEY_TITLE, R.string.credit_evaluation);
        intent.putExtra(SliderPresentationActivity.KEY_TEXT, R.string.splash_credit_text);
        startActivity(intent);
    }

    @OnClick(R.id.question)
    void OnClickQuestion() {
        UtilCore.UtilUI.question(this, getString(R.string.email_contact));
    }

    @OnClick(R.id.btn_evaluate)
    void OnClickEvaluate() {
        validator.validate();
    }

    @OnClick(R.id.back)
    void OnClickBack() {
        finish();
    }

    @OnClick(R.id.loan_type)
    void OnClickType() {
        dialogFragment.setListener(new TypeDialogFragment.callBack() {
            @Override
            public void onClose(Type type) {
                loan_type.setText(type.getName());
                idType = (int) type.getId();
                dialogFragment.dismiss();
            }
        });
        dialogFragment.show(fm, TypeDialogFragment.TAG);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_credit_evaluation;
    }


    @Override
    public void onValidationSucceeded() {
        RestEvaluation();
    }

    private void RestEvaluation() {
        if (!UtilCore.UtilNetwork.isNetworkAvailable(Objects.requireNonNull(activity))) {
            showToastConnection();
            return;
        }
        showDialog();
        restEvaluation.sendEvaluation(prefs.getToken().getAccessToken(), name.getText().toString(),
                email.getText().toString(), dni.getText().toString(), phone.getText().toString(),
                idType, Double.parseDouble(loan_amount.getText().toString()), new RestListener<Evaluation>() {
                    @Override
                    public void onSuccess(Evaluation evaluation) {
                        closeDialog();
                        finish();
                        Intent intent = new Intent(CreditEvaluationActivity.this, ValidProcessActivity.class);
                        intent.putExtra(ValidProcessActivity.KEY_ICON, R.drawable.ic_launch);
                        intent.putExtra(ValidProcessActivity.KEY_TITLE, R.string.str_request_send);
                        intent.putExtra(ValidProcessActivity.KEY_TEXT, R.string.str_disposition_successs);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int statusCode, String message) {
                        closeDialog();
                    }
                });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            EditText view = (EditText) error.getView();
            String message = error.getCollatedErrorMessage(this);
            view.setError(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
