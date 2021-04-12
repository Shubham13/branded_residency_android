package com.digivalet.brandresidential.ui.fragments.book;


import com.digivalet.utils.base.BaseViewModel;

public class BookViewModel extends BaseViewModel<BookNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().loadTheme();
    }
}
