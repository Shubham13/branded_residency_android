package com.paragon.sensonic.ui.fragments.residents;


import android.content.Context;

import com.paragon.sensonic.helpers.ProfileType;
import com.paragon.brdata.DataManager;
import com.paragon.brdata.dto.Friends;
import com.paragon.brdata.dto.Pets;
import com.paragon.brdata.dto.ResidentsRelationRoot;
import com.paragon.brdata.dto.Staff;
import com.paragon.brdata.dto.Vehicle;
import com.paragon.utils.base.BaseViewModel;


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
