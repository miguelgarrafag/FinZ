package com.finz.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.finz.R;
import com.finz.adapter.ListBankNameAdapter;
import com.finz.rest.RestListListener;
import com.finz.rest.utils.RestUtil;
import com.finz.rest.utils.entity.Bank;
import com.finz.util.UtilCore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.AndroidInjection;

public class DispositionMoneyActivity extends BaseActivity implements Validator.ValidationListener{

    private static final String TAG = DispositionMoneyActivity.class.getSimpleName();
    public static final String KEY_BANK_EMAIL = "bank_email";
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

    @BindView(R.id.commission_cost)
    TextView commissionCost;

    @BindView(R.id.saving)
    Button saving;

    @BindView(R.id.corrent)
    Button corrent;

    private double commission;
    private String email;
    private String bankType = "A";
    private List<Bank> banks = new ArrayList<>();
    private Bank bankS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        activity = this;

        if(prefs.getUser()==null){
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            intent.putExtra(LoginRegisterActivity.ACTIVITY, "E");
            startActivity(intent);
            finish();
            return;
        }

        validator.setValidationListener(this);
        fetchRemoteConfig();
    }

    private void fetchRemoteConfig() {
        firebaseRemoteConfig.fetch(firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled() ? 0 : TimeUnit.HOURS.toSeconds(1))
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        firebaseRemoteConfig.activateFetched();
                        Log.e("REMOTE CONFIG", "SUCCESS");
                    } else {
                        Log.e("REMOTE CONFIG", "ERROR");
                    }
                    getValues();
                });
    }

    private void getValues() {
        commission = prefs.getParam().getDispPerc() / 100;
        String commissionMsg = String.valueOf(prefs.getParam().getDispPerc());
        commissionV.setText(getString(R.string.hint_cost, commissionMsg));
        email = firebaseRemoteConfig.getString(KEY_BANK_EMAIL);
        loadBanks();
    }

    private void loadBanks() {
        if (!UtilCore.UtilNetwork.isNetworkAvailable(this)) {
            showToastConnection();
            return;
        }
        restUtil.banks(prefs.getToken().getAccessToken(),
                new RestListListener<Bank>() {
                    @Override
                    public void onSuccess(List<Bank> result) {
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

        ListBankNameAdapter adapter = new ListBankNameAdapter(banks);
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
        UtilCore.UtilUI.question(this, email);
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
            commissionCost.setText(getString(R.string.blank_decimal,(Double.parseDouble(Objects.requireNonNull(cost.getText()).toString()) * commission) + Double.parseDouble(Objects.requireNonNull(cost.getText()).toString())));
        else
            commissionCost.setText(getString(R.string.zero));
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
        intent.putExtra(DispositionMoneyLastActivity.ARGS_EMAIL, email);
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
