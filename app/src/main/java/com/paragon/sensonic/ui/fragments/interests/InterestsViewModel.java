package com.paragon.sensonic.ui.fragments.interests;


import android.content.Context;

import com.paragon.brdata.DataManager;
import com.paragon.brdata.dto.Interest;
import com.paragon.utils.base.BaseViewModel;

public class InterestsViewModel extends BaseViewModel<InterestsNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().loadTheme();
    }

    public void getInterestsData(Context context) {
        Interest interest = DataManager.getDataFromAssets(context, DataManager.INTEREST, Interest.class);
        getNavigator().setFilterList(interest.getData());
        getNavigator().setInterestList(interest.getData());
    }
}
