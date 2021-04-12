package com.digivalet.brandresidential.ui.bottomsheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.BottomSheetCalanderBinding;
import com.digivalet.brandresidential.helpers.AppConstant;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brandresidential.ui.views.calender.DayContainerModel;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CalenderBottomDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetCalanderBinding binding;
    private CustomItemClickListener customItemClickListener;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_calander, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void setOnCalendarClickListener(CustomItemClickListener onCalendarClickListener) {
        this.customItemClickListener = onCalendarClickListener;
    }

    private void init() {
        binding.bottomSheetDoneBtn.setOnClickListener(view -> {
            DayContainerModel dayContainerModel = binding.bottomSheetCalender.getSelectedDate();
            String date = dayContainerModel.getWeek() + AppConstant.COMMA + dayContainerModel.getMonth() + AppConstant.SPACE + dayContainerModel.getDay() + AppConstant.COMMA_SPACE + dayContainerModel.getYear();
            customItemClickListener.onItemClickListener(dayContainerModel.getIndex(), date);
            dismiss();
        });
    }
}
