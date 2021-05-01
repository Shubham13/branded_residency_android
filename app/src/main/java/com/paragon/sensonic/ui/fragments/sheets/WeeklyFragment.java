package com.paragon.sensonic.ui.fragments.sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.FragmentWeeklyBinding;
import com.paragon.sensonic.utils.CustomItemClickListener;
import com.paragon.sensonic.ui.adapters.SpinnerAdapter;
import com.paragon.utils.RecyclerItemClickListener;

import java.util.Arrays;


public class WeeklyFragment extends Fragment {

    private final CustomItemClickListener customItemClickListener;
    private FragmentWeeklyBinding binding;

    public WeeklyFragment(CustomItemClickListener customItemClickListener) {
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weekly, container, false);
        binding.setVariable(BR.weeklyVM, this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init() {
        SpinnerAdapter adapter = new SpinnerAdapter(Arrays.asList(getContext().getResources().getStringArray(R.array.label_week_array)));
        binding.listWeekly.setAdapter(adapter);
        binding.listWeekly.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> adapter.setPosition(position)));
    }
}