package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.finz.R;
import com.finz.dialogFragment.ChangePasswordDialog;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class ProfileActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profile;
    }
    @OnClick(R.id.txt_change_password)
    public void changePassword(){
        final ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(this);
        changePasswordDialog.show();
        changePasswordDialog.setClickCallback(response -> {
            if (response){
                consumeService();
            } else {
                changePasswordDialog.dismiss();
            }
        });
    }
    public void consumeService(){

    }

}
