package com.finz.di;

import com.finz.fragment.LoginFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author SudTechnologies
 */
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();
}
