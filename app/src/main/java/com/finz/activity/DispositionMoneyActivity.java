package com.finz.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.finz.adapter.PagerAdapter;
import com.finz.R;
import com.finz.fragment.LoginFragment;
import com.finz.fragment.RegisterFragment;

import butterknife.BindView;
import dagger.android.AndroidInjection;

public class DispositionMoneyActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new RegisterFragment(), "Register");
        adapter.AddFragment(new LoginFragment(), "Login");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_disposition;
    }

}
