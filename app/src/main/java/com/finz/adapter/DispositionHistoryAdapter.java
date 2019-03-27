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
import com.finz.rest.history.entity.DispositionHistory;
import com.finz.util.UtilCore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DispositionHistoryAdapter extends RecyclerView.Adapter<DispositionHistoryAdapter.ViewHolder> {

    private List<DispositionHistory> items;
    private Context context;

    public DispositionHistoryAdapter(List<DispositionHistory> items, Context context) {
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
        DispositionHistory item = items.get(position);

        holder.title.setText(context.getString(R.string.disposition_money));
        holder.date.setText(UtilCore.UtilDate.getDateService(item.getCreationDate()));
        holder.status.setText(item.getStatusText());
        holder.label1.setText(context.getString(R.string.credit_card));
        holder.text1.setText(context.getString(R.string.credit_card_hiden,item.getLastFour()));
        holder.label2.setText(context.getString(R.string.account_number));
        holder.text2.setText(item.getNroDestiny());
        holder.label3.setText(context.getString(R.string.amount));
        holder.text3.setText(context.getString(R.string.blank_decimal, item.getAmount()));
        holder.label4.setText(context.getString(R.string.total_amount));
        holder.text4.setText(context.getString(R.string.blank_decimal, item.getAmountTotal()));

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
