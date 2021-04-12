package com.digivalet.brandresidential.ui.fragments.guestsprofile;

import com.digivalet.brdata.dto.GuestProfile;
import com.digivalet.brdata.dto.HeaderData;

import java.util.List;

public interface GuestsProfileNavigator {
    void init();
    void setFilters(List<HeaderData> list);
    void setGuestsProfileList(GuestProfile profiles);
}
