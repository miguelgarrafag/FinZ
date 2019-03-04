package com.finz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finz.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder>{
    private ArrayList<Integer> listBanks;

    public HorizontalAdapter(ArrayList<Integer> listBanks){
        this.listBanks = listBanks;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank, parent, false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.imgBank.setImageResource(listBanks.get(position));
    }

    @Override
    public int getItemCount() {
        return listBanks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView imgBank;

        public  ViewHolder(View itemView){
            super(itemView);
            imgBank = itemView.findViewById(R.id.img_bank);
        }
    }
}
