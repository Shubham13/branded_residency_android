package com.digivalet.brandresidential.ui.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.RowDateBinding;

import java.util.Date;
import java.util.List;

public class DateSelectorAdapter extends RecyclerView.Adapter<DateSelectorAdapter.ViewHolder> {

    private final List<Date> dates;
    private int rowPosition = 0;
    private final Context context;

    public DateSelectorAdapter(Context context, List<Date> dates) {
        this.context = context;
        this.dates = dates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowDateBinding rowDateBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_date, parent,
                false);
        return new ViewHolder(rowDateBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position != 4) {
            holder.rowDateBinding.row.setVisibility(View.VISIBLE);
            holder.rowDateBinding.rowMoreDates.setVisibility(View.GONE);

            if (rowPosition == position) {
                holder.rowDateBinding.day.setText(DateFormat.format("EEE", dates.get(position)));
                holder.rowDateBinding.date.setText(DateFormat.format("dd", dates.get(position)));
                Paris.style(holder.rowDateBinding.day).apply(R.style.subXxSmallSemiSystemWhiteAlpha1);
                Paris.style(holder.rowDateBinding.date).apply(R.style.bodyLargeMedSystemWhite);
                holder.rowDateBinding.row.setBackgroundResource(R.drawable.brand_gradient_pill);

            } else {
                holder.rowDateBinding.day.setText(DateFormat.format("EEE", dates.get(position)));
                holder.rowDateBinding.date.setText(DateFormat.format("dd", dates.get(position)));
                Paris.style(holder.rowDateBinding.day).apply(R.style.subXxSmallSemiSystemBlackAlpha50);
                Paris.style(holder.rowDateBinding.date).apply(R.style.bodyLargeMedSystemBlack);
                holder.rowDateBinding.row.setBackgroundResource(R.drawable.brand_accent_4_pill);
            }
        } else {
            if (rowPosition != 4) {
                holder.rowDateBinding.row.setVisibility(View.GONE);
                holder.rowDateBinding.rowMoreDates.setVisibility(View.VISIBLE);
                Paris.style(holder.rowDateBinding.labelDate).apply(R.style.subXxSmallSemiSystemBlackAlpha50);
                holder.rowDateBinding.rowMoreDates.setBackgroundResource(R.drawable.brand_accent_4_pill);
            } else {
                holder.rowDateBinding.row.setVisibility(View.GONE);
                holder.rowDateBinding.rowMoreDates.setVisibility(View.VISIBLE);
                Paris.style(holder.rowDateBinding.labelDate).apply(R.style.subXxSmallSemiSystemWhiteAlpha1);
                holder.rowDateBinding.rowMoreDates.setBackgroundResource(R.drawable.brand_gradient_pill);

            }
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void setPosition(int position) {
        this.rowPosition = position;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowDateBinding rowDateBinding;

        public ViewHolder(RowDateBinding itemView) {
            super(itemView.getRoot());
            this.rowDateBinding = itemView;
        }
    }
}
