package com.paragon.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.Manifest.permission.EXPAND_STATUS_BAR;

/**
 * Created by Rupesh Saxena
 */

public class GeneralFunctions {

    /**
     * Date formats vars
     */
    public static final String DD_MMM_yyyy = "dd-MMM-yyyy";
    public static final String DD_MM_yyyy = "dd-MM-yyyy";
    public static final String DD_MM_yyyy_HH_mm_ss_EEE = "dd-MM-yyyy HH:mm:ss EEE";
    public static final String DD_MM_yyyy_HH_mm_ss = "dd-MM-yyyy HH:mm:ss";
    public static final String DD_MM_yyyy_hh_mm_ss_a = "dd-MM-yyyy hh:mm:ss a";
    public static final String EEEE_DD_MMM_yyyy_hh_mm_ss_a = "EEEE, dd MMM yyyy hh:mm:ss a";
    public static final String DD_MMM_yyyy_hh_mm_ss = "dd MMM yyyy hh:mm:ss";
    public static final String EEEE_DD_MMM_yyyy_HH_mm_ss = "EEEE, dd MMM yyyy HH:mm:ss";
    public static final String DD_MMM_yyyy_HH_mm_ss = "dd MMM yyyy HH:mm:ss";
    public static final String D_MM_yyyy_slash = "dd/MM/yyyy";
    public static final String DD_MMM_yyyy_slash = "dd/MMM/yyyy";
    public static final String DD_MM_yyyy_HH_mm_ss_slash = "dd/MM/yyyy HH:mm:ss";
    public static final String DD_MM_yyyy_hh_mm_ss_a_slash = "dd/MM/yyyy hh:mm:ss a";
    public static final String DD = "dd";
    public static final String MMM = "MMM";

    /**
     * Time formats vars
     */
    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String HH_mm = "HH:mm";
    public static final String hh_mm_ss_a = "hh:mm:ss a";
    public static final String hh_mm_ss = "hh:mm:ss";
    public static final String hh_mm = "hh:mm";

    /**
     * Status Bar
     */
    private static final String TAG_STATUS_BAR = "TAG_STATUS_BAR";
    private static final String TAG_OFFSET = "TAG_OFFSET";
    private static final int KEY_OFFSET = -123;

