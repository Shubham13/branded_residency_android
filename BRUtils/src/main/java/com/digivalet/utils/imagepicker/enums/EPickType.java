package com.digivalet.utils.imagepicker.enums;

import java.util.Arrays;

/**
 * Created by Rupesh Saxena
 */

public enum EPickType {
    CAMERA, GALLERY;

    public boolean inside(EPickType[] array) {
        return Arrays.asList(array).contains(this);
    }

    public static EPickType[] fromInt(int val) {
        if (val > values().length - 1){
            return new EPickType[] {CAMERA, GALLERY};
        }else{
            return new EPickType[] {values()[val]};
        }
    }
}
