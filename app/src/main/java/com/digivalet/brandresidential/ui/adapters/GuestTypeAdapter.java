package com.digivalet.brandresidential.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.RowGuestTypeBinding;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brdata.dto.TypeData;

import java.util.List;

public class GuestTypeAdapter extends RecyclerView.Adapter<GuestTypeAdapter.ViewHolder> {

    private final Context context;
    private int rowPosition = 0;
    CustomItemClickListener customItemClickListener;
    private final List<TypeData> list;

    public GuestTypeAdapter(Context context, List<TypeData> list) {
        this.context = context;
        this.list = list;
    }

    public void onItemClickListener(CustomItemClickListener customItemClickListener) {
        this.customItemClickListener = customItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowGuestTypeBinding rowGuestTypeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_guest_type, parent,
                false);
        return new ViewHolder(rowGuestTypeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TypeData typeData = list.get(position);
        holder.rowGuestTypeBinding.labelGuestType.setText(typeData.getTitle());

        if (rowPosition == position) {
            holder.rowGuestTypeBinding.rowGuestType.setBackgroundResource(R.drawable.brand_gradient_radius_10);
            Paris.style(holder.rowGuestTypeBinding.labelGuestType).apply(R.style.bodyBaselineSemiBoldSystemWhiteAlpha1);
            holder.rowGuestTypeBinding.checkImage.setVisibility(View.VISIBLE);
        } else {
            holder.rowGuestTypeBinding.rowGuestType.setBackgroundResource(R.drawable.gray_border_radius_10_stock_2);
            Paris.style(holder.rowGuestTypeBinding.labelGuestType).apply(R.style.bodyBaselineSemiBoldSystemBlackAlpha50);
            holder.rowGuestTypeBinding.checkImage.setVisibility(View.GONE);
        }
        holder.rowGuestTypeBinding.rowGuestType.setOnClickListener(view -> customItemClickListener.onItemClickListener(position));
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
        RowGuestTypeBinding rowGuestTypeBinding;

        public ViewHolder(RowGuestTypeBinding rowGuestTypeBinding) {
            super(rowGuestTypeBinding.getRoot());
            this.rowGuestTypeBinding = rowGuestTypeBinding;
        }
    }
}
