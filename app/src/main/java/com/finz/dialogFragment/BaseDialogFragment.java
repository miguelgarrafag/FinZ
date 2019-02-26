package com.finz.dialogFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.finz.R;

import java.util.Objects;

import butterknife.ButterKnife;

public abstract class BaseDialogFragment extends DialogFragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutResourceId(), container);
        ButterKnife.bind(this, v);
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return v;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        Objects.requireNonNull(getDialog().getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    protected void showToastLong(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    protected abstract int getLayoutResourceId();
}
