package com.digivalet.brandresidential.ui.fragments.residentdetails;


import com.digivalet.utils.base.BaseViewModel;

public class ResidentDetailsViewModel extends BaseViewModel<ResidentDetailsNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().setFilterList();
        getNavigator().loadResidentDetailsView();
        getNavigator().loadContactInfoView();
        getNavigator().loadIdentificationView();
        getNavigator().loadInnerIdentificationItem();
        getNavigator().loadTheme();
        getNavigator().setRelationshipWithOwnerSpinner();
        getNavigator().setBirthdayDatePicker();
        getNavigator().setOccupationSpinner();
        getNavigator().setStatusSpinner();
        getNavigator().setEditorActionListener();
        getNavigator().setTextWatcher();
    }

    public void onSaveBTNClick() {
        getNavigator().onSaveBTNClick();
    }
}
