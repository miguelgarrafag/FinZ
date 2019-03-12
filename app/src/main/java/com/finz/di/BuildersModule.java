package com.finz.di;

import com.finz.activity.ChangeMoneyActivity;
import com.finz.activity.CreditEvaluationActivity;
import com.finz.activity.DepositActivity;
import com.finz.activity.DispositionMoneyActivity;
import com.finz.activity.DispositionMoneyFinishActivity;
import com.finz.activity.DispositionMoneyLastActivity;
import com.finz.activity.InformationDispositionActivity;
import com.finz.activity.ListAccountActivity;
import com.finz.activity.LoginRegisterActivity;
import com.finz.activity.MenuActivity;
import com.finz.activity.ProfileActivity;
import com.finz.activity.SliderActivity;
import com.finz.activity.ValidProcessActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class BuildersModule {

    //ACTIVITIES
    @ContributesAndroidInjector(modules = {FragmentBuilderModule.class})
    abstract LoginRegisterActivity bindLoginRegisterActivity();

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
    abstract InformationDispositionActivity bindInformationDispositionActivity();

    @ContributesAndroidInjector()
    abstract ValidProcessActivity bindValidProcessActivity();

    @ContributesAndroidInjector()
    abstract ChangeMoneyActivity bindChangeMoneyActivity();

    @ContributesAndroidInjector()
    abstract DepositActivity bindDepositActivity();

    @ContributesAndroidInjector()
    abstract ListAccountActivity bindListAccountActivity();

    @ContributesAndroidInjector()
    abstract CreditEvaluationActivity bindCreditEvaluationActivity();

    @ContributesAndroidInjector()
    abstract ProfileActivity bindProfileActivity();

}
