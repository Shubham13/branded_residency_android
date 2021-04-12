package com.digivalet.brandresidential.ui.fragments.guestprofiledetails;

import com.digivalet.brdata.dto.GuestAccess;

public interface GuestProfileDetailsNavigator {
    void init();

    void setExpandableView(GuestAccess guestAccess);

    void onDatePickerClick();

    void onGuestTypeClick();
}
