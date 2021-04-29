package com.paragon.sensonic.ui.fragments.profile;

import com.paragon.sensonic.helpers.ProfileType;
import com.paragon.brdata.dto.Profile;

public interface ProfileNavigator {
    void init();

    void navigateToList(ProfileType type);

    void navigateToGuestsProfile();

    void setProfileData(Profile data);

    void onProfileImageBrowseClick();

    void onInviteQrClick();

    void onInterestsItemClick();

    void onGuestHistoryClick();
}
