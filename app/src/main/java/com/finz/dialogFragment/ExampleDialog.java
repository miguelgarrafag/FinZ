package com.finz.dialogFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.finz.R;

public class ExampleDialog extends AlertDialog implements View.OnClickListener {
    private ClickCallback clickCallback;
    private EditText edtAccountNumber;
    private TextView txtAccept, txtCancel;

    public ExampleDialog(@NonNull Context context, String accountNumber) {
        super(context);
        init(accountNumber);
    }

    private void init(String accountNumber) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_disposition, null);
        edtAccountNumber = (EditText) view.findViewById(R.id.edt_account);
        txtCancel = view.findViewById(R.id.txt_cancel);
        txtAccept = view.findViewById(R.id.txt_accept);
        setAccountNumber(accountNumber);
        setView(view);
        txtCancel.setOnClickListener(this);
        txtAccept.setOnClickListener(this);
    }

    private void setAccountNumber(String accountNumber) {
        edtAccountNumber.setText(accountNumber);
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
