package com.paragon.sensonic.ui.activities.guestprofiledetails;

import com.paragon.brdata.dto.GuestAccess;

public interface GuestProfileDetailsNavigator {
    void init();

    void setExpandableView(GuestAccess guestAccess);

    void onDatePickerClick();

    void onGuestTypeClick();
}
