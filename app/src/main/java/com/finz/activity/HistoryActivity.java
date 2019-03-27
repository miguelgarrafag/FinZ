package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.finz.R;
import com.finz.fragment.HistoryDispositionFragment;
import com.finz.fragment.HistoryEvaluationFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class HistoryActivity extends BaseActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.disposition)
    ImageView disposition;

    @BindView(R.id.evaluation)
    ImageView evaluation;

    private HistoryEvaluationFragment evaluationFragment;
    private HistoryDispositionFragment dispositionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        if(prefs.getUser()==null){
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            intent.putExtra(LoginRegisterActivity.ACTIVITY, "H");
            startActivity(intent);
            finish();
            return;
        }

        evaluationFragment = HistoryEvaluationFragment.newInstance();
        dispositionFragment = HistoryDispositionFragment.newInstance();

        title.setText(getString(R.string.disposition_money));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, dispositionFragment)
                .add(R.id.container, evaluationFragment)
                .hide(evaluationFragment)
                .commit();

    }

    @OnClick(R.id.back)
    void OnClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.evaluation)
    void onEvaluationClick(){
        menuButtons("e");
        title.setText(getString(R.string.credit_evaluation));
        getSupportFragmentManager()
                .beginTransaction()
                .hide(dispositionFragment)
                .show(evaluationFragment)
                .commit();
    }

    @OnClick(R.id.disposition)
    void onDispositionClick(){
        menuButtons("d");
        title.setText(getString(R.string.disposition_money));
        getSupportFragmentManager()
                .beginTransaction()
                .hide(evaluationFragment)
                .show(dispositionFragment)
                .commit();
    }

    private void menuButtons(String val){
        evaluation.setImageDrawable(val.equals("e")?ContextCompat.getDrawable(this,R.drawable.img_credit_evaluation_pressed):ContextCompat.getDrawable(this,R.drawable.selector_history_evaluation));
        disposition.setImageDrawable(val.equals("e")?ContextCompat.getDrawable(this,R.drawable.selector_history_disposition):ContextCompat.getDrawable(this,R.drawable.img_profile_disposition_pressed));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_history;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
