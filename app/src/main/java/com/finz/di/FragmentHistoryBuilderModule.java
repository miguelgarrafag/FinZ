package com.finz.di;

import com.finz.fragment.HistoryDispositionFragment;
import com.finz.fragment.HistoryEvaluationFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author SudTechnologies
 */
@Module
abstract class FragmentHistoryBuilderModule {

    @ContributesAndroidInjector
    abstract HistoryDispositionFragment contributeHistoryDispositionFragment();

    @ContributesAndroidInjector
    abstract HistoryEvaluationFragment contributeHistoryEvaluationFragment();

}
