package com.digivalet.brandresidential.ui.fragments.profile;

import com.digivalet.brandresidential.helpers.ProfileType;
import com.digivalet.brdata.dto.Profile;

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
