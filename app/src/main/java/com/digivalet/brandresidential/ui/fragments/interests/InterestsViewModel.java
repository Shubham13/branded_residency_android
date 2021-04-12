package com.digivalet.brandresidential.ui.fragments.interests;


import android.content.Context;

import com.digivalet.brdata.DataManager;
import com.digivalet.brdata.dto.Interest;
import com.digivalet.utils.base.BaseViewModel;

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
