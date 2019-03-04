package com.finz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.finz.R;
import com.finz.activity.DispositionMoneyActivity;

import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class LoginFragment extends BaseFragment {

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
        setup();
    }

    private void setup() {
    }

    @OnClick(R.id.btn_next)
    public void next(View view) {
        startActivity(new Intent(getContext(), DispositionMoneyActivity.class));
        getActivity().finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_login;
    }
}
