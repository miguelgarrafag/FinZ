package com.finz.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.culqilib.Token;
import com.culqilib.TokenCallback;
import com.finz.R;
import com.finz.RestDinamicConstant;
import com.finz.constant.ConstantsCore;
import com.finz.dialogFragment.SignatureDialogFragment;
import com.finz.rest.RestEmptyListener;
import com.finz.rest.utils.RestUtil;
import com.finz.rest.utils.entity.Bank;
import com.finz.util.UtilCore;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class DispositionMoneyFinishActivity extends BaseActivity{

    private static final String TAG = DispositionMoneyFinishActivity.class.getSimpleName();
    private static final int REQUEST_IMAGE_CAPTURE = 1991;

    @Inject
    RestUtil restUtil;

    @BindView(R.id.check_photo)
    ImageView checkPhoto;

    @BindView(R.id.check_signature)
    ImageView checkSignature;

    @BindView(R.id.s3)
    Button s3;

    @BindView(R.id.s1)
    Button s1;

    @BindView(R.id.s2)
    Button s2;

    private String emailV;
    private String mCurrentPhotoPath;
    private String tokenId;

    private boolean photo = false;
    private boolean signature = false;

    private String signatureName;
    private String photoName;
    private String type;
    private Bank bank;
    private String account;
    private double amount;
    private String nameCard;
    private String emailCard;
    private String phoneCard;
    private String numberCard;
    private String monthCard;
    private String csvCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        args();
    }

    private void args() {
        emailV = getIntent().getStringExtra(DispositionMoneyLastActivity.ARGS_EMAIL);

        type = getIntent().getStringExtra(DispositionMoneyLastActivity.ARGS_TYPE);
        bank = (Bank) getIntent().getSerializableExtra(DispositionMoneyLastActivity.ARGS_BANK);
        account = getIntent().getStringExtra(DispositionMoneyLastActivity.ARGS_ACCOUNT);
        amount = Double.parseDouble(getIntent().getStringExtra(DispositionMoneyLastActivity.ARGS_AMOUNT));
        nameCard = getIntent().getStringExtra(DispositionMoneyLastActivity.ARGS_NAME_CARD);
        emailCard = getIntent().getStringExtra(DispositionMoneyLastActivity.ARGS_EMAIL_CARD);
        phoneCard = getIntent().getStringExtra(DispositionMoneyLastActivity.ARGS_PHONE_CARD);
        numberCard = getIntent().getStringExtra(DispositionMoneyLastActivity.ARGS_NUMBER_CARD);
        monthCard = getIntent().getStringExtra(DispositionMoneyLastActivity.ARGS_MONTH_CARD);
        csvCard = getIntent().getStringExtra(DispositionMoneyLastActivity.ARGS_CSV_CARD);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            try {photoFile = UtilCore.UtilDraw.createImageFile(this);}
            catch (IOException ex) {Log.e("ERROR PHOTO", ex.getMessage());}

            if (photoFile != null) {
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(this, "com.finz.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            uploadDNI();
            photo = true;
            checkPhoto.setVisibility(View.VISIBLE);
            s1.setEnabled(false);
            evaluateFinish();
        }
    }

    private void uploadDNI(){
        Uri file = Uri.fromFile(saveToInternalStorage(UtilCore.UtilDraw.getResizedBitmap(UtilCore.UtilDraw.rotateBitmapOrientation(mCurrentPhotoPath), 500)));
        StorageReference riversRef = storage.getReference().child(RestDinamicConstant.STOREAGE_FOLDER + ConstantsCore.FStorage.DNI_FOLDER + file.getLastPathSegment());

        riversRef.putFile(file)
                .addOnFailureListener(exception -> showToastLong(getString(R.string.blank_2_string_s, getString(R.string.str_error_uploading_file), exception.getMessage())))
                .addOnSuccessListener(taskSnapshot -> {});
    }

    private File saveToInternalStorage(Bitmap bitmapImage){
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        ContextWrapper cw = new ContextWrapper(this);
        File directory = cw.getDir(ConstantsCore.LocalStorage.DNI, Context.MODE_PRIVATE);
        photoName = "DNI_" + timeStamp + "_" + ".png";
        File mypath=new File(directory,photoName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {e.printStackTrace();}
        finally {try {Objects.requireNonNull(fos).close();} catch (IOException e) {e.printStackTrace();}}
        return new File(mypath.getAbsolutePath());
    }

    private void evaluateFinish() {
        s3.setBackground(photo && signature ? getDrawable(R.drawable.selector_button) : getDrawable(R.drawable.drawable_border_button_gray));
        s3.setTextColor(photo && signature ? ContextCompat.getColor(this, R.color.colorWhite) : ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @OnClick(R.id.back)
    void OnClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.question)
    void OnClickQuestion(){
        UtilCore.UtilUI.question(this, emailV);
    }

    @OnClick(R.id.s1)
    void OnClickS1(){
        dispatchTakePictureIntent();
    }

    @OnClick(R.id.s2)
    void OnClickS2(){
        signaturing();
    }

    private void signaturing() {
        FragmentManager fm = getSupportFragmentManager();
        SignatureDialogFragment dialogFragment = SignatureDialogFragment.newInstance();
        dialogFragment.setCallBack(new SignatureDialogFragment.signatureCallBack() {
            @Override public void onSignature(String signatureName) {
                DispositionMoneyFinishActivity.this.signatureName = signatureName;
                signature = true;
                checkSignature.setVisibility(View.VISIBLE);
                s2.setEnabled(false);
                evaluateFinish();
                dialogFragment.dismiss();
            }
            @Override public void onFalue() {}
        });
        dialogFragment.show(fm, SignatureDialogFragment.TAG);
    }

    @OnClick(R.id.s3)
    void OnClickS3(){
        culqiToken();
    }

    private void restFinish() {
        if (!UtilCore.UtilNetwork.isNetworkAvailable(this)) {
            showToastConnection();
            return;
        }
        showDialog();
        restUtil.disposition(prefs.getToken().getAccessToken(),
                tokenId,
                nameCard,
                emailCard,
                phoneCard,
                type,
                account,
                bank.getId(),
                signatureName,
                photoName,
                amount,
                new RestEmptyListener() {
                    @Override
                    public void onSuccess() {
                        DispositionMoneyActivity.activity.finish();
                        DispositionMoneyLastActivity.activity.finish();
                        finish();
                    }

                    @Override
                    public void onError(int statusCode, String message) {
                        validateErrorResponse(TAG, statusCode, message,
                                null, null, null,
                                () -> restFinish(), null);
                    }
                });
    }

    private void culqiToken(){

        showDialog();
        com.culqilib.Card card = new com.culqilib.Card(
                numberCard,
                csvCard,
                Integer.parseInt(monthCard.substring(0,2)),
                Integer.parseInt(monthCard.substring(3,7)),
                emailCard);
        Token token = new Token(/*"pk_live_cLjnFvPmFu2GH2Ch"*/ "sk_test_c0HkDHxZt3jN9OyS");

        token.createToken(getApplicationContext(), card, new TokenCallback() {
            @Override
            public void onSuccess(JSONObject token) {
                try {
                    tokenId = token.get("id").toString();
                    restFinish();
                } catch (Exception ex){
                    showToastLong(ex.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                closeDialog();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8));
                } catch (JSONException e) {
                    showToastLong(e.getMessage());
                }
                try {
                    showToastLong(Objects.requireNonNull(jsonObject).get("user_message").toString());
                } catch (JSONException e) {
                    showToastLong(e.getMessage());
                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_disposition_money_finish;
    }
}
