package com.digivalet.brandresidential.ui.fragments.residentdetails;

public interface ResidentDetailsNavigator {
    void init();

    void setFilterList();

    void loadResidentDetailsView();

    void loadContactInfoView();

    void loadIdentificationView();

    void loadInnerIdentificationItem();

    void loadTheme();

    void onSaveBTNClick();

    void setRelationshipWithOwnerSpinner();

    void setBirthdayDatePicker();

    void setStatusSpinner();

    void setOccupationSpinner();

    void setIdTypeSpinner();

    void setEditorActionListener();

    void setTextWatcher();

    void validateAllFields();
}
