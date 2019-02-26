package com.finz.di;

import com.finz.activity.DispositionMoneyActivity;
import com.finz.activity.MainActivity;
import com.finz.activity.MenuActivity;
import com.finz.activity.SliderActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class BuildersModule {

    //ACTIVITIES
    @ContributesAndroidInjector()
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {FragmentBuilderModule.class})
    abstract DispositionMoneyActivity bindDispositionMoneyActivity();

    @ContributesAndroidInjector()
    abstract MenuActivity bindMenuActivity();

    @ContributesAndroidInjector()
    abstract SliderActivity bindSliderActivity();

}
