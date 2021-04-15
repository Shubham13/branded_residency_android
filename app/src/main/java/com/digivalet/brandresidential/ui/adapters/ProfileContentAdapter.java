package com.digivalet.brandresidential.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.RowProfileItemBinding;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brdata.dto.Profile;

import java.util.List;

import static com.digivalet.brandresidential.R.string.label_booking_history;
import static com.digivalet.brandresidential.R.string.label_contact_info;
import static com.digivalet.brandresidential.R.string.label_guest_history;
import static com.digivalet.brandresidential.R.string.label_property_map;
import static com.digivalet.brandresidential.R.string.label_residents;
import static com.digivalet.brandresidential.R.string.label_service_directory;

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
            ImageView profile_list_item_image = convertView.findViewById(R.id.profile_list_item_image);
            title.setText(item.getTitle());

            rightTitle.setText(item.getTitle());

            if(item.getType().equalsIgnoreCase(context.getString(label_residents))){
                profile_list_item_image.setImageResource(R.mipmap.ic_family_home);
            }

            if(item.getType().equalsIgnoreCase(context.getString(R.string.label_interests))){
                profile_list_item_image.setImageResource(R.mipmap.ic_interest);
            }

            if(item.getType().equalsIgnoreCase(context.getString(R.string.label_vehicles))){
                profile_list_item_image.setImageResource(R.mipmap.ic_vehicles);
            }

            if(item.getType().equalsIgnoreCase(context.getString(R.string.label_pets))){
                profile_list_item_image.setImageResource(R.mipmap.ic_pets);
            }

            if(item.getType().equalsIgnoreCase(context.getString(R.string.label_staff))){
                profile_list_item_image.setImageResource(R.mipmap.ic_staff);
            }

            if(item.getType().equalsIgnoreCase(context.getString(R.string.label_guests))){
                profile_list_item_image.setImageResource(R.mipmap.ic_guests);
            }

            if(item.getType().equalsIgnoreCase(context.getString(R.string.label_friends))){
                profile_list_item_image.setImageResource(R.mipmap.ic_friends);
            }

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_services))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getCount());
                profile_list_item_image.setImageResource(R.mipmap.ic_services);
            }

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_dining))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getCount());
                profile_list_item_image.setImageResource(R.mipmap.ic_dining);
            }

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_fitness))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getCount());
                profile_list_item_image.setImageResource(R.mipmap.ic_fitness);
            }

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_activity))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getCalCount());
                profile_list_item_image.setImageResource(R.mipmap.ic_fitness);
            }

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_heart))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getBpmCount());
                profile_list_item_image.setImageResource(R.mipmap.ic_fitness);
            }

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_nutrition))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getBpmCount());
                profile_list_item_image.setImageResource(R.mipmap.ic_fitness);
            }

            if (item.getType().equalsIgnoreCase(context.getString(R.string.label_folio))) {
                rightTitle.setVisibility(View.VISIBLE);
                rightTitle.setText(item.getCount());
                profile_list_item_image.setImageResource(R.mipmap.ic_folio);
            }

            if(item.getTitle().equalsIgnoreCase(context.getString(R.string.label_booking_history))){
                profile_list_item_image.setImageResource(R.mipmap.ic_book_history);
            }

            if(item.getTitle().equalsIgnoreCase(context.getString(R.string.label_guest_history))){
                profile_list_item_image.setImageResource(R.mipmap.ic_guest_history);
            }

            if(item.getTitle().equalsIgnoreCase(context.getString(R.string.label_service_directory))){
                profile_list_item_image.setImageResource(R.mipmap.ic_family_home);
            }

            if(item.getTitle().equalsIgnoreCase(context.getString(R.string.label_property_map))){
                profile_list_item_image.setImageResource(R.mipmap.ic_family_home);
            }

            if(item.getTitle().equalsIgnoreCase(context.getString(R.string.label_contact_info))){
                profile_list_item_image.setImageResource(R.mipmap.ic_family_home);
            }

            /*item click*/
            convertView.setOnClickListener(view -> {
                customItemClickListener.onItemClickListener(position, item.getType());
            });

            return convertView;
        }
    }
}
