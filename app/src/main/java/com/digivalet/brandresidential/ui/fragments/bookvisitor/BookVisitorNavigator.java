package com.digivalet.brandresidential.ui.fragments.bookvisitor;



import com.digivalet.brdata.dto.GuestType;

import java.util.Date;

public interface BookVisitorNavigator {

    void init();

    void setGuestTypeData(GuestType guestType);

    void onOneTimeGuestClick();

    void onFrequentGuestClick();

    void onShortStayingGuestClick();

    void onContinueClick();

    void onGuestStatusClick();

    void onRepeatClick();

    void onTimeClick();

    void setDateSelector(Date date);

    void startDateClick();

    void allowUntilClick();
}
