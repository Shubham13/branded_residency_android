package com.paragon.sensonic.ui.fragments.registerguest;

import com.paragon.brdata.dto.GuestAccess;

public interface RegisterGuestNavigator {
    void init();

    void setExpandableView(GuestAccess guestAccess);

    void onRegisterClick();
}
