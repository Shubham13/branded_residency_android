package com.paragon.sensonic.ui.activities.guestsprofile;

import com.paragon.brdata.dto.GuestProfile;
import com.paragon.brdata.dto.HeaderData;

import java.util.List;

public interface GuestsProfileNavigator {
    void init();
    void setFilters(List<HeaderData> list);
    void setGuestsProfileList(GuestProfile profiles);
}
