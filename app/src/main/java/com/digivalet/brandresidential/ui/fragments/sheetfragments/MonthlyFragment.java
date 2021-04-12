package com.digivalet.brandresidential.ui.fragments.sheetfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.FragmentMonthBinding;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;


public class MonthlyFragment extends Fragment {

    private final CustomItemClickListener customItemClickListener;
    private FragmentMonthBinding fragmentMonthBinding;

    public MonthlyFragment(CustomItemClickListener customItemClickListener) {
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentMonthBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_month,container,false);
        return fragmentMonthBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
    }
}