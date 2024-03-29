package com.paragon.sensonic.ui.activities.frienddetails;


import com.paragon.utils.base.BaseViewModel;

public class FriendDetailsViewModel extends BaseViewModel<FriendDetailsNavigator> {

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
