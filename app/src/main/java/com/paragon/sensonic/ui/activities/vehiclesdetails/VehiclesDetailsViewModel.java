package com.paragon.sensonic.ui.activities.vehiclesdetails;


import com.paragon.utils.base.BaseViewModel;

public class VehiclesDetailsViewModel extends BaseViewModel<VehiclesDetailsNavigator> {

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
