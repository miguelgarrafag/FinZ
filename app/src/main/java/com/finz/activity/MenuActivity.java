package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;

import com.finz.R;
import com.finz.rest.RestListener;
import com.finz.rest.utils.RestUtil;
import com.finz.rest.utils.entity.Param;
import com.finz.util.UtilCore;

import javax.inject.Inject;

import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class MenuActivity extends BaseActivity {

    private static final String TAG = MenuActivity.class.getSimpleName();

    @Inject
    RestUtil restUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        if(!prefs.getSliders()){
            startActivity(new Intent(this, SliderActivity.class));
            finish();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(prefs.getToken()!=null)
            restParam();
    }

    private void restParam() {
        if (!UtilCore.UtilNetwork.isNetworkAvailable(this)) {
            showToastConnection();
            return;
        }
        restUtil.params(prefs.getToken().getAccessToken(), new RestListener<Param>() {
            @Override
            public void onSuccess(Param param) {
                prefs.setParam(param);
            }

            @Override
            public void onError(int statusCode, String message) {
                validateErrorResponse(TAG, statusCode, message,
                        null, null, null,
                        () -> restParam(), null);
            }
        });
    }

    @OnClick(R.id.button_disposition)
    public void disposition() {
        startActivity(new Intent(this, DispositionMoneyActivity.class));
    }

    @OnClick(R.id.btn_change_money)
    public void change() {
        showSplashIntroMoney();
    }

    @OnClick(R.id.history)
    public void history() {
        startActivity(new Intent(this, HistoryActivity.class));
    }

    private void showSplashIntroMoney() {
        Intent intent = new Intent(this, SliderPresentationActivity.class);
        intent.putExtra(SliderPresentationActivity.KEY_IMAGE, R.drawable.ic_dolar_splash);
        intent.putExtra(SliderPresentationActivity.KEY_ICON, R.drawable.img_change_money);
        intent.putExtra(SliderPresentationActivity.KEY_TITLE, R.string.making_impruvements);
        intent.putExtra(SliderPresentationActivity.KEY_TEXT, R.string.splash_disposition_text);
        intent.putExtra(SliderPresentationActivity.KEY_TEXT_BUTTON, R.string.back);
        startActivity(intent);
    }

    @OnClick(R.id.btn_credit_evaluation)
    public void evaluation() {
        startActivity(new Intent(this, CreditEvaluationActivity.class));
    }

    @OnClick(R.id.img_profile)
    public void profile() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_menu;
    }


}
