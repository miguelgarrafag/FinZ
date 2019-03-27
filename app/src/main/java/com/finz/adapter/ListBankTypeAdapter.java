package com.finz.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finz.R;
import com.finz.rest.utils.entity.BankType;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListBankTypeAdapter extends RecyclerView.Adapter<ListBankTypeAdapter.ViewHolder> {

    private List<BankType> items;
    private OnCallBack listener;

    public ListBankTypeAdapter(List<BankType> items) {
        this.items = items;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank_name, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BankType item = items.get(position);

        holder.bank.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bank)
        TextView bank;

        @OnClick(R.id.bank)
        void onClickBank(){
            listener.onSelectedBank(getAdapterPosition());
        }

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnCallBack {
        void onSelectedBank(int position);
    }

    public void setListener(OnCallBack listener) {
        this.listener = listener;
    }
}
