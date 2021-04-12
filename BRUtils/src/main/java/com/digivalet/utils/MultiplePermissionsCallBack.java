package com.digivalet.utils;

import com.karumi.dexter.PermissionToken;

/**
 * Created by Rupesh Saxena
 */

public interface MultiplePermissionsCallBack {
    void isAllPermissionsGranted();

    void isAnyPermissionPermanentlyDenied();

    void somePermissionsDenied();

    void onPermissionRationaleShouldBeShown(PermissionToken token);

    void onError();
}
