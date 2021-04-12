package com.digivalet.brandresidential.ui.activities.dashboard;

import com.digivalet.utils.base.BaseViewModel;

public class DashboardViewModel extends BaseViewModel<DashboardNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().initBottomNavFragment();
        getNavigator().loadDefaultFragment();
    }
}
