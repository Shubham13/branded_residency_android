package com.paragon.sensonic.ui.views.countrypicker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paragon.sensonic.R;
import com.paragon.sensonic.ui.views.countrypicker.listeners.OnItemClickListener;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {

    // region Variables
    private final OnItemClickListener listener;
    private final List<Country> countries;
    private final Context context;
    // endregion

    //region Constructor
    public CountriesAdapter(Context context, List<Country> countries,
                            OnItemClickListener listener) {
        this.context = context;
        this.countries = countries;
        this.listener = listener;
    }
    // endregion

    // region Adapter Methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_country_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Country country = countries.get(position);
        holder.countryNameText.setText(country.getName());
        holder.countryCode.setText(country.getCode());
        holder.countryFlagTv.setText(getFlag(country.getCode()));
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(country);
            }
        });
    }

    public String getFlag(String code) {
        int flagOffset = 0x1F1E6;
        int asciiOffset = 0x41;

        int firstChar = Character.codePointAt(code, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(code, 1) - asciiOffset + flagOffset;

        String flag = new String(Character.toChars(firstChar))
                + new String(Character.toChars(secondChar));
        return flag;
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }
    // endregion

    // region ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView countryFlagTv;
        private final TextView countryNameText;
        private final TextView countryCode;
        private final LinearLayout rootView;

        ViewHolder(View itemView) {
            super(itemView);
            countryCode = itemView.findViewById(R.id.countryCode);
            countryFlagTv = itemView.findViewById(R.id.country_flag);
            countryNameText = itemView.findViewById(R.id.country_title);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }
    // endregion
}
