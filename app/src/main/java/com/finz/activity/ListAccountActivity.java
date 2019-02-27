package com.finz.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.finz.R;
import com.finz.adapter.HorizontalAdapter;
import com.finz.adapter.ListBankAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class ListAccountActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ListBankAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> listBanks;
    ArrayList<String> listAccountBanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        listBanks = new ArrayList<>();
        listAccountBanks = new ArrayList<>();
        recyclerAdapter = new ListBankAdapter(listBanks, listAccountBanks);

        listBanks.add("fgadfadfa");
        listBanks.add("asdfadsf");
        listBanks.add("adsfa dfa ds");
        listBanks.add("asdfadsfa  a");

        listAccountBanks.add("34363141");
        listAccountBanks.add("324 345461 3");
        listAccountBanks.add("6541444134");
        listAccountBanks.add("7254131 413");

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);


    }

    @OnClick(R.id.btn_close)
    public void close() {
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_list_account;
    }


}
