package com.digivalet.brandresidential.ui.fragments.shop;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.digivalet.brandresidential.BR;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.FragmentShopBinding;
import com.digivalet.utils.base.BaseFragment;
import com.digivalet.utils.styler.Host;
import com.digivalet.utils.styler.StyleBuilder;

public class ShopFragment extends BaseFragment<FragmentShopBinding, ShopViewModel> implements ShopNavigator {

    private final ShopViewModel shopViewModel = new ShopViewModel();

    @Override
    public int getBindingVariable() {
        return BR.shopVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    public ShopViewModel getViewModel() {
        return shopViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shopViewModel.init();
    }

    @Override
    public void init() {
        mViewDataBinding.toolbar.toolbarTitle.setText(getString(R.string.menu_shopping));
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.INVISIBLE);
        mViewDataBinding.toolbar.toolbarLeftLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loadTheme() {
        StyleBuilder.setStyleOnText(Host.tool_bar, mViewDataBinding.toolbar.toolbarTitle);
    }
}
