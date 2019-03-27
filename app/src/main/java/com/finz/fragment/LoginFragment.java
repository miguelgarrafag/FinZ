package com.finz.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.EditText;

import com.finz.R;
import com.finz.rest.RestEmptyListener;
import com.finz.rest.RestListener;
import com.finz.rest.token.RestToken;
import com.finz.rest.token.entity.Token;
import com.finz.rest.user.RestUser;
import com.finz.rest.user.entity.User;
import com.finz.util.UtilCore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class LoginFragment extends BaseFragment implements Validator.ValidationListener{

    private static final String TAG = LoginFragment.class.getSimpleName();

    @Inject
    RestUser restUser;

    @Inject
    RestToken restToken;

    @BindView(R.id.email)
    @Email(messageResId = R.string.str_login_email_format)
    @NotEmpty(messageResId = R.string.str_login_email_valid)
    TextInputEditText email;

    @BindView(R.id.pass)
    @NotEmpty(messageResId = R.string.str_login_password_valid)
    TextInputEditText pass;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndroidSupportInjection.inject(this);
        setHasOptionsMenu(true);

        validator.setValidationListener(this);
    }

    @OnClick(R.id.login)
    public void onClickLogin() {
        validator.validate();
    }

    @OnClick(R.id.lose)
    public void onClickLose() {
        losePass();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onValidationSucceeded() {
        RestToken();
    }

    public void losePass(){
        if(Objects.requireNonNull(email.getText()).toString().isEmpty())
            email.setError(getString(R.string.str_error_lose_pass));
        else {
            RestLosePass();
        }
    }

    private void RestLosePass() {
        if (!UtilCore.UtilNetwork.isNetworkAvailable(Objects.requireNonNull(getContext()))) {
            showToastConnection();
            return;
        }
        showDialog();
        restUser.recoverPwd(Objects.requireNonNull(email.getText()).toString(),
                new RestEmptyListener() {
                    @Override
                    public void onSuccess() {
                        closeDialog();
                        showToastLong(R.string.str_lose_pass_ok);
                    }

                    @Override
                    public void onError(int statusCode, String message) {
                        closeDialog();
                        validateErrorResponse(TAG, statusCode, message,
                                null, getString(R.string.srt_email_not_valid), null,
                                null, null);
                    }
                });
    }

    public void RestToken(){
        if (!UtilCore.UtilNetwork.isNetworkAvailable(Objects.requireNonNull(getContext()))) {
            showToastConnection();
            return;
        }
        showDialog();
        restToken.requestAccessToken(Objects.requireNonNull(email.getText()).toString(),
                Objects.requireNonNull(pass.getText()).toString(),
                new RestListener<Token>() {
                    @Override
                    public void onSuccess(Token token) {
                        prefs.setToken(token);
                        requestUserInfo(token);
                    }

                    @Override
                    public void onError(int statusCode, String message) {
                        closeDialog();
                        validateErrorResponse(statusCode);
                    }
                });
    }

    private void requestUserInfo(Token token) {
        if (!UtilCore.UtilNetwork.isNetworkAvailable(Objects.requireNonNull(getContext()))) {
            showToastConnection();
            return;
        }
        restUser.userInfo(token.getAccessToken(),
                new RestListener<User>() {
                    @Override
                    public void onSuccess(User user) {
                        prefs.setUser(user);
                        startActivity(evaluateIntent());
                        Objects.requireNonNull(getActivity()).finish();
                    }

                    @Override
                    public void onError(int statusCode, String message) {
                        closeDialog();
                        validateErrorResponse(TAG, statusCode, getString(R.string.str_login_failed),
                                getString(R.string.str_login_failed), null, null,
                                null, null);
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
