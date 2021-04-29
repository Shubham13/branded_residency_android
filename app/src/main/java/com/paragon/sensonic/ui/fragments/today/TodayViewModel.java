package com.paragon.sensonic.ui.fragments.today;


import com.paragon.utils.base.BaseViewModel;

public class TodayViewModel extends BaseViewModel<TodayNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().loadTheme();
    }
}
