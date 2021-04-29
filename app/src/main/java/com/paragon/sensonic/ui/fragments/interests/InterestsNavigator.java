package com.paragon.sensonic.ui.fragments.interests;

import com.paragon.brdata.dto.InterestData;

import java.util.List;

public interface InterestsNavigator {
    void init();

    void loadTheme();

    void setFilterList(List<InterestData> list);

    void setInterestList(List<InterestData> list);
}
