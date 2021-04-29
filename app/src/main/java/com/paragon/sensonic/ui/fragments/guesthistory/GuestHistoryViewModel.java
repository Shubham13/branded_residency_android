package com.paragon.sensonic.ui.fragments.guesthistory;


import android.content.Context;

import com.paragon.brdata.DataManager;

import com.paragon.brdata.dto.GuestHistory;
import com.paragon.utils.base.BaseViewModel;

public class GuestHistoryViewModel extends BaseViewModel<GuestHistoryNavigator> {

    public void init() {
        getNavigator().init();
    }

    public void getGuestHistoryData(Context context){
        GuestHistory guestHistory = DataManager.getDataFromAssets(context, DataManager.GUEST_HISTORY, GuestHistory.class);
        getNavigator().setGuestHistory(guestHistory);
    }
}
