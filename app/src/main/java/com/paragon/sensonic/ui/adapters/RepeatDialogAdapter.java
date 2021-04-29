package com.paragon.sensonic.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.RowSpinnerItemBinding;

import java.util.List;

public class RepeatDialogAdapter extends RecyclerView.Adapter<RepeatDialogAdapter.ViewHolder> {

    private int rowPosition = -1;
    private final List<String> list;
    private String selectedValue;

    public RepeatDialogAdapter(List<String> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowSpinnerItemBinding rowSpinnerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_spinner_item, parent,
                false);
        return new ViewHolder(rowSpinnerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.rowSpinnerBinding.rowSpinnerTitle.setText(list.get(position));

        if (position == 5) {
            holder.rowSpinnerBinding.arrow.setVisibility(View.VISIBLE);
        } else {
            holder.rowSpinnerBinding.arrow.setVisibility(View.GONE);
        }

        if (rowPosition == position) {
            if (position != 5)
                holder.rowSpinnerBinding.rowSpinnerCheckImage.setVisibility(View.VISIBLE);

            selectedValue = holder.rowSpinnerBinding.rowSpinnerTitle.getText().toString();
        } else {
            holder.rowSpinnerBinding.rowSpinnerCheckImage.setVisibility(View.INVISIBLE);
        }
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
        RowSpinnerItemBinding rowSpinnerBinding;

        public ViewHolder(RowSpinnerItemBinding rowSpinnerBinding) {
            super(rowSpinnerBinding.getRoot());
            this.rowSpinnerBinding = rowSpinnerBinding;
        }
    }

    public String getSelectedValue() {
        return selectedValue;
    }
}
