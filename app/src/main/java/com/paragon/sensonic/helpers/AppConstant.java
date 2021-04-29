package com.paragon.sensonic.helpers;

import android.Manifest;

public class AppConstant {

    /*general vars*/
    public static final String SPACE = " ";
    public static final String COMMA_SPACE = ", ";
    public static final String COMMA = ",";
    public static final String EMPTY = "";

    /*intent request code*/
    public static final int REQUEST_SETTINGS = 101;

    /*permissions*/
    public static final String[] READ_WRITE_EXTERNAL_STORAGE_AND_CAMERA = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
}
