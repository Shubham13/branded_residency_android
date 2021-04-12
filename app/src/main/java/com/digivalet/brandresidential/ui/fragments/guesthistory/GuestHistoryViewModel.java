package com.digivalet.brandresidential.ui.fragments.guesthistory;


import android.content.Context;

import com.digivalet.brdata.DataManager;

import com.digivalet.brdata.dto.GuestHistory;
import com.digivalet.utils.base.BaseViewModel;

public class GuestHistoryViewModel extends BaseViewModel<GuestHistoryNavigator> {

    public void init() {
        getNavigator().init();
    }

    public void getGuestHistoryData(Context context){
        GuestHistory guestHistory = DataManager.getDataFromAssets(context, DataManager.GUEST_HISTORY, GuestHistory.class);
        getNavigator().setGuestHistory(guestHistory);
    }
}
