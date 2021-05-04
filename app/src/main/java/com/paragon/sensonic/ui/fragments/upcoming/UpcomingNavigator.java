package com.paragon.sensonic.ui.fragments.upcoming;

import com.paragon.brdata.dto.ResidentsRelationRoot;

public interface UpcomingNavigator {
    void init();
    void setResidentsList(ResidentsRelationRoot relationRoot);
}
