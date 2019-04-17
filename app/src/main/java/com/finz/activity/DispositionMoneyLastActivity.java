package com.finz.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.finz.R;
import com.finz.util.UtilCore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.pro100svitlo.creditCardNfcReader.CardNfcAsyncTask;
import com.pro100svitlo.creditCardNfcReader.utils.CardNfcUtils;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cards.pay.paycardsrecognizer.sdk.Card;
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent;
import dagger.android.AndroidInjection;

public class DispositionMoneyLastActivity extends BaseActivity implements Validator.ValidationListener, CardNfcAsyncTask.CardNfcInterface {

    static final int REQUEST_CODE_SCAN_CARD = 15;

    public static final String ARGS_EMAIL = "email";
    public static final String ARGS_TYPE = "type";
    public static final String ARGS_BANK = "bank";
    public static final String ARGS_ACCOUNT = "account";
    public static final String ARGS_AMOUNT = "amount";
    public static final String ARGS_NAME_CARD = "name_card";
    public static final String ARGS_EMAIL_CARD = "email_card";
    public static final String ARGS_PHONE_CARD = "phone_card";
    public static final String ARGS_NUMBER_CARD = "number_card";
    public static final String ARGS_MONTH_CARD = "month_card";
    public static final String ARGS_CSV_CARD = "csv_card";

    @SuppressLint("StaticFieldLeak")
    public static Activity activity;

    @BindView(R.id.name_card)
    @NotEmpty(messageResId = R.string.str_register_validate_card_name)
    TextInputEditText nameCard;

    @BindView(R.id.email)
    @Email(messageResId = R.string.str_register_validate_email_valid)
    @NotEmpty(messageResId = R.string.str_register_validate_email_not_empty)
    TextInputEditText email;

    @BindView(R.id.phone)
    @Length(min = 9, messageResId = R.string.str_validate_phone)
    @NotEmpty(messageResId = R.string.str_register_validate_phone)
    TextInputEditText phone;

    @BindView(R.id.card_number)
    @NotEmpty(messageResId = R.string.str_register_validate_card)
    TextInputEditText cardNumber;

    @BindView(R.id.month)
    @NotEmpty(messageResId = R.string.str_register_validate_month)
    TextInputEditText month;

    @BindView(R.id.csv)
    @NotEmpty(messageResId = R.string.str_register_validate_csv)
    TextInputEditText csv;

    @BindView(R.id.nfc)
    ImageView nfc;

    //NFC Variables
    private CardNfcAsyncTask mCardNfcAsyncTask;
    private CardNfcUtils mCardNfcUtils;
    private NfcAdapter mNfcAdapter;
    private boolean mIntentFromCreate;
    private AlertDialog mTurnNfcDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        activity = this;

        validator.setValidationListener(this);

        nfcInit();
    }

    @OnClick(R.id.camera)
    void OnClickCamera(){
        scanCard();
    }

    @OnClick(R.id.back)
    void OnClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.question)
    void OnClickQuestion(){
        UtilCore.UtilUI.question(this, getString(R.string.email_contact));
    }

    @OnClick(R.id.btn_pay)
    void OnClickPay(){
        validator.validate();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_disposition_money_last;
    }


    @Override
    public void onValidationSucceeded() {
        Intent intent = new Intent(this, DispositionMoneyFinishActivity.class);
        intent.putExtra(ARGS_EMAIL, getIntent().getStringExtra(ARGS_EMAIL));
        intent.putExtra(ARGS_TYPE, getIntent().getStringExtra(ARGS_TYPE));
        intent.putExtra(ARGS_BANK, getIntent().getSerializableExtra(ARGS_BANK));
        intent.putExtra(ARGS_ACCOUNT, getIntent().getStringExtra(ARGS_ACCOUNT));
        intent.putExtra(ARGS_AMOUNT, getIntent().getStringExtra(ARGS_AMOUNT));
        intent.putExtra(ARGS_NAME_CARD, Objects.requireNonNull(nameCard.getText()).toString());
        intent.putExtra(ARGS_EMAIL_CARD, Objects.requireNonNull(email.getText()).toString());
        intent.putExtra(ARGS_PHONE_CARD, Objects.requireNonNull(phone.getText()).toString());
        intent.putExtra(ARGS_NUMBER_CARD, Objects.requireNonNull(cardNumber.getText()).toString());
        intent.putExtra(ARGS_MONTH_CARD, Objects.requireNonNull(month.getText()).toString());
        intent.putExtra(ARGS_CSV_CARD, Objects.requireNonNull(csv.getText()).toString());
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

    //CARDSCANN
    //**********************************************************************************
    private void scanCard() {
        Intent intent = new ScanCardIntent.Builder(this).build();
        startActivityForResult(intent, REQUEST_CODE_SCAN_CARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN_CARD) {
            if (resultCode == Activity.RESULT_OK) {
                Card card = data.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD);

                cardNumber.setText(card.getCardNumber());
                month.setText(card.getExpirationDate());

                String cardData = "Card number: " + card.getCardNumber() + "\n"
                        + "Card holder: " + card.getCardHolderName() + "\n"
                        + "Card expiration date: " + card.getExpirationDate();
                Log.i("CARDSCANN", "Card info: " + cardData);
            } else if (resultCode == Activity.RESULT_CANCELED) Log.i("CARDSCANN", "Scan canceled");
            else Log.i("CARDSCANN", "Scan failed");
        }
    }
    //**********************************************************************************

    //NFC
    //**********************************************************************************
    private void nfcInit() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter != null){
            mCardNfcUtils = new CardNfcUtils(this);
            mIntentFromCreate = true;
            onNewIntent(getIntent());
            nfc.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIntentFromCreate = false;
        if (mNfcAdapter != null && !mNfcAdapter.isEnabled()) showTurnOnNfcDialog();
        else if (mNfcAdapter != null) mCardNfcUtils.enableDispatch();

    }

    private void showTurnOnNfcDialog(){
        if (mTurnNfcDialog == null)
            mTurnNfcDialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.ad_nfcTurnOn_title))
                    .setMessage(getString(R.string.ad_nfcTurnOn_message))
                    .setPositiveButton(getString(R.string.ad_nfcTurnOn_pos), (dialogInterface, i) -> startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS)))
                    .setNegativeButton(getString(R.string.ad_nfcTurnOn_neg), (dialogInterface, i) -> {})
                    .create();
        mTurnNfcDialog.show();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mNfcAdapter != null) mCardNfcUtils.disableDispatch();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mNfcAdapter != null && mNfcAdapter.isEnabled())
            mCardNfcAsyncTask = new CardNfcAsyncTask.Builder(this, intent, mIntentFromCreate).build();
    }

    @Override
    public void startNfcReadCard() {
        showDialog();
    }

    @Override
    public void cardIsReadyToRead() {
        cardNumber.setText(mCardNfcAsyncTask.getCardNumber());
        month.setText(mCardNfcAsyncTask.getCardExpireDate());
    }

    @Override
    public void doNotMoveCardSoFast() {
        showToastLong(R.string.snack_doNotMoveCard);
    }

    @Override
    public void unknownEmvCard() {
        showToastLong(R.string.snack_unknownEmv);
    }

    @Override
    public void cardWithLockedNfc() {
        showToastLong(R.string.snack_lockedNfcCard);
    }

    @Override
    public void finishNfcReadCard() {
        closeDialog();
        mCardNfcAsyncTask = null;
    }
    //NFC
    //**********************************************************************************
}
