package com.paragon.sensonic.ui.fragments.registerguest;


import android.content.Context;

import com.paragon.brdata.DataManager;
import com.paragon.brdata.dto.GuestAccess;
import com.paragon.utils.base.BaseViewModel;

public class RegisterGuestViewModel extends BaseViewModel<RegisterGuestNavigator> {

    public void init() {
        getNavigator().init();
    }

    public void getExpandableList(Context context) {
        GuestAccess guestAccess = DataManager.getDataFromAssets(context, DataManager.GUEST_ACCESS, GuestAccess.class);
        getNavigator().setExpandableView(guestAccess);
    }

    public void onRegisterClick(){
        getNavigator().onRegisterClick();
    }
}
