package com.digivalet.brandresidential.ui.fragments.shop;


import com.digivalet.utils.base.BaseViewModel;

public class ShopViewModel extends BaseViewModel<ShopNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().loadTheme();
    }
}
