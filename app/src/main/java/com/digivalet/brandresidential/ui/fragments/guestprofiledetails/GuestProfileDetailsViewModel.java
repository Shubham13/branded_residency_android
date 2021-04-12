package com.digivalet.brandresidential.ui.fragments.guestprofiledetails;


import android.content.Context;

import com.digivalet.brdata.DataManager;
import com.digivalet.brdata.dto.GuestAccess;
import com.digivalet.utils.base.BaseViewModel;

public class GuestProfileDetailsViewModel extends BaseViewModel<GuestProfileDetailsNavigator> {

    public void init() {
        getNavigator().init();
    }

    public void onDateClick() {
        getNavigator().onDatePickerClick();
    }

    public void onGuestTypeClick() {
        getNavigator().onGuestTypeClick();
    }

    public void getExpandableList(Context context) {
        GuestAccess guestAccess = DataManager.getDataFromAssets(context, DataManager.GUEST_ACCESS, GuestAccess.class);
        getNavigator().setExpandableView(guestAccess);
    }
}
