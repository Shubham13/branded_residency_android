package com.paragon.sensonic.ui.fragments.sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.BottomDialogCustomBinding;
import com.paragon.sensonic.utils.CustomItemClickListener;
import com.paragon.sensonic.ui.adapters.CustomDialogAdapter;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

public class CustomDialogFragment extends BottomSheetDialogFragment {

    private BottomDialogCustomBinding bottomDialogCustomBinding;
    private CustomItemClickListener customItemClickListener;
    private TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bottomDialogCustomBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_dialog_custom, container, false);
        return bottomDialogCustomBinding.getRoot();
    }

    public void setCustomClickListener(CustomItemClickListener customClickListener) {
        this.customItemClickListener = customClickListener;
    }

    public void setTextViews(TextView textViews) {
        this.textView = textViews;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        bottomDialogCustomBinding.toolbar.toolbarRightImage.setVisibility(View.INVISIBLE);
        bottomDialogCustomBinding.toolbar.toolbarTitle.setText(getString(R.string.label_custom));
        //bottomDialogCustomBinding.toolbar.toolbarBackBtn.setText(getString(R.string.label_repeat));
        bottomDialogCustomBinding.calendarTabLayout.addTab(bottomDialogCustomBinding.calendarTabLayout.newTab().setText(getString(R.string.label_daily)));
        bottomDialogCustomBinding.calendarTabLayout.addTab(bottomDialogCustomBinding.calendarTabLayout.newTab().setText(getString(R.string.label_weekly)));
        bottomDialogCustomBinding.calendarTabLayout.addTab(bottomDialogCustomBinding.calendarTabLayout.newTab().setText(getString(R.string.label_monthly)));
        bottomDialogCustomBinding.toolbar.toolbarLeftLayout.setOnClickListener(view -> {
            dismiss();
        });

        /*set adapter*/
        CustomDialogAdapter customAdapter = new CustomDialogAdapter(getChildFragmentManager(), customItemClickListener);
        bottomDialogCustomBinding.customViewPager.setAdapter(customAdapter);
        bottomDialogCustomBinding.customViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(bottomDialogCustomBinding.calendarTabLayout));

        bottomDialogCustomBinding.calendarTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                bottomDialogCustomBinding.customViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
