package com.digivalet.brandresidential.ui.fragments.interests;

import com.digivalet.brdata.dto.InterestData;

import java.util.List;

public interface InterestsNavigator {
    void init();

    void loadTheme();

    void setFilterList(List<InterestData> list);

    void setInterestList(List<InterestData> list);
}
