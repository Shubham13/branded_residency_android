package com.paragon.sensonic.ui.adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.RowSpinnerItemBinding;
import com.paragon.sensonic.helpers.CustomItemClickListener;

import java.util.Calendar;
import java.util.List;

public class TimePickAdapter extends RecyclerView.Adapter<TimePickAdapter.ViewHolder> {

    private int rowPosition = -1;
    private final List<String> list;
    private final Calendar calendar;
    private final int hours;
    private final int minute;
    private final String AM_PM;
    private CustomItemClickListener listener;
    private String selectedValue, selectedDate;
    private String precedeMin = "";
    private TextView date;

    public TimePickAdapter(List<String> list) {
        this.list = list;
        //init calendar
        calendar = Calendar.getInstance();
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        /*get am,pm*/
        if (calendar.get(Calendar.AM_PM) == Calendar.PM)
            AM_PM = "pm";
        else
            AM_PM = "am";

        if (minute < 10) {
            precedeMin = "0";
        }
        selectedDate = (hours == 0) ? "12" : hours + "" + ":" + precedeMin + minute + " " + AM_PM;
    }

    public void setItemClickListener(CustomItemClickListener listener) {
        this.listener = listener;
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

        if (position == 1) {
            holder.rowSpinnerBinding.rowTime.setVisibility(View.VISIBLE);
            holder.rowSpinnerBinding.editTime.setText(selectedDate);
            date = holder.rowSpinnerBinding.editTime;
        } else {
            holder.rowSpinnerBinding.rowTime.setVisibility(View.GONE);
        }

        if (rowPosition == position) {
            holder.rowSpinnerBinding.rowSpinnerCheckImage.setVisibility(View.VISIBLE);
            if (position == 1)
                selectedValue = holder.rowSpinnerBinding.editTime.getText().toString();
            else
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

    public void showTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int hrs = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(date.getContext(), R.style.DialogTheme, (v, hourOfDay, minute)
                -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(0, 0, 0, hourOfDay, minute);
            selectedDate = (String) DateFormat.format("h:mm aaa", calendar);
            date.setText(selectedDate);
            selectedValue = date.getText().toString();

        }, hrs, min, false);
        timePickerDialog.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(date.getContext().getColor(R.color.brand_accent));
            timePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(date.getContext().getColor(R.color.brand_accent));
        }
    }

    public String getSelectedValue() {
        return selectedValue;
    }
}
