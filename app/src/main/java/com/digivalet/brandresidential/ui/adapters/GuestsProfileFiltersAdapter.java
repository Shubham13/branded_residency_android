package com.digivalet.brandresidential.ui.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.RowToolbarFilterItemBinding;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brdata.dto.HeaderData;

import java.util.List;

public class GuestsProfileFiltersAdapter extends RecyclerView.Adapter<GuestsProfileFiltersAdapter.ViewHolder> {

    private final Context context;
    private final List<HeaderData> filterList;
    private CustomItemClickListener customItemClickListener;

    public GuestsProfileFiltersAdapter(Context context, List<HeaderData> filterList) {
        this.context = context;
        this.filterList = filterList;
    }

    public void setOnClickListener(CustomItemClickListener onClickListener) {
        this.customItemClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowToolbarFilterItemBinding rowFilterBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_toolbar_filter_item, parent,
                false);
        ViewHolder viewHolder = new ViewHolder(rowFilterBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rowFilterBinding.rowToolbarFilterItemTitle.setText(filterList.get(position).getTitle());
        holder.rowFilterBinding.rowToolbarFilterItemIcon.setVisibility(View.VISIBLE);
        Paris.style(holder.rowFilterBinding.rowToolbarFilterItemTitle).apply(filterList.get(position).isSelected() ? R.style.bodySmallRegSystemWhite : R.style.bodySmallRegSystemBlack);
        Paris.style(holder.rowFilterBinding.rowToolbarFilterItemRoot).apply(filterList.get(position).isSelected() ? R.style.brandGradientPill : R.style.brandAccent10Pill);
        ImageViewCompat.setImageTintList(holder.rowFilterBinding.rowToolbarFilterItemIcon, filterList.get(position).isSelected() ? ColorStateList.valueOf(Color.WHITE) : ColorStateList.valueOf(Color.BLACK));

        holder.itemView.getRootView().setOnClickListener(e -> {
            filterList.get(position).setSelected(!filterList.get(position).isSelected());
            Paris.style(holder.rowFilterBinding.rowToolbarFilterItemTitle).apply(filterList.get(position).isSelected() ? R.style.bodySmallRegSystemWhite : R.style.bodySmallRegSystemBlack);
            Paris.style(holder.rowFilterBinding.rowToolbarFilterItemRoot).apply(filterList.get(position).isSelected() ? R.style.brandGradientPill : R.style.brandAccent10Pill);
            ImageViewCompat.setImageTintList(holder.rowFilterBinding.rowToolbarFilterItemIcon, filterList.get(position).isSelected() ? ColorStateList.valueOf(Color.WHITE) : ColorStateList.valueOf(Color.BLACK));
            customItemClickListener.onItemClickListener(position);
        });
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowToolbarFilterItemBinding rowFilterBinding;

        public ViewHolder(RowToolbarFilterItemBinding itemView) {
            super(itemView.getRoot());
            this.rowFilterBinding = itemView;
        }
    }
}
