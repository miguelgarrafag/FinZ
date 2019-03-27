package com.finz.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.finz.activity.CreditEvaluationActivity;
import com.finz.activity.DispositionMoneyActivity;
import com.finz.activity.HistoryActivity;
import com.finz.activity.LoginRegisterActivity;
import com.finz.activity.ProfileActivity;
import com.mobsandgeeks.saripaar.Validator;
import com.finz.R;
import com.finz.aplication.ApplicationPreferences;
import com.finz.constant.ConstantsCore;
import com.finz.listener.SuccessListener;
import com.finz.rest.RestListener;
import com.finz.rest.token.RestToken;
import com.finz.rest.token.entity.Token;
import com.finz.util.UtilCore;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author SudTechnologies
 */
public abstract class BaseFragment extends Fragment {

    @Inject ApplicationPreferences prefs;
    @Inject RestToken restToken;

    protected Validator validator;
    protected ProgressDialog dialog;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourceId(), container, false);
        ButterKnife.bind(this, view);
        validator = new Validator(this);

        return view;
    }

    protected void showDialog() {
        dialog = UtilCore.UtilUI.showProgressDialog(getActivity());
    }

    protected void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    protected void showToastLong(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    protected void showToastLong(@StringRes int message) {
        if(getActivity()!=null)
            Toast.makeText(getActivity(), getString(message), Toast.LENGTH_LONG).show();
    }

    protected void showToastConnection() {
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_connection_error,
                getActivity().findViewById(R.id.custom_connection_error));

        Toast toast = new Toast(getActivity().getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void refreshToken(String TAG, SuccessListener listener){
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
                            startActivity(new Intent(getContext(), LoginRegisterActivity.class));
                            Objects.requireNonNull(getActivity()).finish();
                        }else
                            showToastLong(getString(R.string.blank_2_string_ss, getString(R.string.str_error_app), TAG));
                    }
                });
    }

    public void validateErrorResponse(String TAG, int statusCode, String restMessage,
                                      String badRequiestMessage, String notFoundMessage, String conflicMessage,
                                      SuccessListener unauthorizedListener, SuccessListener badRequiestListener){
        switch (statusCode) {
            case ConstantsCore.HttpStatus.UNAUTHORIZED:
                if(unauthorizedListener!=null)refreshToken(TAG, unauthorizedListener);
                break;
            case ConstantsCore.HttpStatus.BAD_REQUEST:
                validateMessage(badRequiestMessage, R.string.str_unknow_validate_mnessage_error);
                if(badRequiestListener!=null) badRequiestListener.onSucces();
                break;
            case ConstantsCore.HttpStatus.NOT_FOUND:
                validateMessage(notFoundMessage, R.string.str_unknow_validate_mnessage_error);
                break;
            case ConstantsCore.HttpStatus.CONFLICT:
                validateMessage(conflicMessage, R.string.str_unknow_validate_mnessage_error);
                break;
            default:
                validateMessage(restMessage, R.string.str_unknow_rest_error);
        }

    }

    public void validateErrorResponse(int statusCode){
        switch (statusCode) {
            case ConstantsCore.HttpStatus.UNAUTHORIZED:
                showToastLong(getString(R.string.str_login_failed));
                break;
            case ConstantsCore.HttpStatus.BAD_REQUEST:
                showToastLong(getString(R.string.str_login_failed_pass));
                break;
        }

    }

    public void validateMessage(String message, int errorMessage){
        if(message!=null)
            if(!message.equals("")) {
                showToastLong(message);
                return;
            }

        showToastLong(errorMessage);
    }

    public Intent evaluateIntent(){
        Intent intent = null;
        switch (Objects.requireNonNull(getActivity()).getIntent().getStringExtra(LoginRegisterActivity.ACTIVITY)){
            case "P":
                intent = new Intent(getActivity(), ProfileActivity.class);
                break;
            case "DM":
                intent = new Intent(getActivity(), DispositionMoneyActivity.class);
                break;
            case "CE":
                intent = new Intent(getActivity(), CreditEvaluationActivity.class);
                break;
            case "H":
                intent = new Intent(getActivity(), HistoryActivity.class);
                break;
        }
        return intent;
    }

    protected abstract int getLayoutResourceId();
}
