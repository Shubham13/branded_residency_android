package com.paragon.sensonic.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.RowGuestProfileItemBinding;
import com.paragon.sensonic.utils.CustomItemClickListener;
import com.paragon.brdata.dto.GuestHistorySubTitle;
import com.paragon.brdata.dto.HistoryData;
import com.paragon.utils.anim.list.SpringAdapterAnimationType;
import com.paragon.utils.anim.list.SpringAdapterAnimator;

import java.util.List;

public class GuestHistoryAdapter extends RecyclerView.Adapter<GuestHistoryAdapter.ViewHolder> {

    private final Context context;
    private final List<HistoryData> list;
    private final CustomItemClickListener customItemClickListener;
    private final SpringAdapterAnimator animator;

    public GuestHistoryAdapter(Context context, List<HistoryData> list, RecyclerView recyclerView, CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.customItemClickListener = customItemClickListener;
        this.list = list;
        this.animator = new SpringAdapterAnimator(recyclerView);
        this.animator.setSpringAnimationType(SpringAdapterAnimationType.SLIDE_FROM_BOTTOM);
        this.animator.addConfig(85, 18);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowGuestProfileItemBinding rowGuestProfileHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_guest_profile_item,
                parent, false);
        animator.onSpringItemCreate(rowGuestProfileHeaderBinding.getRoot());
        return new ViewHolder(rowGuestProfileHeaderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        animator.onSpringItemBind(holder.itemView, position);
        //Paris.style(holder.rowGuestProfileHeaderBinding.rowGuestProfileHeader).apply(R.style.subXSmallSemiSystemBlackAlpha70);
        holder.rowGuestProfileHeaderBinding.rowGuestProfileHeader.setText(list.get(position).getTitle());
        ListAdapter guestSubTitleAdapter = new ListAdapter(context, list.get(position).getSubTitles());
        guestSubTitleAdapter.setOnClickListener(customItemClickListener);
        holder.rowGuestProfileHeaderBinding.rowGuestProfileList.setAdapter(guestSubTitleAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowGuestProfileItemBinding rowGuestProfileHeaderBinding;

        public ViewHolder(RowGuestProfileItemBinding itemView) {
            super(itemView.getRoot());
            this.rowGuestProfileHeaderBinding = itemView;
        }
    }

    private class ListAdapter extends BaseAdapter {

        private final Context context;
        private final List<GuestHistorySubTitle> items;
        private CustomItemClickListener customItemClickListener;

        public ListAdapter(Context context, List<GuestHistorySubTitle> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setOnClickListener(CustomItemClickListener onClickListener) {
            this.customItemClickListener = onClickListener;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                GuestHistorySubTitle guestProfileSubTitle = items.get(position);
                convertView = LayoutInflater.from(context).inflate(R.layout.row_guest_profile_list_item, parent, false);
                TextView title = convertView.findViewById(R.id.row_guest_profile_title);
                TextView date = convertView.findViewById(R.id.row_guest_profile_date);
                TextView subTitle = convertView.findViewById(R.id.row_guest_profile_sub_title);
                View divider = convertView.findViewById(R.id.row_guest_profile_divider);
                title.setText(guestProfileSubTitle.getTitle());
                date.setText(guestProfileSubTitle.getDate());
                convertView.setOnClickListener(view -> customItemClickListener.onItemClickListener(position, guestProfileSubTitle));
            }

            return convertView;
        }
    }
}
