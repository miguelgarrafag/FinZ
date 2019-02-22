package com.finz.di;

import com.finz.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class BuildersModule {

    //ACTIVITIES
    @ContributesAndroidInjector(modules = {FragmentBuilderModule.class})
    abstract MainActivity bindMainActivity();

}
