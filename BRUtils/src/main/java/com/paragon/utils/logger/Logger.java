package com.paragon.utils.logger;

import android.util.Log;

/**
 * Created by Rupesh Saxena
 */

public class Logger {
    private static final boolean ENABLED = true;
    private static final boolean DISABLED = false;
    private static final boolean LOG_STATUS = ENABLED;
    private static final String LOG_PREFIX = " :-> ";
    private static final String LOG_CAT = "Logger";
    public static final String EXCEPTION_MSG = "Some Exception while printing logger :->";

    public static void e(String prefixP, String textToLog) {
        try {
            String prefix = prefixP + LOG_PREFIX;
            if (LOG_STATUS == ENABLED) {
                Log.e(LOG_CAT, prefix + textToLog);
            }
        } catch (Exception e) {
            Log.e(LOG_CAT, EXCEPTION_MSG + e.toString());
        }
    }

    public static void v(String prefixP, String textToLog) {
        try {
            String prefix = prefixP + LOG_PREFIX;
            if (LOG_STATUS == ENABLED) {
                Log.v(LOG_CAT, prefix + textToLog);
            }
        } catch (Exception e) {
            Log.v(LOG_CAT, EXCEPTION_MSG + e.toString());
        }
    }


    public static void d(String prefixP, String textToLog) {
        try {
            String prefix = prefixP + LOG_PREFIX;
            if (LOG_STATUS == ENABLED) {
                Log.d(LOG_CAT, prefix + textToLog);
            }
        } catch (Exception e) {
            Log.d(LOG_CAT, EXCEPTION_MSG + e.toString());
        }
    }


    public static void i(String prefixP, String textToLog) {
        try {
            String prefix = prefixP + LOG_PREFIX;
            if (LOG_STATUS == ENABLED) {
                Log.e(LOG_CAT, prefix + textToLog);
            }
        } catch (Exception e) {
            Log.i(LOG_CAT, EXCEPTION_MSG + e.toString());
        }
    }


    public static void w(String prefixP, String textToLog) {
        try {
            String prefix = prefixP + LOG_PREFIX;
            if (LOG_STATUS == ENABLED) {
                Log.w(LOG_CAT, prefix + textToLog);
            }
        } catch (Exception e) {
            Log.w(LOG_CAT, EXCEPTION_MSG + e.toString());
        }
    }
}
