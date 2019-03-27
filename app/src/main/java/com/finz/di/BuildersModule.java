package com.finz.di;

import com.finz.activity.CreditEvaluationActivity;
import com.finz.activity.DispositionMoneyActivity;
import com.finz.activity.DispositionMoneyFinishActivity;
import com.finz.activity.DispositionMoneyLastActivity;
import com.finz.activity.HistoryActivity;
import com.finz.activity.LoginRegisterActivity;
import com.finz.activity.MenuActivity;
import com.finz.activity.ProfileActivity;
import com.finz.activity.SliderActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class BuildersModule {

    //ACTIVITIES
    @ContributesAndroidInjector(modules = {FragmentLoginRegisterBuilderModule.class})
    abstract LoginRegisterActivity bindLoginRegisterActivity();

    @ContributesAndroidInjector(modules = {FragmentHistoryBuilderModule.class})
    abstract HistoryActivity bindHistoryActivity();

    @ContributesAndroidInjector()
    abstract MenuActivity bindMenuActivity();

    @ContributesAndroidInjector()
    abstract SliderActivity bindSliderActivity();

    @ContributesAndroidInjector()
    abstract DispositionMoneyActivity bindDispositionMoneyActivity();

    @ContributesAndroidInjector()
    abstract DispositionMoneyLastActivity bindDispositionMoneyLastActivity();

    @ContributesAndroidInjector()
    abstract DispositionMoneyFinishActivity bindDispositionMoneyFinishActivity();

    @ContributesAndroidInjector()
    abstract CreditEvaluationActivity bindCreditEvaluationActivity();

    @ContributesAndroidInjector()
    abstract ProfileActivity bindProfileActivity();

}
