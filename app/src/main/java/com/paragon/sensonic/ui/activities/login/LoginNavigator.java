package com.paragon.sensonic.ui.activities.login;

import com.paragon.sensonic.data.LoginResponse;

public interface LoginNavigator {
    void init();
    void onLoginClick();
    void onMobileClick();
    void onEmailClick();
    void onShowProgress();
    void onHideProgress();
    void onSuccess(LoginResponse response);
    void onError(String error);
}
