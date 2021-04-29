package com.paragon.sensonic.ui.fragments.bookvisitor;


import android.content.Context;

import com.paragon.brdata.DataManager;
import com.paragon.brdata.dto.GuestType;
import com.paragon.utils.base.BaseViewModel;
import com.paragon.utils.logger.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class BookVisitorViewModel extends BaseViewModel<BookVisitorNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().setDateSelector(Calendar.getInstance().getTime());
    }

    public void onOneTimeGuestClick() {
        getNavigator().onOneTimeGuestClick();
    }

    public void onFrequentGuestClick() {
        getNavigator().onFrequentGuestClick();
    }

    public void onShortStayingGuestClick() {
        getNavigator().onShortStayingGuestClick();
    }

    public void onContinueClick() {
        getNavigator().onContinueClick();
    }

    public void onGuestStatusClick() {
        getNavigator().onGuestStatusClick();
    }

    public void setGuestTypeData(Context context) {
        GuestType guestType = DataManager.getDataFromAssets(context, DataManager.GUEST_TYPE, GuestType.class);
        getNavigator().setGuestTypeData(guestType);
    }

    public void onRepeatClick() {
        getNavigator().onRepeatClick();
    }

    public void onTimeClick() {
        getNavigator().onTimeClick();
    }

    public void startDateClick() {
        getNavigator().startDateClick();
    }

    public void allowUntilClick() {
        getNavigator().allowUntilClick();
    }

    public List<Date> getDates(Date date) {
        List<Date> dateList = new ArrayList<>();
        for (int index = 0; index < 5; index++) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            gc.add(Calendar.DATE, index);
            dateList.add(gc.getTime());
            Logger.d("Dates", "getDates: " + gc.getTime());
        }
        return dateList;
    }

}
