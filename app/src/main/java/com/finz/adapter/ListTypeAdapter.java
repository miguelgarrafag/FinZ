package com.finz.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finz.R;
import com.finz.rest.type.entity.Type;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListTypeAdapter extends RecyclerView.Adapter<ListTypeAdapter.ViewHolder> {
    private List<Type> listType;
    public static AddCallback addCallback;

    public interface AddCallback {
        void onAddCallback(int position);
    }

    public void setAddCallback(AddCallback addCallback) {
        this.addCallback = addCallback;
    }

    public ListTypeAdapter(List<Type> listType) {
        this.listType = listType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Type item = listType.get(position);
        holder.type.setText(item.getName());
        holder.type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCallback.onAddCallback(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listType.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView type;

        public ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
        }

    }
}
