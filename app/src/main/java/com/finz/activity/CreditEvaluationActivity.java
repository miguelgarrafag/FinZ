package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.finz.R;
import com.finz.adapter.ListBankTypeAdapter;
import com.finz.rest.RestListListener;
import com.finz.rest.RestListener;
import com.finz.rest.utils.RestUtil;
import com.finz.rest.utils.entity.BankType;
import com.finz.rest.utils.entity.Evaluation;
import com.finz.util.UtilCore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class CreditEvaluationActivity extends BaseActivity implements Validator.ValidationListener {

    private static final String TAG = CreditEvaluationActivity.class.getSimpleName();

    @Inject
    RestUtil restUtil;

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

    private List<BankType> types = new ArrayList<>();
    private BankType typeS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        if(prefs.getUser()==null){
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            intent.putExtra(LoginRegisterActivity.ACTIVITY, "CE");
            startActivity(intent);
            finish();
            return;
        }

        validator.setValidationListener(this);
        showSplashIntro();
        loadType();
    }

    private void loadType() {
        restUtil.loadType(prefs.getToken().getAccessToken(), new RestListListener<BankType>() {
            @Override
            public void onSuccess(List<BankType> result) {
                types.addAll(result);
            }

            @Override
            public void onError(int statusCode, String message) {
                validateErrorResponse(TAG, statusCode, message,
                        null, null, null,
                        () -> loadType(), null);
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

    @OnClick(R.id.evaluate)
    void OnClickEvaluate() {
        validator.validate();
    }

    @OnClick(R.id.back)
    void OnClickBack() {
        finish();
    }

    @OnClick(R.id.loan_type)
    void OnClickType() {
        showType();
    }

    private void showType() {
        final RecyclerView recyclerView = new RecyclerView(this);

        ListBankTypeAdapter adapter = new ListBankTypeAdapter(types);
        recyclerView.setLayoutParams(UtilCore.UtilViews.getParamsBank());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AlertDialog alertDialog = UtilCore.UtilViews.showCustomDialogControl(this,
                R.string.hint_type,
                R.string.str_choice_type,
                recyclerView);

        adapter.setListener(position -> {
            typeS = types.get(position);
            loan_type.setText(types.get(position).getName());
            alertDialog.dismiss();
        });
    }

    private void RestEvaluation() {
        if (!UtilCore.UtilNetwork.isNetworkAvailable(this)) {
            showToastConnection();
            return;
        }
        showDialog();
        restUtil.sendEvaluation(
                prefs.getToken().getAccessToken(),
                Objects.requireNonNull(name.getText()).toString(),
                Objects.requireNonNull(email.getText()).toString(),
                Objects.requireNonNull(dni.getText()).toString(),
                Objects.requireNonNull(phone.getText()).toString(),
                typeS.getId(),
                Double.parseDouble(Objects.requireNonNull(loan_amount.getText()).toString()),
                new RestListener<Evaluation>() {
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
                        validateErrorResponse(TAG, statusCode, message,
                                null, null, null,
                                () -> RestEvaluation(), null);
                    }
                });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_credit_evaluation;
    }

    @Override
    public void onValidationSucceeded() {
        RestEvaluation();
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
