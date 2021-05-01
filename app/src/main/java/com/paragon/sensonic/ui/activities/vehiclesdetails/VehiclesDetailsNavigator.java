package com.paragon.sensonic.ui.activities.vehiclesdetails;

public interface VehiclesDetailsNavigator {
    void init();

    void setFilterList();

    void loadResidentDetailsView();

    void loadContactInfoView();

    void loadIdentificationView();

    void loadInnerIdentificationItem();

    void onSaveBTNClick();

    void setRelationshipWithOwnerSpinner();

    void setBirthdayDatePicker();

    void setStatusSpinner();

    void setOccupationSpinner();

    void setIdTypeSpinner();
}
