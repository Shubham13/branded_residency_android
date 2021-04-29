package com.paragon.sensonic.ui.fragments.today;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.FragmentTodayBinding;
import com.paragon.utils.base.BaseFragment;
import com.paragon.utils.styler.Host;
import com.paragon.utils.styler.StyleBuilder;

public class TodayFragment extends BaseFragment<FragmentTodayBinding, TodayViewModel> implements TodayNavigator {

    private final TodayViewModel todayViewModel = new TodayViewModel();

    @Override
    public int getBindingVariable() {
        return BR.todayVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_today;
    }

    @Override
    public TodayViewModel getViewModel() {
        return todayViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todayViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todayViewModel.init();
    }

    @Override
    public void init() {
        mViewDataBinding.toolbar.toolbarTitle.setText(getString(R.string.menu_today));
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.INVISIBLE);
        mViewDataBinding.toolbar.toolbarLeftLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loadTheme() {
        StyleBuilder.setStyleOnText(Host.tool_bar, mViewDataBinding.toolbar.toolbarTitle);
    }
}
