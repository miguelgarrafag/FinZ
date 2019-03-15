package com.finz.dialogFragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.finz.R;

import butterknife.BindView;
import butterknife.OnClick;

public class TakePicTutoDialogFragment extends BaseDialogFragment {

    public static final String TAG = TakePicTutoDialogFragment.class.getSimpleName();

    @BindView(R.id.gif)
    ImageView gif;

    private callBack listener;

    public static TakePicTutoDialogFragment newInstance() {
        return new TakePicTutoDialogFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listener!=null)
            listener.onClose();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gif.setBackgroundResource(R.drawable.take_photo);
        AnimationDrawable animationDrawable = (AnimationDrawable) gif.getBackground();
        animationDrawable.start();

    }

    @OnClick(R.id.camera)
    void onClickCamera(){
        this.dismiss();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_fragment_take_pic_tuto;
    }

    public  interface callBack{
        void onClose();
    }

    public void setListener(callBack listener) {
        this.listener = listener;
    }
}
