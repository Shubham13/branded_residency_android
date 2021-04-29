package com.paragon.sensonic.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.RowResidentsBinding;
import com.paragon.brdata.dto.StaffData;
import com.paragon.utils.anim.list.SpringAdapterAnimationType;
import com.paragon.utils.anim.list.SpringAdapterAnimator;

import java.util.List;


public class StaffListAdapter extends RecyclerView.Adapter<StaffListAdapter.ViewHolder> {

    private final Context context;
    private final List<StaffData> list;
    private final SpringAdapterAnimator animator;

    public StaffListAdapter(Context context, List<StaffData> list, RecyclerView recyclerView) {
        this.context = context;
        this.list = list;
        this.animator = new SpringAdapterAnimator(recyclerView);
        this.animator.setSpringAnimationType(SpringAdapterAnimationType.SLIDE_FROM_BOTTOM);
        this.animator.addConfig(85, 18);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowResidentsBinding rowResidentsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_residents, parent, false);
        animator.onSpringItemCreate(rowResidentsBinding.getRoot());
        return new ViewHolder(rowResidentsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        animator.onSpringItemBind(holder.itemView, position);
        StaffData staffData = list.get(position);
        holder.rowResidentsBinding.rowResidentsItemName.setText(staffData.getName());
        holder.rowResidentsBinding.rowResidentsItemDesignation.setText(staffData.getType());
        holder.rowResidentsBinding.rowResidentsItemImage.setImageResource(R.mipmap.ic_staff);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowResidentsBinding rowResidentsBinding;

        public ViewHolder(RowResidentsBinding itemView) {
            super(itemView.getRoot());
            this.rowResidentsBinding = itemView;
        }
    }
}
