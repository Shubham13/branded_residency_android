package com.paragon.sensonic.ui.fragments.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.FragmentActivityBinding;
import com.paragon.utils.base.BaseFragment;
import com.paragon.utils.styler.Host;
import com.paragon.utils.styler.StyleBuilder;

public class ActivityFragment extends BaseFragment<FragmentActivityBinding, ActivityViewModel> implements ActivityNavigator {

    private final ActivityViewModel activityViewModel = new ActivityViewModel();

    @Override
    public int getBindingVariable() {
        return BR.activityVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_activity;
    }

    @Override
    public ActivityViewModel getViewModel() {
        return activityViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityViewModel.init();
    }

    @Override
    public void init() {
        mViewDataBinding.toolbar.toolbarTitle.setText(getString(R.string.menu_activity));
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.INVISIBLE);
        mViewDataBinding.toolbar.toolbarLeftLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loadTheme() {
        StyleBuilder.setStyleOnText(Host.tool_bar, mViewDataBinding.toolbar.toolbarTitle);
    }
}
