package com.finz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finz.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListBankAdapter extends RecyclerView.Adapter<ListBankAdapter.ViewHolder> {
    private ArrayList<String> listBanks;
    private ArrayList<String> listAccountBanks;

    public ListBankAdapter(ArrayList<String> listBanks, ArrayList<String> listAccountBanks) {
        this.listBanks = listBanks;
        this.listAccountBanks = listAccountBanks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_bank, parent, false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtBank.setText(listBanks.get(position));
        holder.txtAccount.setText(listAccountBanks.get(position));
    }

    @Override
    public int getItemCount() {
        return listBanks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtBank, txtAccount;

        public ViewHolder(View itemView) {
            super(itemView);
            txtBank = itemView.findViewById(R.id.txt_name);
            txtAccount = itemView.findViewById(R.id.txt_account);
        }
    }
}
