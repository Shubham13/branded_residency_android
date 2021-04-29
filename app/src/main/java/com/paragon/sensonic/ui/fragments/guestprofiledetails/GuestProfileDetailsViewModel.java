package com.paragon.sensonic.ui.fragments.guestprofiledetails;


import android.content.Context;

import com.paragon.brdata.DataManager;
import com.paragon.brdata.dto.GuestAccess;
import com.paragon.utils.base.BaseViewModel;

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
