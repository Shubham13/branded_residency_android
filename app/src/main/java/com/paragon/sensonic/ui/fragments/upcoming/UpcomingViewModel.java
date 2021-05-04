package com.paragon.sensonic.ui.fragments.upcoming;

import android.content.Context;

import com.paragon.brdata.DataManager;
import com.paragon.brdata.dto.Friends;
import com.paragon.brdata.dto.Pets;
import com.paragon.brdata.dto.ResidentsRelationRoot;
import com.paragon.brdata.dto.Staff;
import com.paragon.brdata.dto.Vehicle;
import com.paragon.sensonic.utils.ProfileType;
import com.paragon.utils.base.BaseViewModel;

public class UpcomingViewModel extends BaseViewModel<UpcomingNavigator> {

    public void init() {
        getNavigator().init();
    }

    public void setResidentsData(Context context, ProfileType type) {
        ResidentsRelationRoot relationRoot = DataManager.getDataFromAssets(context, DataManager.RESIDENTS_RELATION, ResidentsRelationRoot.class);
        getNavigator().setResidentsList(relationRoot);
    }
}
