package com.paragon.sensonic.ui.bottomsheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.BottomSheetTimeBinding;
import com.paragon.sensonic.helpers.CustomItemClickListener;
import com.paragon.sensonic.ui.adapters.TimePickAdapter;
import com.paragon.utils.RecyclerItemClickListener;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Arrays;

public class TimeBottomDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetTimeBinding binding;
    private CustomItemClickListener listener;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_time, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void setOnItemClickListener(CustomItemClickListener listener) {
        this.listener = listener;
    }

    private void init() {
        //set labels
        binding.bottomSheetTitle.setText(R.string.label_time);

        /*add adapter*/
        TimePickAdapter adapter = new TimePickAdapter(Arrays.asList(getContext().getResources().getStringArray(R.array.label_time_array)));
        binding.bottomSheetList.setAdapter(adapter);
        binding.bottomSheetList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            adapter.setPosition(position);
            if (position == 1) {
                adapter.showTimePickerDialog();
            }
        }));

        /*done btn click*/
        binding.bottomSheetDoneBtn.setOnClickListener(e -> {
            listener.onItemClickListener(0, adapter.getSelectedValue());
            dismiss();
        });
    }
}
