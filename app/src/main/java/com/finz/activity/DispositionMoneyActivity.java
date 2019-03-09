package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.finz.R;
import com.finz.util.UtilCore;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class DispositionMoneyActivity extends BaseActivity {

    @BindView(R.id.name_card)
    TextInputEditText nameCard;

    @BindView(R.id.email)
    TextInputEditText email;

    @BindView(R.id.phone)
    TextInputEditText phone;

    @BindView(R.id.amount)
    TextInputEditText amount;

    @BindView(R.id.month)
    TextInputEditText month;

    @BindView(R.id.csv)
    TextInputEditText csv;

    @BindView(R.id.bank)
    TextInputEditText bank;

    @BindView(R.id.count_number)
    TextInputEditText countNumber;

    @BindView(R.id.saving)
    Button saving;

    @BindView(R.id.corrent)
    Button corrent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        if(prefs.getUser()==null){
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            intent.putExtra(LoginRegisterActivity.ACTIVITY, "E");
            startActivity(intent);
            finish();
            return;
        }

    }

    @OnClick(R.id.back)
    void OnClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.question)
    void OnClickQuestion(){
        final TextView email = new EditText(this);

        email.setLayoutParams(UtilCore.UtilViews.getParamsEmail());
        email.setPadding(0,0,0,0);
        email.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        email.setTypeface(ResourcesCompat.getFont(this, R.font.product_sans_regular));
        email.setGravity(Gravity.CENTER);
        email.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_button_lose_pass));
        email.setText(Html.fromHtml(getResources().getString(R.string.dialog_help_email)));

        UtilCore.UtilViews.showCustomDialog(this,
                R.string.dialog_help,
                R.string.dialog_help_msg,
                email);

        email.setOnClickListener(v -> {

        });
    }

    @OnClick(R.id.btn_pay)
    void OnClickPay(){

    }

    @OnClick(R.id.saving)
    void OnClickSavinng(){
        savingSelected(true);
    }

    @OnClick(R.id.corrent)
    void OnClickCurrent(){
        savingSelected(false);
    }

    private void savingSelected(boolean val){
        saving.setBackground(val ? getDrawable(R.drawable.drawable_border_button_blue) : getDrawable(R.drawable.drawable_border_button_gray));
        saving.setTextColor(val ? ContextCompat.getColor(this, R.color.colorWhite) : ContextCompat.getColor(this, R.color.colorGray));
        corrent.setBackground(val ? getDrawable(R.drawable.drawable_border_button_gray) : getDrawable(R.drawable.drawable_border_button_blue));
        corrent.setTextColor(val ? ContextCompat.getColor(this, R.color.colorGray) : ContextCompat.getColor(this, R.color.colorWhite));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_disposition_money;
    }
}
