package com.finz.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.EditText;

import com.finz.R;
import com.finz.rest.RestListener;
import com.finz.rest.token.RestToken;
import com.finz.rest.token.entity.Token;
import com.finz.rest.user.RestUser;
import com.finz.rest.user.entity.User;
import com.finz.util.UtilCore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class RegisterFragment extends BaseFragment implements Validator.ValidationListener{

    private static final String TAG = RegisterFragment.class.getSimpleName();

    @Inject
    RestUser restUser;

    @Inject
    RestToken restToken;

    @BindView(R.id.name)
    @NotEmpty(messageResId = R.string.str_register_validate_name)
    TextInputEditText name;

    @BindView(R.id.last_name)
    @NotEmpty(messageResId = R.string.str_register_validate_lastname)
    TextInputEditText lastName;

    @BindView(R.id.phone)
    @Length(min = 9, messageResId = R.string.str_validate_phone)
    @NotEmpty(messageResId = R.string.str_register_validate_phone)
    TextInputEditText phone;

    @BindView(R.id.email)
    @Email(messageResId = R.string.str_register_validate_email_valid)
    @NotEmpty(messageResId = R.string.str_register_validate_email_not_empty)
    TextInputEditText email;

    @BindView(R.id.dni)
    @NotEmpty(sequence = 1, messageResId = R.string.str_register_validate_dni)
    @Length(sequence = 2, min = 8, messageResId = R.string.str_validate_dni)
    TextInputEditText dni;

    @BindView(R.id.pass)
    @Password(messageResId = R.string.str_register_validate_password)
    @Length(min = 6, messageResId = R.string.str_validate_pass)
    TextInputEditText pass;

    public RegisterFragment(){}

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndroidSupportInjection.inject(this);
        setHasOptionsMenu(true);
        validator.setValidationListener(this);
    }

    @OnClick(R.id.register)
    void onClickRegister(){
        validator.validate();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onValidationSucceeded() {
        RestUser();
    }

    public void RestUser(){
        if (!UtilCore.UtilNetwork.isNetworkAvailable(Objects.requireNonNull(getContext()))) {
            showToastConnection();
            return;
        }
        showDialog();
        restUser.register(Objects.requireNonNull(email.getText()).toString(),
                Objects.requireNonNull(pass.getText()).toString(),
                Objects.requireNonNull(name.getText()).toString(),
                Objects.requireNonNull(lastName.getText()).toString(),
                Objects.requireNonNull(phone.getText()).toString(),
                Objects.requireNonNull(dni.getText()).toString(),
                new RestListener<User>() {
                    @Override
                    public void onSuccess(User user) {
                        prefs.setUser(user);
                        RestToken();
                    }

                    @Override
                    public void onError(int statusCode, String message) {
                        closeDialog();
                        validateErrorResponse(TAG, statusCode, message,
                                null, null, getString(R.string.srt_exist_email),
                                null, null);
                    }
                });
    }

    public void RestToken(){
        if (!UtilCore.UtilNetwork.isNetworkAvailable(Objects.requireNonNull(getContext()))) {
            showToastConnection();
            return;
        }
        restToken.requestAccessToken(Objects.requireNonNull(email.getText()).toString(),
                Objects.requireNonNull(pass.getText()).toString(),
                new RestListener<Token>() {
                    @Override
                    public void onSuccess(Token token) {
                        prefs.setToken(token);
                        startActivity(evaluateIntent());
                        Objects.requireNonNull(getActivity()).finish();
                    }

                    @Override
                    public void onError(int statusCode, String message) {
                        closeDialog();
                        validateErrorResponse(statusCode);
                    }
                });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            EditText view = (EditText) error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            view.setError(message);
        }
    }
}
