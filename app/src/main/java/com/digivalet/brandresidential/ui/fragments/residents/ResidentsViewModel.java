package com.digivalet.brandresidential.ui.fragments.residents;


import android.content.Context;

import com.digivalet.brandresidential.helpers.ProfileType;
import com.digivalet.brdata.DataManager;
import com.digivalet.brdata.dto.Friends;
import com.digivalet.brdata.dto.Pets;
import com.digivalet.brdata.dto.ResidentsRelationRoot;
import com.digivalet.brdata.dto.Staff;
import com.digivalet.brdata.dto.Vehicle;
import com.digivalet.utils.base.BaseViewModel;


public class ResidentsViewModel extends BaseViewModel<ResidentsNavigator> {

    public void init() {
        getNavigator().init();
    }

    public void setResidentsData(Context context, ProfileType type) {
        switch (type) {
            case residents:
                ResidentsRelationRoot relationRoot = DataManager.getDataFromAssets(context, DataManager.RESIDENTS_RELATION, ResidentsRelationRoot.class);
                getNavigator().setResidentsList(relationRoot);
                break;
            case interests:
                break;
            case vehicles:
                Vehicle vehicle = DataManager.getDataFromAssets(context, DataManager.VEHICLES, Vehicle.class);
                getNavigator().setVehiclesList(vehicle);
                break;
            case pets:
                Pets petsRoot = DataManager.getDataFromAssets(context, DataManager.PETS, Pets.class);
                getNavigator().setPetList(petsRoot);
                break;
            case staff:
                Staff staffRoot = DataManager.getDataFromAssets(context, DataManager.STAFF, Staff.class);
                getNavigator().setStaffList(staffRoot);
                break;
            case guests:
                break;
            case friends:
                Friends friendsRoot = DataManager.getDataFromAssets(context, DataManager.FRIENDS, Friends.class);
                getNavigator().setFriendsList(friendsRoot);
                break;
            case guesthistory:
                break;
        }
    }

}
