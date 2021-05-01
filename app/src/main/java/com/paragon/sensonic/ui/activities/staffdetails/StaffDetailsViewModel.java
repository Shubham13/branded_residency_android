package com.paragon.sensonic.ui.activities.staffdetails;


import com.paragon.utils.base.BaseViewModel;

public class StaffDetailsViewModel extends BaseViewModel<StaffDetailsNavigator> {

    public void init() {
        getNavigator().init();
        getNavigator().setFilterList();
        getNavigator().loadResidentDetailsView();
        getNavigator().loadContactInfoView();
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
