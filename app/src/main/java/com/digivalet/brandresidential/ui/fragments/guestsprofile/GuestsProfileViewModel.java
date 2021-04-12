package com.digivalet.brandresidential.ui.fragments.guestsprofile;


import android.content.Context;

import com.digivalet.brdata.DataManager;
import com.digivalet.brdata.dto.GuestProfile;
import com.digivalet.utils.base.BaseViewModel;

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
