package com.paragon.sensonic.ui.fragments.book;


import com.paragon.utils.base.BaseViewModel;

public class BookViewModel extends BaseViewModel<BookNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().loadTheme();
    }
}
