package com.finz.di;

import com.finz.fragment.LoginFragment;
import com.finz.fragment.RegisterFragment;
import com.finz.fragment.SliderFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author SudTechnologies
 */
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector
    abstract RegisterFragment contributeRegisterFragment();

    @ContributesAndroidInjector
    abstract SliderFragment contributeSliderFragment();
}
