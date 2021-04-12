package com.digivalet.utils;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

/**
 * Created by Rupesh Saxena
 */

public class ActivityNavigator {

    private final Context mContext;

    public ActivityNavigator(Context mContext) {
        this.mContext = mContext;
    }

    public void startAct(Class<?> cls) {
        Intent mainIntent = new Intent(mContext, cls);
        mContext.startActivity(mainIntent);
    }

    public void startActWithData(Class<?> cls, Bundle bundle) {
        Intent mainIntent = new Intent(mContext, cls);
        mainIntent.putExtras(bundle);
        mContext.startActivity(mainIntent);
    }

    public void startActWithDataClearTop(Class<?> cls, Bundle bundle) {
        Intent mainIntent = new Intent(mContext, cls);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainIntent.putExtras(bundle);
        mContext.startActivity(mainIntent);
    }


    public void startActForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent mainIntent = new Intent(mContext, cls);
        mainIntent.putExtras(bundle);
        ((AppCompatActivity) mContext).startActivityForResult(mainIntent, requestCode);
    }

    public void startActForResult(Class<?> cls, int requestCode) {
        Intent mainIntent = new Intent(mContext, cls);
        ((AppCompatActivity) mContext).startActivityForResult(mainIntent, requestCode);
    }

    public void startActForResult(String cls, int requestCode) {
        Intent mainIntent = new Intent(cls);
        ((AppCompatActivity) mContext).startActivityForResult(mainIntent, requestCode);
    }


    public void startActForResult(Fragment fragment, Class<?> cls, int requestCode) {
        Intent mainIntent = new Intent(mContext, cls);
        fragment.startActivityForResult(mainIntent, requestCode);
    }

    public void startActForResult(Fragment fragment, Class<?> cls, int requestCode, Bundle bundle) {
        Intent mainIntent = new Intent(mContext, cls);
        mainIntent.putExtras(bundle);
        fragment.startActivityForResult(mainIntent, requestCode);
    }

    public void startActWithTransition(Class<?> cls, ActivityOptionsCompat options) {
        Intent mainIntent = new Intent(mContext, cls);
        mContext.startActivity(mainIntent, options.toBundle());
    }


    public void setOkResult() {
        Intent output = new Intent();
        ((AppCompatActivity) mContext).setResult(AppCompatActivity.RESULT_OK, output);
    }

    public void setOkResult(Bundle bn) {
        Intent output = new Intent();
        output.putExtras(bn);
        ((AppCompatActivity) mContext).setResult(AppCompatActivity.RESULT_OK, output);
    }

    public void setOkResult(int resultCode) {
        Intent output = new Intent();
        ((AppCompatActivity) mContext).setResult(resultCode, output);
    }
}
