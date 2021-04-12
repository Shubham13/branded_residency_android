package com.digivalet.brandresidential.ui.fragments.petdetails;


import com.digivalet.utils.base.BaseViewModel;

public class PetDetailsViewModel extends BaseViewModel<PetDetailsNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().setFilterList();
        getNavigator().loadResidentDetailsView();
        getNavigator().loadIdentificationView();
        getNavigator().loadInnerIdentificationItem();
        getNavigator().setRelationshipWithOwnerSpinner();
        getNavigator().setBirthdayDatePicker();
        getNavigator().setOccupationSpinner();
        getNavigator().setStatusSpinner();
    }

    public void onSaveBTNClick() {
        getNavigator().onSaveBTNClick();
    }
}
