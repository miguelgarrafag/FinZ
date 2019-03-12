package com.finz.dialogFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.finz.RestDinamicConstant;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.firebase.storage.StorageReference;
import com.finz.R;
import com.finz.constant.ConstantsCore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

public class SignatureDialogFragment extends BaseDialogFragment {

    public static final String TAG = SignatureDialogFragment.class.getSimpleName();

    @BindView(R.id.signature)
    SignaturePad signature;

    private signatureCallBack callBack;
    private boolean falue = false;
    private String signatureName;

    public static SignatureDialogFragment newInstance() {
        return new SignatureDialogFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(!falue)
            callBack.onFalue();
    }

    private File saveToInternalStorage(Bitmap bitmapImage){
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        ContextWrapper cw = new ContextWrapper(Objects.requireNonNull(getContext()).getApplicationContext());
        File directory = cw.getDir(ConstantsCore.LocalStorage.SIGNATURE, Context.MODE_PRIVATE);
        signatureName = "SIGNATURE_" + timeStamp + "_" + ".png";
        File mypath=new File(directory,"SIGNATURE_" + timeStamp + "_" + ".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {e.printStackTrace();}
        finally { try { Objects.requireNonNull(fos).close();} catch (IOException e) {e.printStackTrace();} }
        return new File(mypath.getAbsolutePath());
    }

    @OnClick(R.id.signing)
    void onSigningClick(View v){
        ((CircularProgressButton) v).startAnimation();

        Uri file = Uri.fromFile(saveToInternalStorage(signature.getTransparentSignatureBitmap()));
        StorageReference riversRef = storage.getReference().child(RestDinamicConstant.STOREAGE_FOLDER + ConstantsCore.FStorage.SIGNATURE_FOLDER + file.getLastPathSegment());

        riversRef.putFile(file)
                .addOnFailureListener(exception ->
                        showToastLong(getString(R.string.blank_2_string_s, getString(R.string.str_error_uploading_file), exception.getMessage()))
                )
                .addOnSuccessListener(taskSnapshot -> {
                    falue = true;
                    callBack.onSignature(signatureName);
                });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_fragment_signature;
    }

    public interface signatureCallBack{
        void onSignature(String signatureName);
        void onFalue();
    }

    public void setCallBack(signatureCallBack callBack) {
        this.callBack = callBack;
    }
}
