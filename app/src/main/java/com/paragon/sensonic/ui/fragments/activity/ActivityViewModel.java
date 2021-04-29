package com.paragon.sensonic.ui.fragments.activity;


import com.paragon.utils.base.BaseViewModel;

public class ActivityViewModel extends BaseViewModel<ActivityNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().loadTheme();
    }
}
