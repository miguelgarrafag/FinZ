package com.finz.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.finz.R;

import dagger.android.support.AndroidSupportInjection;

public class RegisterFragment extends BaseFragment {

    public RegisterFragment(){}

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
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
        return R.layout.fragment_register;
    }
}
