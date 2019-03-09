package com.finz.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.finz.adapter.FragmentPagerAdapter;
import com.finz.R;
import com.finz.fragment.LoginFragment;
import com.finz.fragment.RegisterFragment;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class LoginRegisterActivity extends BaseActivity implements HasSupportFragmentInjector {

    public static final String ACTIVITY = "activity";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(RegisterFragment.newInstance(), getString(R.string.register));
        adapter.addFragment(LoginFragment.newInstance(), getString(R.string.login));

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login_register;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
