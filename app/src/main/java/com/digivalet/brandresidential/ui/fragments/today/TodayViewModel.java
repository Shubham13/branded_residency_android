package com.digivalet.brandresidential.ui.fragments.today;


import com.digivalet.utils.base.BaseViewModel;

public class TodayViewModel extends BaseViewModel<TodayNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().loadTheme();
    }
}