    /**
     * Other vars
     */
    public static final String EMPTY = "";


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////Date, Time & Day///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    /**
     * Get time by date
     *
     * @param date is used to get date
     */
    public static String getTimeByDate(Date date) {
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HH_mm_ss);
            return simpleDateFormat.format(date);
        } else {
            return EMPTY;
        }
    }

    /**
     * Change date format
     *
     * @param date           is used to get date
     * @param requiredFormat is desired date format
     */
    public static String changeDateFormat(Date date, String requiredFormat) {
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(requiredFormat);
            return simpleDateFormat.format(date);
        } else {
            return EMPTY;
        }
    }

    /**
     * Covert string date format in date object
     *
     * @param date           is given date
     * @param requiredFormat is desired date format
     */
    public static Date convertGivenDateFormat(String date, String requiredFormat) {
        try {
            if (date != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(requiredFormat);
                return simpleDateFormat.parse(date);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Calculate date difference in milliseconds
     *
     * @param startDate given start date
     * @param endDate   given end date
     */
    public static long getDatesDifferenceInMS(Date startDate, Date endDate) {
        return endDate.getTime() - startDate.getTime();
    }

    /**
     * Convert date format from given date format to required format
     *
     * @param date         given date
     * @param inputFormat  input date format
     * @param outputFormat output date format
     * @param locale       system local
     */
    public static String changeFormatByInToOutFormat(String date, String inputFormat, String outputFormat, Locale locale) {
        SimpleDateFormat input = new SimpleDateFormat(inputFormat, locale);
        SimpleDateFormat output = new SimpleDateFormat(outputFormat, locale);
        Date inputDate;
        String formattedDate = EMPTY;
        try {
            inputDate = input.parse(date);
            formattedDate = output.format(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    /**
     * Get current date & time in required format
     *
     * @param requiredFormat required date format
     * @param locale         system local
     */
    public static String getCurrentDateOrTime(String requiredFormat, Locale locale) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(requiredFormat, locale);
        return simpleDateFormat.format(date);
    }

    /**
     * Convert time by given format to required format
     *
     * @param time         given time
     * @param inputFormat  input time format
     * @param outputFormat output time format
     * @param locale       system local
     */
    public static String convertTimeByGivenFormat(String time, String inputFormat, String outputFormat, Locale locale) {
        SimpleDateFormat input = new SimpleDateFormat(inputFormat, locale);
        SimpleDateFormat output = new SimpleDateFormat(outputFormat, locale);
        Date _24HourDate = null;
        try {
            _24HourDate = input.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(_24HourDate);
    }

    /**
     * Get date from milliseconds
     *
     * @param milliSeconds   given milliseconds
     * @param requiredFormat required date format
     * @param locale         system local
     */
    public static String getDateFromMilliSeconds(long milliSeconds, String requiredFormat, Locale locale) {
        SimpleDateFormat formatter = new SimpleDateFormat(requiredFormat, locale);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    /**
     * Get milliseconds from date
     *
     * @param givenDate   given date
     * @param inputFormat input date format
     * @param locale      system local
     */
    public static long getMilliSecondsFromDate(String givenDate, String inputFormat, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat, locale);
        long timeInMilliseconds = 0;
        try {
            Date mDate = sdf.parse(givenDate);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    /**
     * Calculate date difference in days
     *
     * @param startDate given start date
     * @param endDate   given end date
     */
    public static String getDifferenceBetweenDateInDays(Date startDate, Date endDate) {

        String labelDays = " Days ";
        String labelHours = " Hours ";
        String labelMinutes = " Minutes ";
        String labelSeconds = " Seconds ";

        long difference = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = difference / daysInMilli;
        difference = difference % daysInMilli;

        long elapsedHours = difference / hoursInMilli;
        difference = difference % hoursInMilli;

        long elapsedMinutes = difference / minutesInMilli;
        difference = difference % minutesInMilli;

        long elapsedSeconds = difference / secondsInMilli;

        return elapsedDays + labelDays + elapsedHours + labelHours + elapsedMinutes + labelMinutes + elapsedSeconds + labelSeconds;
    }


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////Bitmap & Drawable///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    /**
     * Get bitmap for res. id
     *
     * @param context  activity context
     * @param drawable res. id
     */
    public static Bitmap getBitmapFromResourceId(Context context, int drawable) {
        return BitmapFactory.decodeResource(context.getResources(), drawable);
    }

    /**
     * Get bitmap from imageView
     *
     * @param context   activity context
     * @param imageView required imageView
     */
    public static Bitmap getBitmapFromImageView(Context context, ImageView imageView) {
        return BitmapFactory.decodeResource(context.getResources(), Integer.parseInt(imageView.getTag().toString()));
    }

    /**
     * Get drawable from bitmap
     *
     * @param context activity context
     * @param bitmap  required bitmap
     */
    public static Drawable getDrawableFromBitmap(Context context, Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }


    /**
     * Get drawable from res.
     *
     * @param context  activity context
     * @param drawable required res. id
     */
    public static Drawable getDrawableFromResourceId(Context context, int drawable) {
        return context.getResources().getDrawable(drawable);
    }


    /**
     * Convert URL to bitmap
     *
     * @param urlString given URL
     */
    public static Bitmap convertUrlToBitmap(String urlString) {
        try {
            if (!TextUtils.isEmpty(urlString)) {
                URL url = new URL(urlString);
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get bitmap from drawable
     *
     * @param context    activity context
     * @param drawableId required res. id
     */
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {

        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////Snack Bar///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    /**
     * Show snack bar
     *
     * @param view         parent views
     * @param message      required message to show
     * @param snackBarTime snack bar showing time
     */
    public static void showSnackBar(View view, String message, int snackBarTime) {
        Snackbar snackbar = Snackbar.make(view, message, snackBarTime);
        snackbar.show();
    }

    /**
     * Show snack bar with action
     *
     * @param view         parent views
     * @param message      required message to show
     * @param snackBarTime snack bar showing time
     * @param actionName   required action name
     * @param listener     action click event listener
     */
    public static void showSnackBarWithAction(View view, String message, int snackBarTime, String actionName, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, message, snackBarTime)
                .setAction(actionName, listener);
        snackbar.show();
    }

    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////Status Bar///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    /**
     * Return the status bar's height.
     *
     * @return the status bar's height
     */
    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * Set the status bar's visibility.
     *
     * @param activity  The activity.
     * @param isVisible True to set status bar visible, false otherwise.
     */
    public static void setStatusBarVisibility(@NonNull final Activity activity,
                                              final boolean isVisible) {
        setStatusBarVisibility(activity.getWindow(), isVisible);
    }

    /**
     * Set the status bar's visibility.
     *
     * @param window    The window.
     * @param isVisible True to set status bar visible, false otherwise.
     */
    public static void setStatusBarVisibility(@NonNull final Window window,
                                              final boolean isVisible) {
        if (isVisible) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            showStatusBarView(window);
            addMarginTopEqualStatusBarHeight(window);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            hideStatusBarView(window);
            subtractMarginTopEqualStatusBarHeight(window);
        }
    }

    /**
     * Return whether the status bar is visible.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarVisible(@NonNull final Activity activity) {
        int flags = activity.getWindow().getAttributes().flags;
        return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0;
    }

    /**
     * Set the status bar's light mode.
     *
     * @param activity    The activity.
     * @param isLightMode True to set status bar light mode, false otherwise.
     */
    public static void setStatusBarLightMode(@NonNull final Activity activity,
                                             final boolean isLightMode) {
        setStatusBarLightMode(activity.getWindow(), isLightMode);
    }

    /**
     * Set the status bar's light mode.
     *
     * @param window      The window.
     * @param isLightMode True to set status bar light mode, false otherwise.
     */
    public static void setStatusBarLightMode(@NonNull final Window window,
                                             final boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (isLightMode) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    /**
     * Is the status bar light mode.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarLightMode(@NonNull final Activity activity) {
        return isStatusBarLightMode(activity.getWindow());
    }

    /**
     * Is the status bar light mode.
     *
     * @param window The window.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarLightMode(@NonNull final Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                return (vis & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) != 0;
            }
        }
        return false;
    }

    /**
     * Add the top margin size equals status bar's height for view.
     *
     * @param view The view.
     */
    public static void addMarginTopEqualStatusBarHeight(@NonNull View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        view.setTag(TAG_OFFSET);
        Object haveSetOffset = view.getTag(KEY_OFFSET);
        if (haveSetOffset != null && (Boolean) haveSetOffset) return;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin + getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        view.setTag(KEY_OFFSET, true);
    }

    /**
     * Subtract the top margin size equals status bar's height for view.
     *
     * @param view The view.
     */
    public static void subtractMarginTopEqualStatusBarHeight(@NonNull View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Object haveSetOffset = view.getTag(KEY_OFFSET);
        if (haveSetOffset == null || !(Boolean) haveSetOffset) return;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin - getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        view.setTag(KEY_OFFSET, false);
    }

    private static void addMarginTopEqualStatusBarHeight(final Window window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        View withTag = window.getDecorView().findViewWithTag(TAG_OFFSET);
        if (withTag == null) return;
        addMarginTopEqualStatusBarHeight(withTag);
    }

    private static void subtractMarginTopEqualStatusBarHeight(final Window window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        View withTag = window.getDecorView().findViewWithTag(TAG_OFFSET);
        if (withTag == null) return;
        subtractMarginTopEqualStatusBarHeight(withTag);
    }

    /**
     * Set the status bar's color.
     *
     * @param activity The activity.
     * @param color    The status bar's color.
     */
    public static View setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color) {
        return setStatusBarColor(activity, color, false);
    }

    /**
     * Set the status bar's color.
     *
     * @param activity The activity.
     * @param color    The status bar's color.
     * @param isDecor  True to add fake status bar in DecorView,
     *                 false to add fake status bar in ContentView.
     */
    public static View setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color,
                                         final boolean isDecor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return null;
        transparentStatusBar(activity);
        return applyStatusBarColor(activity, color, isDecor);
    }

    /**
     * Set the status bar's color.
     *
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar,
                                         @ColorInt final int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Activity activity = getActivityByView(fakeStatusBar);
        if (activity == null) return;
        transparentStatusBar(activity);
        fakeStatusBar.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(color);
    }

    /**
     * Set the custom status bar.
     *
     * @param fakeStatusBar The fake status bar view.
     */
    public static void setStatusBarCustom(@NonNull final View fakeStatusBar) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Activity activity = getActivityByView(fakeStatusBar);
        if (activity == null) return;
        transparentStatusBar(activity);
        fakeStatusBar.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight()
            );
            fakeStatusBar.setLayoutParams(layoutParams);
        } else {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = getStatusBarHeight();
        }
    }

    /**
     * Set the status bar's color for DrawerLayout.
     * <p>DrawLayout must add {@code android:fitsSystemWindows="true"}</p>
     *
     * @param drawer        The DrawLayout.
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     */
    public static void setStatusBarColor4Drawer(@NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @ColorInt final int color) {
        setStatusBarColor4Drawer(drawer, fakeStatusBar, color, false);
    }

    /**
     * Set the status bar's color for DrawerLayout.
     * <p>DrawLayout must add {@code android:fitsSystemWindows="true"}</p>
     *
     * @param drawer        The DrawLayout.
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
     */
    public static void setStatusBarColor4Drawer(@NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @ColorInt final int color,
                                                final boolean isTop) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Activity activity = getActivityByView(fakeStatusBar);
        if (activity == null) return;
        transparentStatusBar(activity);
        drawer.setFitsSystemWindows(false);
        setStatusBarColor(fakeStatusBar, color);
        for (int i = 0, count = drawer.getChildCount(); i < count; i++) {
            drawer.getChildAt(i).setFitsSystemWindows(false);
        }
        if (isTop) {
            hideStatusBarView(activity);
        } else {
            setStatusBarColor(activity, color, false);
        }
    }

    private static View applyStatusBarColor(final Activity activity,
                                            final int color,
                                            boolean isDecor) {
        ViewGroup parent = isDecor ?
                (ViewGroup) activity.getWindow().getDecorView() :
                (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeStatusBarView = parent.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            fakeStatusBarView = createStatusBarView(activity, color);
            parent.addView(fakeStatusBarView);
        }
        return fakeStatusBarView;
    }

    private static void hideStatusBarView(final Activity activity) {
        hideStatusBarView(activity.getWindow());
    }

    private static void hideStatusBarView(final Window window) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }

    private static void showStatusBarView(final Window window) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.VISIBLE);
    }

    private static View createStatusBarView(final Activity activity,
                                            final int color) {
        View statusBarView = new View(activity);
        statusBarView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        statusBarView.setBackgroundColor(color);
        statusBarView.setTag(TAG_STATUS_BAR);
        return statusBarView;
    }

    private static void transparentStatusBar(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int vis = window.getDecorView().getSystemUiVisibility() & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                window.getDecorView().setSystemUiVisibility(option | vis);
            } else {
                window.getDecorView().setSystemUiVisibility(option);
            }
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * Return the action bar's height.
     *
     * @return the action bar's height
     */
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(
                    tv.data, context.getResources().getDisplayMetrics()
            );
        }
        return 0;
    }

    /**
     * Set the notification bar's visibility.
     * <p>Must hold {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />}</p>
     *
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    @RequiresPermission(EXPAND_STATUS_BAR)
    public static void setNotificationBarVisibility(Context context, final boolean isVisible) {
        String methodName;
        if (isVisible) {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "expand" : "expandNotificationsPanel";
        } else {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        }
        invokePanels(context, methodName);
    }

    private static void invokePanels(Context context, final String methodName) {
        try {
            @SuppressLint("WrongConstant")
            Object service = context.getSystemService("statusbar");
            @SuppressLint("PrivateApi")
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the navigation bar's height.
     *
     * @return the navigation bar's height
     */
    public static int getNavBarHeight() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * Set the navigation bar's visibility.
     *
     * @param activity  The activity.
     * @param isVisible True to set navigation bar visible, false otherwise.
     */
    public static void setNavBarVisibility(Context context, @NonNull final Activity activity, boolean isVisible) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        setNavBarVisibility(context, activity.getWindow(), isVisible);

    }

    /**
     * Set the navigation bar's visibility.
     *
     * @param window    The window.
     * @param isVisible True to set navigation bar visible, false otherwise.
     */
    public static void setNavBarVisibility(Context context, @NonNull final Window window, boolean isVisible) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        final ViewGroup decorView = (ViewGroup) window.getDecorView();
        for (int i = 0, count = decorView.getChildCount(); i < count; i++) {
            final View child = decorView.getChildAt(i);
            final int id = child.getId();
            if (id != View.NO_ID) {
                String resourceEntryName = context
                        .getResources()
                        .getResourceEntryName(id);
                if ("navigationBarBackground".equals(resourceEntryName)) {
                    child.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
                }
            }
        }
        final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (isVisible) {
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~uiOptions);
        } else {
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | uiOptions);
        }
    }

    /**
     * Return whether the navigation bar visible.
     * <p>Call it in onWindowFocusChanged will get right result.</p>
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNavBarVisible(Context context, @NonNull final Activity activity) {
        return isNavBarVisible(context, activity.getWindow());
    }

    /**
     * Return whether the navigation bar visible.
     * <p>Call it in onWindowFocusChanged will get right result.</p>
     *
     * @param window The window.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNavBarVisible(Context context, @NonNull final Window window) {
        boolean isVisible = false;
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        for (int i = 0, count = decorView.getChildCount(); i < count; i++) {
            final View child = decorView.getChildAt(i);
            final int id = child.getId();
            if (id != View.NO_ID) {
                String resourceEntryName = context
                        .getResources()
                        .getResourceEntryName(id);
                if ("navigationBarBackground".equals(resourceEntryName)
                        && child.getVisibility() == View.VISIBLE) {
                    isVisible = true;
                    break;
                }
            }
        }
        if (isVisible) {
            int visibility = decorView.getSystemUiVisibility();
            isVisible = (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
        }
        return isVisible;
    }

    /**
     * Set the navigation bar's color.
     *
     * @param activity The activity.
     * @param color    The navigation bar's color.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavBarColor(@NonNull final Activity activity, @ColorInt final int color) {
        setNavBarColor(activity.getWindow(), color);
    }

    /**
     * Set the navigation bar's color.
     *
     * @param window The window.
     * @param color  The navigation bar's color.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavBarColor(@NonNull final Window window, @ColorInt final int color) {
        window.setNavigationBarColor(color);
    }

    /**
     * Return the color of navigation bar.
     *
     * @param activity The activity.
     * @return the color of navigation bar
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getNavBarColor(@NonNull final Activity activity) {
        return getNavBarColor(activity.getWindow());
    }

    /**
     * Return the color of navigation bar.
     *
     * @param window The window.
     * @return the color of navigation bar
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getNavBarColor(@NonNull final Window window) {
        return window.getNavigationBarColor();
    }

    /**
     * Return whether the navigation bar visible.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isSupportNavBar(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (wm == null) return false;
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y || realSize.x != size.x;
        }
        boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        return !menu && !back;
    }

    private static Activity getActivityByView(@NonNull final View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        Log.e("BarUtils", "the view's Context is not an Activity.");
        return null;
    }


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////Brightness///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    /**
     * Return whether automatic brightness mode is enabled.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAutoBrightnessEnabled(Context context) {
        try {
            int mode = Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE
            );
            return mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Enable or disable automatic brightness mode.
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param enabled True to enabled, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean setAutoBrightnessEnabled(Context context, final boolean enabled) {
        return Settings.System.putInt(
                context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                enabled ? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                        : Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        );
    }

    /**
     * Get brightness
     *
     * @return 0-255
     */
    public static int getBrightness(Context context) {
        try {
            return Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Set brightness
     * <p>{@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param brightness
     */
    public static boolean setBrightness(Context context, @IntRange(from = 0, to = 255) final int brightness) {
        ContentResolver resolver = context.getContentResolver();
        boolean b = Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        resolver.notifyChange(Settings.System.getUriFor("screen_brightness"), null);
        return b;
    }

    /**
     * Set Window Brightness
     *
     * @param window
     * @param brightness
     */
    public static void setWindowBrightness(@NonNull final Window window,
                                           @IntRange(from = 0, to = 255) final int brightness) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255f;
        window.setAttributes(lp);
    }

    /**
     * Get Window Brightness
     *
     * @param window
     * @return 0-255
     */
    public static int getWindowBrightness(Context context, final Window window) {
        WindowManager.LayoutParams lp = window.getAttributes();
        float brightness = lp.screenBrightness;
        if (brightness < 0) return getBrightness(context);
        return (int) (brightness * 255);
    }

    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////Color///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns a color associated with a particular resource ID.
     *
     * @param id The desired resource identifier.
     * @return a color associated with a particular resource ID
     */
    public static int getColor(Context context, @ColorRes int id) {
        return ContextCompat.getColor(context, id);
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     *
     * @param color The color.
     * @param alpha Alpha component \([0..255]\) of the color.
     * @return the {@code color} with {@code alpha} component
     */
    public static int setAlphaComponent(@ColorInt final int color,
                                        @IntRange(from = 0x0, to = 0xFF) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     *
     * @param color The color.
     * @param alpha Alpha component \([0..1]\) of the color.
     * @return the {@code color} with {@code alpha} component
     */
    public static int setAlphaComponent(@ColorInt int color,
                                        @FloatRange(from = 0, to = 1) float alpha) {
        return (color & 0x00ffffff) | ((int) (alpha * 255.0f + 0.5f) << 24);
    }

    /**
     * Set the red component of {@code color} to be {@code red}.
     *
     * @param color The color.
     * @param red   Red component \([0..255]\) of the color.
     * @return the {@code color} with {@code red} component
     */
    public static int setRedComponent(@ColorInt int color,
                                      @IntRange(from = 0x0, to = 0xFF) int red) {
        return (color & 0xff00ffff) | (red << 16);
    }

    /**
     * Set the red component of {@code color} to be {@code red}.
     *
     * @param color The color.
     * @param red   Red component \([0..1]\) of the color.
     * @return the {@code color} with {@code red} component
     */
    public static int setRedComponent(@ColorInt int color,
                                      @FloatRange(from = 0, to = 1) float red) {
        return (color & 0xff00ffff) | ((int) (red * 255.0f + 0.5f) << 16);
    }

    /**
     * Set the green component of {@code color} to be {@code green}.
     *
     * @param color The color.
     * @param green Green component \([0..255]\) of the color.
     * @return the {@code color} with {@code green} component
     */
    public static int setGreenComponent(@ColorInt int color,
                                        @IntRange(from = 0x0, to = 0xFF) int green) {
        return (color & 0xffff00ff) | (green << 8);
    }

    /**
     * Set the green component of {@code color} to be {@code green}.
     *
     * @param color The color.
     * @param green Green component \([0..1]\) of the color.
     * @return the {@code color} with {@code green} component
     */
    public static int setGreenComponent(@ColorInt int color,
                                        @FloatRange(from = 0, to = 1) float green) {
        return (color & 0xffff00ff) | ((int) (green * 255.0f + 0.5f) << 8);
    }

    /**
     * Set the blue component of {@code color} to be {@code blue}.
     *
     * @param color The color.
     * @param blue  Blue component \([0..255]\) of the color.
     * @return the {@code color} with {@code blue} component
     */
    public static int setBlueComponent(@ColorInt int color,
                                       @IntRange(from = 0x0, to = 0xFF) int blue) {
        return (color & 0xffffff00) | blue;
    }

    /**
     * Set the blue component of {@code color} to be {@code blue}.
     *
     * @param color The color.
     * @param blue  Blue component \([0..1]\) of the color.
     * @return the {@code color} with {@code blue} component
     */
    public static int setBlueComponent(@ColorInt int color,
                                       @FloatRange(from = 0, to = 1) float blue) {
        return (color & 0xffffff00) | (int) (blue * 255.0f + 0.5f);
    }

    /**
     * Color-string to color-int.
     * <p>Supported formats are:</p>
     *
     * <ul>
     * <li><code>#RRGGBB</code></li>
     * <li><code>#AARRGGBB</code></li>
     * </ul>
     *
     * <p>The following names are also accepted: <code>red</code>, <code>blue</code>,
     * <code>green</code>, <code>black</code>, <code>white</code>, <code>gray</code>,
     * <code>cyan</code>, <code>magenta</code>, <code>yellow</code>, <code>lightgray</code>,
     * <code>darkgray</code>, <code>grey</code>, <code>lightgrey</code>, <code>darkgrey</code>,
     * <code>aqua</code>, <code>fuchsia</code>, <code>lime</code>, <code>maroon</code>,
     * <code>navy</code>, <code>olive</code>, <code>purple</code>, <code>silver</code>,
     * and <code>teal</code>.</p>
     *
     * @param colorString The color-string.
     * @return color-int
     * @throws IllegalArgumentException The string cannot be parsed.
     */
    public static int string2Int(@NonNull String colorString) {
        return Color.parseColor(colorString);
    }

    /**
     * Color-int to color-string.
     *
     * @param colorInt The color-int.
     * @return color-string
     */
    public static String int2RgbString(@ColorInt int colorInt) {
        colorInt = colorInt & 0x00ffffff;
        String color = Integer.toHexString(colorInt);
        while (color.length() < 6) {
            color = "0" + color;
        }
        return "#" + color;
    }

    /**
     * Color-int to color-string.
     *
     * @param colorInt The color-int.
     * @return color-string
     */
    public static String int2ArgbString(@ColorInt final int colorInt) {
        String color = Integer.toHexString(colorInt);
        while (color.length() < 6) {
            color = "0" + color;
        }
        while (color.length() < 8) {
            color = "f" + color;
        }
        return "#" + color;
    }

    /**
     * Return the random color.
     *
     * @return the random color
     */
    public static int getRandomColor() {
        return getRandomColor(true);
    }

    /**
     * Return the random color.
     *
     * @param supportAlpha True to support alpha, false otherwise.
     * @return the random color
     */
    public static int getRandomColor(final boolean supportAlpha) {
        int high = supportAlpha ? (int) (Math.random() * 0x100) << 24 : 0xFF000000;
        return high | (int) (Math.random() * 0x1000000);
    }


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////Others Methods///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    /**
     * Hide keyboard
     *
     * @param context required context
     */
    public static void hideKeyboard(@NonNull Context context) {
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * Get string by resource id
     *
     * @param context required context
     * @param resId   resource id
     */
    @NonNull
    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * Convert dp to px
     *
     * @param context required context
     * @param dp      dp size
     */
    public static float dpToPx(Context context, float dp) {
        return Math.round(dp * (context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * Convert px to dp
     *
     * @param context required context
     * @param px      px size
     */
    public static float pxToDp(Context context, float px) {
        return px * context.getResources().getDisplayMetrics().density;
    }

    /**
     * Convert px to sp for text size
     *
     * @param context required context
     * @param px      px
     */
    public static float pxToSp(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * Read file from assets folder
     *
     * @param context  to get assets dir.
     * @param fileName name of assets dir. file
     * @param fileExt  file extension of assets dir. file
     */
    public static String readFileByAssets(Context context, String fileName, String fileExt) {
        String json;
        String charsetName = "UTF-8";
        String dot = ".";

        try {
            InputStream is = context.getAssets().open(fileName + dot + fileExt);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, charsetName);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Serialize an object to Json.
     *
     * @param object to serialize.
     */
    public static String serialize(Object object, Class clazz) {
        return new Gson().toJson(object, clazz);
    }

    /**
     * Deserialize a json representation of an object
     *
     * @param string A json string to deserialize.
     */
    public static <T> T deserialize(String string, Class<T> clazz) {
        return new Gson().fromJson(string, clazz);
    }

    /**
     * Get the network info
     *
     * @param context to get NetworkInfo
     * @return {@link NetworkInfo}
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo();
        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
            return null;
        }
    }

    /**
     * Check if there is any connection
     *
     * @param context a {@link Context}
     * @return boolean
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnectedOrConnecting());
    }

    public static boolean isConnected(Context context, int connectionType) {
        switch (connectionType) {
            case ConnectivityManager.TYPE_WIFI:
                return isConnectedToWifi(context);
            case ConnectivityManager.TYPE_MOBILE:
                return isConnectedToMobile(context);
            default:
                return isConnected(context);
        }
    }

    /**
     * @param context
     * @return boolean
     * <p>
     * Instead use {@link com.paragon.utils.GeneralFunctions#isConnected(Context)}
     */
    @Deprecated
    public static boolean isOnline(Context context) {
        return isConnected(context);
    }

    /**
     * Check if there is any connection to a Wifi network
     *
     * @param context
     * @return boolean
     */
    public static boolean isConnectedToWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connection to a mobile network
     *
     * @param context
     * @return
     */
    public static boolean isConnectedToMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Check if the connection is fast
     *
     * @param type
     * @param subType
     * @return boolean
     */
    public static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Chopped list in sub list with
     * give length
     *
     * @param list
     * @param length
     */
    public static <T> ArrayList<ArrayList<T>> choppedList(ArrayList<T> list, final int length) {
        ArrayList<ArrayList<T>> parts = new ArrayList<ArrayList<T>>();
        final int listLength = list.size();
        for (int i = 0; i < listLength; i += length) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(listLength, i + length)))
            );
        }
        return parts;
    }


    /**
     * Make file part for multi-part request
     *
     * @param name
     * @param fileUri
     */
    public static MultipartBody.Part prepareFilePart(String name, Uri fileUri) {
        File file = new File(Objects.requireNonNull(fileUri.getPath()));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-brdata"), file);
        return MultipartBody.Part.createFormData(name, file.getName(), requestFile);
    }


    /**
     * Set margins of any view
     *
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    /*other functions*/

    /*get country code*/
    public static String getCountryMobileCode(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimCountryIso();
    }

    /*get country flag*/
    public static String getCountryFlag(String code) {
        int flagOffset = 0x1F1E6;
        int asciiOffset = 0x41;
        int firstChar = Character.codePointAt(code, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(code, 1) - asciiOffset + flagOffset;
        return new String(Character.toChars(firstChar)) + new String(Character.toChars(secondChar));
    }

    /*email validation*/
    public static boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern) && email.length() > 0;
    }

    public static boolean isValidMobileNumber(String mobile) {
        if (mobile.length()!=14) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(mobile).matches();
        }
    }

    /*internet check*/
    public static boolean isInternetAvailable() {
        try {
            final String TEST_URL = "www.google.com";
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            InetAddress ip = InetAddress.getByName(TEST_URL);
            return !ip.equals("");
        } catch (Exception e) {
            return false;
        }
    }
}
