package com.finz.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.finz.R;
import com.finz.adapter.ListBankTypeAdapter;
import com.finz.rest.RestListListener;
import com.finz.rest.utils.RestUtil;
import com.finz.rest.utils.entity.BankType;
import com.finz.util.UtilCore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.AndroidInjection;

public class DispositionMoneyActivity extends BaseActivity implements Validator.ValidationListener{

    private static final String TAG = DispositionMoneyActivity.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    public static Activity activity;

    @Inject
    RestUtil restUtil;

    @BindView(R.id.bank)
    @NotEmpty(messageResId = R.string.str_register_validate_bank)
    TextInputEditText bank;

    @BindView(R.id.count_number)
    @NotEmpty(messageResId = R.string.str_register_validate_count)
    TextInputEditText countNumber;

    @BindView(R.id.cost)
    @NotEmpty(messageResId = R.string.str_register_validate_mount)
    TextInputEditText cost;

    @BindView(R.id.commission)
    TextView commissionV;

    @BindView(R.id.saving)
    Button saving;

    @BindView(R.id.corrent)
    Button corrent;

    private double commission;
    private String bankType = "A";
    private List<BankType> banks = new ArrayList<>();
    private BankType bankS;
    private String commissionMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        activity = this;

        if(prefs.getUser()==null){
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            intent.putExtra(LoginRegisterActivity.ACTIVITY, "DM");
            startActivity(intent);
            finish();
            return;
        }

        loadDefauls();
        showSplashIntro();
        validator.setValidationListener(this);
    }

    private void loadDefauls() {
        commission = prefs.getParam().getDispPerc() / 100;
        commissionMsg = String.valueOf(prefs.getParam().getDispPerc());
        commissionV.setText(Html.fromHtml(getString(R.string.hint_cost, commissionMsg, getString(R.string.zero))));
        loadBanks();
    }

    private void showSplashIntro() {
        Intent intent = new Intent(this, SliderPresentationActivity.class);
        intent.putExtra(SliderPresentationActivity.KEY_IMAGE, R.drawable.ic_disposition_splash);
        intent.putExtra(SliderPresentationActivity.KEY_ICON, R.drawable.img_profile_disposition);
        intent.putExtra(SliderPresentationActivity.KEY_TITLE, R.string.disposition_money);
        intent.putExtra(SliderPresentationActivity.KEY_TEXT, R.string.splash_disposition_text);
        startActivity(intent);
    }

    private void loadBanks() {
        if (!UtilCore.UtilNetwork.isNetworkAvailable(this)) {
            showToastConnection();
            return;
        }
        restUtil.banks(prefs.getToken().getAccessToken(),
                new RestListListener<BankType>() {
                    @Override
                    public void onSuccess(List<BankType> result) {
                        banks.addAll(result);
                    }

                    @Override
                    public void onError(int statusCode, String message) {
                        validateErrorResponse(TAG, statusCode, message,
                                null, null, null,
                                () -> loadBanks(), null);
                    }
                });
    }

    @OnClick(R.id.back)
    void OnClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.bank)
    void OnClickBank(){
        showBank();
    }

    private void showBank() {
        final RecyclerView recyclerView = new RecyclerView(this);

        ListBankTypeAdapter adapter = new ListBankTypeAdapter(banks);
        recyclerView.setLayoutParams(UtilCore.UtilViews.getParamsBank());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AlertDialog alertDialog = UtilCore.UtilViews.showCustomDialogControl(this,
                R.string.hint_bank,
                R.string.str_choice_bank,
                recyclerView);

        adapter.setListener(position -> {
            bankS = banks.get(position);
            bank.setText(banks.get(position).getName());
            alertDialog.dismiss();
        });
    }

    @OnClick(R.id.question)
    void OnClickQuestion(){
        UtilCore.UtilUI.question(this, getString(R.string.email_contact));
    }

    @OnClick(R.id.btn_pay)
    void OnClickPay(){
        validator.validate();
    }

    @OnClick(R.id.saving)
    void OnClickSavinng(){
        savingSelected(true);
    }

    @OnClick(R.id.corrent)
    void OnClickCurrent(){
        savingSelected(false);
    }

    @OnTextChanged(R.id.cost)
    void OnChangeCost(){
        if(!Objects.requireNonNull(cost.getText()).toString().isEmpty())
            commissionV.setText(Html.fromHtml( getString(R.string.hint_cost, commissionMsg,
                    "<b>" + getString(R.string.blank_decimal,(Double.parseDouble(Objects.requireNonNull(cost.getText()).toString()) * commission) + Double.parseDouble(Objects.requireNonNull(cost.getText()).toString())) + "</b>")));
        else
            commissionV.setText(Html.fromHtml(getString(R.string.hint_cost, commissionMsg, getString(R.string.zero))));
    }

    private void savingSelected(boolean val){
        bankType = val ? "A" : "C";
        saving.setBackground(val ? getDrawable(R.drawable.drawable_border_button_blue) : getDrawable(R.drawable.drawable_border_button_gray));
        saving.setTextColor(val ? ContextCompat.getColor(this, R.color.colorWhite) : ContextCompat.getColor(this, R.color.colorGray));
        corrent.setBackground(val ? getDrawable(R.drawable.drawable_border_button_gray) : getDrawable(R.drawable.drawable_border_button_blue));
        corrent.setTextColor(val ? ContextCompat.getColor(this, R.color.colorGray) : ContextCompat.getColor(this, R.color.colorWhite));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_disposition_money;
    }

    @Override
    public void onValidationSucceeded() {
        Intent intent = new Intent(this, DispositionMoneyLastActivity.class);
        intent.putExtra(DispositionMoneyLastActivity.ARGS_TYPE, bankType);
        intent.putExtra(DispositionMoneyLastActivity.ARGS_BANK, bankS);
        intent.putExtra(DispositionMoneyLastActivity.ARGS_ACCOUNT, Objects.requireNonNull(countNumber.getText()).toString());
        intent.putExtra(DispositionMoneyLastActivity.ARGS_AMOUNT, Objects.requireNonNull(cost.getText()).toString());
        intent.putExtra(DispositionMoneyLastActivity.ARGS_AMOUNT, Objects.requireNonNull(cost.getText()).toString());
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            EditText view = (EditText) error.getView();
            String message = error.getCollatedErrorMessage(this);
            view.setError(message);
        }
    }
}
