package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import com.finz.R;
import com.finz.rest.RestEmptyListener;
import com.finz.rest.user.RestUser;
import com.finz.util.UtilCore;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class ProfileActivity extends BaseActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName();

    @Inject
    RestUser restUser;

    @BindView(R.id.full_name)
    TextView fullName;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.phone)
    TextView phone;

    @BindView(R.id.dni)
    TextView dni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        if(prefs.getUser()==null){
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            intent.putExtra(LoginRegisterActivity.ACTIVITY, "P");
            startActivity(intent);
            finish();
            return;
        }

        fullName.setText(getString(R.string.blank_2_string_s, prefs.getUser().getName(), prefs.getUser().getLastName()));
        email.setText(prefs.getUser().getEmail());
        phone.setText(prefs.getUser().getPhone());
        dni.setText(prefs.getUser().getDni());
    }

    @OnClick(R.id.logout)
    void OnCLickLogout(){
        UtilCore.UtilViews.showCustomDialog(this,
                R.string.warning,
                getString(R.string.str_logout_question),
                () -> {},
                () -> {
            prefs.clearall();
            startActivity(new Intent(this,MenuActivity.class));
            finish();
        });
    }

    @OnClick(R.id.back)
    void OnCLickBack(){
        onBackPressed();
    }

    @OnClick(R.id.change_pass)
    void OnCLickChangePass(){
        showChangePassDialog();
    }

    private void showChangePassDialog() {
        final EditText currentPass = new EditText(this);
        final EditText newPass = new EditText(this);
        final EditText newPassRepeat = new EditText(this);

        currentPass.setLayoutParams(UtilCore.UtilViews.getParams());
        currentPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        currentPass.setTypeface(ResourcesCompat.getFont(this, R.font.product_sans_regular));
        currentPass.setHint(R.string.str_current_pass);
        currentPass.setHintTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        currentPass.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimaryDark));
        currentPass.setSelection(currentPass.getText().length());

        newPass.setLayoutParams(UtilCore.UtilViews.getParams());
        newPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newPass.setTypeface(ResourcesCompat.getFont(this, R.font.product_sans_regular));
        newPass.setHint(R.string.str_new_pass);
        newPass.setHintTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        newPass.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimaryDark));

        newPassRepeat.setLayoutParams(UtilCore.UtilViews.getParams());
        newPassRepeat.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newPassRepeat.setTypeface(ResourcesCompat.getFont(this, R.font.product_sans_regular));
        newPassRepeat.setHint(R.string.str_new_pass_repeat);
        newPassRepeat.setHintTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        newPassRepeat.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimaryDark));

        UtilCore.UtilViews.showCustomDialog(this,
                R.string.dialog_change_pass,
                R.string.dialog_change_pass_msg,
                currentPass, newPass, newPassRepeat,
                () -> {/**/},
                () -> RestChangePass(currentPass.getText().toString(), newPass.getText().toString()));
    }

    private void RestChangePass(String oldPass, String newPass) {
        if (!UtilCore.UtilNetwork.isNetworkAvailable(this)) {
            showToastConnection();
            return;
        }
        showDialog();
        restUser.changePwd(oldPass, newPass, prefs.getToken().getAccessToken(),
                new RestEmptyListener() {
                    @Override
                    public void onSuccess() {
                        closeDialog();
                        showToastLong(R.string.str_pass_changed);
                    }

                    @Override
                    public void onError(int statusCode, String message) {
                        validateErrorResponse(TAG, statusCode, message,
                                getString(R.string.str_pass_current_error), null, null,
                                () -> RestChangePass(oldPass, newPass), null);
                    }
                });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profile;
    }

}
