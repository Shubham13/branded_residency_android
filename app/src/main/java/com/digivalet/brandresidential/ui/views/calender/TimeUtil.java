package com.digivalet.brandresidential.ui.views.calender;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {
    private static final String DATE_FORMAT_1 = "dd MMM yy";

    public static long getTimestamp(String dateString) {
        try {
            Date date = new SimpleDateFormat(DATE_FORMAT_1, Locale.ENGLISH).parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getDate(long time) {
        Date date = new Date(time);
        DateFormat format = new SimpleDateFormat(DATE_FORMAT_1, Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }

    public static boolean isPastDate(String input) {
        try {
            Date date = new SimpleDateFormat(DATE_FORMAT_1, Locale.ENGLISH).parse(input);
            return date.before(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getWeek(String input){
        Date date = null;
        try {
            SimpleDateFormat inFormat = new SimpleDateFormat(DATE_FORMAT_1);
            date = inFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        return outFormat.format(date);
    }
}
