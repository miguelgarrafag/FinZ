package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.finz.R;
import com.finz.adapter.HorizontalAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class ChangeMoneyActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.btn_buy)
    Button btnBuy;

    @BindView(R.id.btn_sale)
    Button btnSale;

    private HorizontalAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Integer> listBanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        listBanks = new ArrayList<>();
        recyclerAdapter = new HorizontalAdapter(listBanks);

        listBanks.add(R.drawable.img_banco_nacion);
        listBanks.add(R.drawable.img_banbif);
        listBanks.add(R.drawable.img_bcp);
        listBanks.add(R.drawable.img_interbank);
        listBanks.add(R.drawable.img_pichincha);
        listBanks.add(R.drawable.img_bbva);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void setup() {
        btnBuy.setPadding(100, 0, 100, 0);
        btnSale.setPadding(100, 0, 100, 0);
    }

    @OnClick(R.id.btn_process)
    public void processChange() {
        startActivity(new Intent(this, DepositActivity.class));
        finish();
    }

    @OnClick(R.id.btn_buy)
    public void buy() {
        btnBuy.setBackgroundResource(R.drawable.drawable_border_button_blue);
        btnBuy.setTextColor(getResources().getColor(R.color.colorWhite));
        btnSale.setBackgroundResource(R.drawable.drawable_border_button_gray);
        btnSale.setTextColor(getResources().getColor(R.color.colorGray));
        setup();
    }

    @OnClick(R.id.btn_sale)
    public void sale() {
        btnSale.setBackgroundResource(R.drawable.drawable_border_button_blue);
        btnSale.setTextColor(getResources().getColor(R.color.colorWhite));
        btnBuy.setBackgroundResource(R.drawable.drawable_border_button_gray);
        btnBuy.setTextColor(getResources().getColor(R.color.colorGray));
        setup();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_change_money;
    }


}
