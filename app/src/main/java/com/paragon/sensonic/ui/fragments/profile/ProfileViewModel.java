package com.paragon.sensonic.ui.fragments.profile;

import android.content.Context;

import com.paragon.sensonic.helpers.ProfileType;
import com.paragon.brdata.DataManager;
import com.paragon.brdata.dto.Profile;
import com.paragon.utils.base.BaseViewModel;

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
