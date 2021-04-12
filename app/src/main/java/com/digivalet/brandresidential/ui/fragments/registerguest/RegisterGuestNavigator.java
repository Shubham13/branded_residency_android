package com.digivalet.brandresidential.ui.fragments.registerguest;

import com.digivalet.brdata.dto.GuestAccess;

public interface RegisterGuestNavigator {
    void init();

    void setExpandableView(GuestAccess guestAccess);

    void onRegisterClick();
}
