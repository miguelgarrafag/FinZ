package com.finz.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.mobsandgeeks.saripaar.Validator;
import com.finz.R;
import com.finz.aplication.ApplicationPreferences;
import com.finz.constant.ConstantsCore;
import com.finz.listener.SuccessListener;
import com.finz.rest.RestListener;
import com.finz.rest.token.RestToken;
import com.finz.rest.token.entity.Token;
import com.finz.util.UtilCore;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    protected Validator validator;

    @Inject
    ApplicationPreferences prefs;

    @Inject
    RestToken restToken;

    protected ProgressDialog dialog;
    public FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);

        validator = new Validator(this);
        storage = FirebaseStorage.getInstance();
    }

    protected void showDialog() {
        dialog = UtilCore.UtilUI.showProgressDialog(this);
    }

    protected void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    protected void showToastConnection() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_connection_error,
                findViewById(R.id.custom_connection_error));

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    protected void showToastLong(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void showToastLong(@StringRes int message) {
        Toast.makeText(this, getString(message), Toast.LENGTH_LONG).show();
    }

    public void refreshToken(String TAG, SuccessListener listener) {
        restToken.refreshToken(prefs.getToken().getRefreshToken(),
                new RestListener<Token>() {
                    @Override
                    public void onSuccess(Token token) {
                        prefs.setToken(token);
                        listener.onSucces();
                    }

                    @Override
                    public void onError(int statusCode, String message) {
                        if(statusCode==401) {
                            showToastLong(R.string.str_error_sesion);
                            prefs.clearall();
                            startActivity(new Intent(BaseActivity.this, MenuActivity.class));
                            finish();
                        }else
                            showToastLong(getString(R.string.blank_2_string_ss, getString(R.string.str_error_app), TAG));
                    }
                });
    }

    public void validateErrorResponse(String TAG, int statusCode, String restMessage,
                                      String badRequestMessage, String notFoundMessage, String conflictMessage,
                                      SuccessListener unauthorizedListener, SuccessListener badRequestListener) {
        switch (statusCode) {
            case ConstantsCore.HttpStatus.UNAUTHORIZED:
                if (unauthorizedListener != null)
                    refreshToken(TAG, unauthorizedListener);
                if (restMessage != null)
                    validateMessage(restMessage, R.string.str_unknow_rest_error);
                break;
            case ConstantsCore.HttpStatus.BAD_REQUEST:
                validateMessage(badRequestMessage, R.string.str_unknow_validate_mnessage_error);
                if (badRequestListener != null) badRequestListener.onSucces();
                break;
            case ConstantsCore.HttpStatus.NOT_FOUND:
                validateMessage(notFoundMessage, R.string.str_unknow_validate_mnessage_error);
                break;
            case ConstantsCore.HttpStatus.CONFLICT:
                validateMessage(conflictMessage, R.string.str_unknow_validate_mnessage_error);
                break;
            default:
                validateMessage(restMessage, R.string.str_unknow_rest_error);
        }
    }

    public void validateMessage(String message, int errorMessage) {
        if (message != null)
            if (!message.equals("")) {
                showToastLong(message);
                return;
            }

        showToastLong(errorMessage);
    }

    protected abstract int getLayoutResourceId();
}
