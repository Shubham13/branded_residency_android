package com.digivalet.brandresidential.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.RowResidentsBinding;
import com.digivalet.brdata.dto.PetsData;
import com.digivalet.utils.anim.list.SpringAdapterAnimationType;
import com.digivalet.utils.anim.list.SpringAdapterAnimator;

import java.util.List;


public class PetListAdapter extends RecyclerView.Adapter<PetListAdapter.ViewHolder> {

    private final Context context;
    private final List<PetsData> list;
    private final SpringAdapterAnimator animator;

    public PetListAdapter(Context context, List<PetsData> list, RecyclerView recyclerView) {
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
        PetsData petsData = list.get(position);
        holder.rowResidentsBinding.rowResidentsItemName.setText(petsData.getName());
        holder.rowResidentsBinding.rowResidentsItemDesignation.setText(petsData.getType());
        holder.rowResidentsBinding.rowResidentsItemImage.setImageResource(R.mipmap.ic_pet_dog);
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
