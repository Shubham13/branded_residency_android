package com.paragon.sensonic.utils;

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

    /*server var*/
    public static final String BASE_URL = "https://api-qa.digivaletliving.com/auth/v1/";

    /*AWS Cognito Credentials Provider*/
    public static final String  IDENTITY_POOL_ID = "ap-south-1:7ba2a60a-ea6f-41df-b18c-927a620e1c5b";
    public static final String SERVICE_NAME = "execute-api";
    public static final String REGION = "ap-south-1";
    public static final String BRAND_ID = "606dcab8a12ad6ce63a92c90";
    public static final String PROPERTY_ID = "606dcafaa12ad6ce63a92c91";
    public static final String SCOPE = "MOBILE_ANDROID";

}
