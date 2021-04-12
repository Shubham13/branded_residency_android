package com.digivalet.brandresidential.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.RowInterestsBinding;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brdata.dto.InterestData;
import com.digivalet.brdata.dto.InterestSubTitle;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {


    private final LinkedHashMap<Object, Boolean> interestStatus;
    private CustomItemClickListener onItemClickListener;
    private final ArrayList<Boolean> isAllSelectedList;
    private List<InterestData> interestData;
    private int rowPosition = -1;

    public InterestAdapter(Context context) {
        this.interestStatus = new LinkedHashMap<>();
        this.isAllSelectedList = new ArrayList<>();
        isAllSelectedList.add(false);
        isAllSelectedList.add(false);
        isAllSelectedList.add(false);
        isAllSelectedList.add(false);
        isAllSelectedList.add(false);
    }

    public void setOnItemClickListener(CustomItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<InterestData> data) {
        this.interestData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowInterestsBinding rowInterestBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_interests,
                parent, false);
        return new ViewHolder(rowInterestBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        InterestData interestModel = interestData.get(position);
        holder.rowInterestBinding.rowInterestHeader.setText(interestModel.getTitle());
        holder.rowInterestBinding.rowInterestChipGroup.removeAllViews();

        for (int i = 0; i < interestModel.getSubTitles().size(); i++) {
            InterestSubTitle interestSubTitle = interestModel.getSubTitles().get(i);
            Chip chip = new Chip(holder.rowInterestBinding.rowInterestChipGroup.getContext());
            chip.setText(interestSubTitle.getTitle());
            chip.setTag(i);
            chip.setClickable(true);
            chip.setFocusable(true);
            chip.setRippleColor(null);
            chip.setChipCornerRadius(10);
            interestStatus.put(chip.getTag() + "_" + position, false);

            if (rowPosition == position && !isAllSelectedList.get(position)) {
                interestStatus.put(chip.getTag() + "_" + position, true);
                chip.setTextColor(Color.WHITE);
                chip.setChipBackgroundColorResource(R.color.brand_gradient_dark);
                if (i == interestModel.getSubTitles().size() - 1) {
                    isAllSelectedList.set(position, true);
                }
            } else {
                if (i == interestModel.getSubTitles().size() - 1) {
                    isAllSelectedList.set(position, false);
                }
                interestStatus.put(chip.getTag() + "_" + position, false);
                chip.setTextColor(Color.BLACK);
                chip.setChipBackgroundColorResource(R.color.brand_accent_10);
            }

            chip.setOnClickListener(view -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (interestStatus.get(chip.getTag() + "_" + position)) {
                        interestStatus.put(chip.getTag() + "_" + position, false);
                        chip.setTextColor(Color.BLACK);
                        chip.setChipBackgroundColorResource(R.color.brand_accent_10);
                    } else {
                        interestStatus.put(chip.getTag() + "_" + position, true);
                        chip.setTextColor(Color.WHITE);
                        chip.setChipBackgroundColorResource(R.color.brand_gradient_dark);
                    }
                }
            });
            holder.rowInterestBinding.rowInterestChipGroup.addView(chip);
        }

        holder.rowInterestBinding.labelAll.setOnClickListener(view -> {
            onItemClickListener.onItemClickListener(position, view.getId());
        });
    }

    @Override
    public int getItemCount() {
        return interestData.size();
    }

    public void setAllSelected(int position) {
        rowPosition = position;
        notifyItemChanged(rowPosition);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowInterestsBinding rowInterestBinding;

        public ViewHolder(RowInterestsBinding itemView) {
            super(itemView.getRoot());
            this.rowInterestBinding = itemView;
        }
    }
}
