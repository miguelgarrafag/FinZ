package com.finz.dialogFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.finz.R;

public class ChangePasswordDialog extends AlertDialog implements View.OnClickListener {
    private ClickCallback clickCallback;
    private TextView txtAccept, txtCancel;

    public ChangePasswordDialog(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        txtCancel = view.findViewById(R.id.txt_cancel);
        txtAccept = view.findViewById(R.id.txt_accept);
        setView(view);
        txtCancel.setOnClickListener(this);
        txtAccept.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_accept:
                clickCallback.onSuccess(true);
                break;
            case R.id.txt_cancel:
                clickCallback.onSuccess(false);
                break;
            default:
                break;
        }
    }

    public interface ClickCallback {
        void onSuccess(boolean response);
    }

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

}
