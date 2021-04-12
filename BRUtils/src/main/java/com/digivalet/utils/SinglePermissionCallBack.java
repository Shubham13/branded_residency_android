package com.digivalet.utils;

import com.karumi.dexter.PermissionToken;

/**
 * Created by Rupesh Saxena
 */

public interface SinglePermissionCallBack {
    void isPermissionsGranted();

    void isPermissionDenied();

    void isPermissionPermanentlyDenied();

    void onPermissionRationaleShouldBeShown(PermissionToken token);

    void onError();
}
