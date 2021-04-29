package com.paragon.brdata;

import android.content.Context;

import com.paragon.utils.GeneralFunctions;

public class DataManager {

    public static String CALENDAR_OPTIONS = "calendarOption";
    public static String FRIENDS = "friends";
    public static String GUEST_ACCESS = "GuestAccess";
    public static String GUEST_HISTORY = "guestHistory";
    public static String GUEST_PROFILE = "guestProfile";
    public static String GUEST_TYPE = "GuestType";
    public static String INTEREST = "interests";
    public static String MEETING_ROOM_BOOKING = "meetingRoomBooking";
    public static String MEETING_ROOM_DETAIL = "meetingRoomDetail";
    public static String PETS = "pets";
    public static String PROFILE = "profile";
    public static String PROFILE_DETAIL = "profileDetail";
    public static String RESIDENTS_RELATION = "residentsRelation";
    public static String SPORTS_BOOKING = "sportsBooking";
    public static String SPORTS_BOOKING_DETAILS = "sportsBookingDetail";
    public static String STAFF = "staff";
    public static String SUGGESTION = "suggestions";
    public static String TODAY_TAB = "todayTab";
    public static String VEHICLES = "vehicles";

    /*other vars*/
    private final static String fileExt = "json";

    public static <T> T getDataFromAssets(Context context, String fileName, Class<T> clazz) {
        return GeneralFunctions.deserialize(GeneralFunctions.readFileByAssets(context, fileName, fileExt), clazz);
    }

}
