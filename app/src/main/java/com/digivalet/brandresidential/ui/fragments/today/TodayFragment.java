package com.digivalet.brandresidential.ui.fragments.today;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.digivalet.brandresidential.BR;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.FragmentActivityBinding;
import com.digivalet.brandresidential.databinding.FragmentTodayBinding;
import com.digivalet.utils.base.BaseFragment;
import com.digivalet.utils.styler.Host;
import com.digivalet.utils.styler.StyleBuilder;

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
        mViewDataBinding.toolbar.toolbarBackBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loadTheme() {
        StyleBuilder.setStyleOnView(Host.tool_bar, mViewDataBinding.toolbar.toolbarDivider);
        StyleBuilder.setStyleOnText(Host.tool_bar, mViewDataBinding.toolbar.toolbarTitle);
    }
}
