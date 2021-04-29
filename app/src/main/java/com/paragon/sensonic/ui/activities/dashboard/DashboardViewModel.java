package com.paragon.sensonic.ui.activities.dashboard;

import com.paragon.utils.base.BaseViewModel;

public class DashboardViewModel extends BaseViewModel<DashboardNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().initBottomNavFragment();
        getNavigator().loadDefaultFragment();
    }
}
