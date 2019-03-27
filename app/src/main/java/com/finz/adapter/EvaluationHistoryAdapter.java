package com.finz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.finz.R;
import com.finz.rest.history.entity.EvaluationHistory;
import com.finz.util.UtilCore;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluationHistoryAdapter extends RecyclerView.Adapter<EvaluationHistoryAdapter.ViewHolder> {

    private List<EvaluationHistory> items;
    private Context context;

    public EvaluationHistoryAdapter(List<EvaluationHistory> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EvaluationHistory item = items.get(position);

        holder.title.setText(context.getString(R.string.credit_evaluation));
        holder.date.setText(UtilCore.UtilDate.getDateService(item.getCreationDate()));
        holder.status.setText(item.getStatusText());
        holder.label1.setText(context.getString(R.string.loan_type));
        holder.text1.setText(item.getType());
        holder.label2.setText(context.getString(R.string.loan_amount));
        holder.text2.setText(context.getString(R.string.blank_decimal,item.getAmount()));

        holder.label3.setVisibility(View.GONE);
        holder.text3.setVisibility(View.GONE);
        holder.label4.setVisibility(View.GONE);
        holder.text4.setVisibility(View.GONE);

        switch (item.getStatusText()){
            case "Pendiente":
                holder.statusColor.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.shape_history_process));
                break;
            case "Anulado":
                holder.statusColor.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.shape_history_canceled));
                break;
            case "Aprobado":
                holder.statusColor.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.shape_history_success));
                break;
            case "Error":
                holder.statusColor.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.shape_history_error));
                break;
            default:
                holder.statusColor.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.shape_history_process));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.label1)
        TextView label1;

        @BindView(R.id.label2)
        TextView label2;

        @BindView(R.id.label3)
        TextView label3;

        @BindView(R.id.label4)
        TextView label4;

        @BindView(R.id.text1)
        TextView text1;

        @BindView(R.id.text2)
        TextView text2;

        @BindView(R.id.text3)
        TextView text3;

        @BindView(R.id.text4)
        TextView text4;

        @BindView(R.id.status_color)
        ImageView statusColor;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
