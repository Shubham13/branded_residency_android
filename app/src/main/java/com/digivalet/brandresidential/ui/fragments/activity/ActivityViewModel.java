package com.digivalet.brandresidential.ui.fragments.activity;


import com.digivalet.utils.base.BaseViewModel;

public class ActivityViewModel extends BaseViewModel<ActivityNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().loadTheme();
    }
}
