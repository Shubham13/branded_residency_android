package com.paragon.sensonic.ui.fragments.shop;


import com.paragon.utils.base.BaseViewModel;

public class ShopViewModel extends BaseViewModel<ShopNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().loadTheme();
    }
}
