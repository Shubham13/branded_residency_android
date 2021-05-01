package com.paragon.sensonic.ui.activities.profile;

import com.paragon.sensonic.utils.ProfileType;
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
