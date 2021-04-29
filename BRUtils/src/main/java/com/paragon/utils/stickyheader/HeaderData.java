package com.paragon.utils.stickyheader;


import androidx.annotation.LayoutRes;

/**
 * Created by Rupesh Saxena
 */

public interface HeaderData extends StickyMainData {
    @LayoutRes
    int getHeaderLayout();

    int getHeaderType();
}
