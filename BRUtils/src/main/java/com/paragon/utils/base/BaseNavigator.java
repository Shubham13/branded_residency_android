package com.paragon.utils.base;

import android.content.Context;

import com.paragon.utils.ActivityNavigator;
import com.paragon.utils.dialogs.OnDialogButtonsClick;

/**
 * Created by Rupesh Saxena
 */

public interface BaseNavigator {

    default void setStatusBarColor(int color){}

    default void hideStatusBar(){}

    default void showProgressDialog(){}

    default void hideProgressDialog(){}

    default void showGeneralDialog(String title, String message, String buttonTxt, OnDialogButtonsClick listener){}

    default void showNPButtonDialog(String title, String message, String pButtonTxt, String nButtonTxt, OnDialogButtonsClick listener){}

    int getIntegerResource(int id);

    ActivityNavigator getActivityNavigator(Context context);
}
