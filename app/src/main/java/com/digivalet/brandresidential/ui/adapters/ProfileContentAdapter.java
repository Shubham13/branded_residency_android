package com.digivalet.brandresidential.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.RowProfileItemBinding;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brdata.dto.Profile;

import java.util.List;

public class ProfileContentAdapter extends RecyclerView.Adapter<ProfileContentAdapter.ViewHolder> {

    private final Context context;
    private final List<Profile.HeaderTitle> list;
    private final CustomItemClickListener customItemClickListener;

    public ProfileContentAdapter(Context context, List<Profile.HeaderTitle> list, CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.list = list;
        this.customItemClickListener = customItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowProfileItemBinding rowProfileHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_profile_item,
                parent, false);

        return new ViewHolder(rowProfileHeaderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*set content*/
        holder.rowProfileItemBinding.profileHeaderText.setText(list.get(position).getTitle());

        /*set list content*/
        ListAdapter listAdapter = new ListAdapter(context, list.get(position).getSubTitles());
        listAdapter.setOnClickListener(customItemClickListener);
        holder.rowProfileItemBinding.profileItemList.setAdapter(listAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowProfileItemBinding rowProfileItemBinding;

        public ViewHolder(RowProfileItemBinding itemView) {
            super(itemView.getRoot());
            this.rowProfileItemBinding = itemView;
        }
    }


    /*list adapter*/
    private class ListAdapter extends BaseAdapter {
        private final Context context;
        private final List<Profile.HeaderTitle.SubTitle> list;
        private CustomItemClickListener customItemClickListener;

        public ListAdapter(Context context, List<Profile.HeaderTitle.SubTitle> list) {
            this.context = context;
            this.list = list;
        }

        public void setOnClickListener(CustomItemClickListener onClickListener) {
            this.customItemClickListener = onClickListener;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(context).inflate(R.layout.row_profile_list_item, parent, false);

            /*set content*/
            Profile.HeaderTitle.SubTitle item = list.get(position);
            TextView title = convertView.findViewById(R.id.profile_list_item_title);
            TextView rightTitle = convertView.findViewById(R.id.profile_list_item_title_right);
            title.setText(item.getTitle());

            rightTitle.setText(item.getTitle());

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_services))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getCount());
            }

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_dining))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getCount());
            }

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_fitness))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getCount());
            }

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_activity))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getCalCount());
            }

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_heart))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getBpmCount());
            }

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_folio))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getCount());
            }

            /*item click*/
            convertView.setOnClickListener(view -> {
                customItemClickListener.onItemClickListener(position, item.getType());
            });

            return convertView;
        }
    }
}
