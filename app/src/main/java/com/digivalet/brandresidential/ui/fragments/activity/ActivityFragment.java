package com.digivalet.brandresidential.ui.fragments.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.digivalet.brandresidential.BR;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.FragmentActivityBinding;
import com.digivalet.utils.base.BaseFragment;
import com.digivalet.utils.styler.Host;
import com.digivalet.utils.styler.StyleBuilder;

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
        mViewDataBinding.toolbar.toolbarBackBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loadTheme() {
        StyleBuilder.setStyleOnView(Host.tool_bar, mViewDataBinding.toolbar.toolbarDivider);
        StyleBuilder.setStyleOnText(Host.tool_bar, mViewDataBinding.toolbar.toolbarTitle);
    }
}
