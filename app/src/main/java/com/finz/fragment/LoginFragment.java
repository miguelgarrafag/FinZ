package com.finz.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.finz.R;

import dagger.android.support.AndroidSupportInjection;

public class LoginFragment extends BaseFragment {

    public LoginFragment(){}

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

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_login;
    }
}
