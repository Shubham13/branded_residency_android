package com.digivalet.brandresidential.ui.fragments.profile;

import android.content.Context;

import com.digivalet.brandresidential.helpers.ProfileType;
import com.digivalet.brdata.DataManager;
import com.digivalet.brdata.dto.Friends;
import com.digivalet.brdata.dto.Pets;
import com.digivalet.brdata.dto.Profile;
import com.digivalet.brdata.dto.ResidentsRelationRoot;
import com.digivalet.brdata.dto.Staff;
import com.digivalet.brdata.dto.Vehicle;
import com.digivalet.utils.base.BaseViewModel;

public class ProfileViewModel extends BaseViewModel<ProfileNavigator> {

    public void init() {
        getNavigator().init();
    }

    public void getProfileData(Context context){
        Profile data = DataManager.getDataFromAssets(context,DataManager.PROFILE, Profile.class);
        getNavigator().setProfileData(data);
    }

    public void navigateToList(ProfileType type) {
        switch (type) {
            case residents:
                getNavigator().navigateToList(type);
                break;
            case interests:
                getNavigator().onInterestsItemClick();
                break;
            case vehicles:
                getNavigator().navigateToList(type);
                break;
            case pets:
                getNavigator().navigateToList(type);
                break;
            case staff:
                getNavigator().navigateToList(type);
                break;
            case guests:
                getNavigator().navigateToGuestsProfile();
                break;
            case friends:
                getNavigator().navigateToList(type);
                break;
            case guesthistory:
                getNavigator().onGuestHistoryClick();
                break;
        }
    }


    public void onProfileImageBrowsClick() {
        getNavigator().onProfileImageBrowseClick();
    }

    public void onInviteQrClick() {
        getNavigator().onInviteQrClick();
    }
}
