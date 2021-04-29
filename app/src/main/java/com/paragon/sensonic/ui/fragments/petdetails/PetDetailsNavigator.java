package com.paragon.sensonic.ui.fragments.petdetails;

public interface PetDetailsNavigator {
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
