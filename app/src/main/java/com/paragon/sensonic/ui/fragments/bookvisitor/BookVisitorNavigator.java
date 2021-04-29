package com.paragon.sensonic.ui.fragments.bookvisitor;



import com.paragon.brdata.dto.GuestType;

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
