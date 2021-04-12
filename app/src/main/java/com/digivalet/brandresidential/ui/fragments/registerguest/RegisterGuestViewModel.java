package com.digivalet.brandresidential.ui.fragments.registerguest;


import android.content.Context;

import com.digivalet.brdata.DataManager;
import com.digivalet.brdata.dto.GuestAccess;
import com.digivalet.utils.base.BaseViewModel;

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
