package com.paragon.sensonic.ui.activities.guestsprofile;


import android.content.Context;

import com.paragon.brdata.DataManager;
import com.paragon.brdata.dto.GuestProfile;
import com.paragon.utils.base.BaseViewModel;

public class GuestsProfileViewModel extends BaseViewModel<GuestsProfileNavigator> {

    public void init() {
        getNavigator().init();
    }

    public void getGuestsData(Context context) {
        GuestProfile data = DataManager.getDataFromAssets(context, DataManager.GUEST_PROFILE, GuestProfile.class);
        getNavigator().setFilters(data.getHeaderData());
        getNavigator().setGuestsProfileList(data);
    }
}
