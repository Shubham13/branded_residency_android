package com.digivalet.brandresidential.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.RowToolbarFilterItemBinding;
import com.digivalet.brdata.dto.InterestData;

import java.util.List;

public class InterestFilterAdapter extends RecyclerView.Adapter<InterestFilterAdapter.ViewHolder> {

    private final List<InterestData> list;
    private int rowPosition = 0;
    private final Context context;

    public InterestFilterAdapter(Context context, List<InterestData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowToolbarFilterItemBinding rowFilterBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_toolbar_filter_item, parent,
                false);
        return new ViewHolder(rowFilterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (rowPosition == position) {
            Paris.style(holder.rowFilterBinding.rowToolbarFilterItemRoot).apply(R.style.brandGradientPill);
            Paris.style(holder.rowFilterBinding.rowToolbarFilterItemTitle).apply(R.style.bodySmallRegSystemWhite);
        } else {
            Paris.style(holder.rowFilterBinding.rowToolbarFilterItemRoot).apply(R.style.brandAccent10Pill);
            Paris.style(holder.rowFilterBinding.rowToolbarFilterItemTitle).apply(R.style.bodySmallRegSystemBlack);
        }

        holder.rowFilterBinding.rowToolbarFilterItemTitle.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setPosition(int position) {
        this.rowPosition = position;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowToolbarFilterItemBinding rowFilterBinding;

        public ViewHolder(RowToolbarFilterItemBinding itemView) {
            super(itemView.getRoot());
            this.rowFilterBinding = itemView;
        }
    }
}
