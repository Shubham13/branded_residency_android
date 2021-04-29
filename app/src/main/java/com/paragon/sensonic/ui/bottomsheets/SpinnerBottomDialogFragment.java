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
import com.paragon.sensonic.databinding.BottomSheetSpinnerBinding;
import com.paragon.sensonic.helpers.CustomItemClickListener;
import com.paragon.sensonic.ui.adapters.SpinnerAdapter;
import com.paragon.utils.RecyclerItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class SpinnerBottomDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetSpinnerBinding binding;
    private CustomItemClickListener customItemClickListener;
    private SpinnerAdapter spinnerAdapter;
    private final List<String> list;
    private final String title;

    public SpinnerBottomDialogFragment(String string, List<String> list) {
        this.title = string;
        this.list = list;
    }

    public void setOnItemClickListener(CustomItemClickListener onItemClickListener) {
        this.customItemClickListener = onItemClickListener;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_spinner,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setSpinnerList();
    }

    private void setSpinnerList() {
        spinnerAdapter = new SpinnerAdapter(list);
        binding.bottomSheetList.setAdapter(spinnerAdapter);
        binding.bottomSheetList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            spinnerAdapter.setPosition(position);
            customItemClickListener.onItemClickListener(position,list.get(position));
        }));
    }

    private void init() {
        binding.bottomSheetTitle.setText(title);
        binding.bottomSheetDoneBtn.setOnClickListener(view -> dismiss());
    }
}
