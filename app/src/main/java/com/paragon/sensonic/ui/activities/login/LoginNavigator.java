package com.paragon.sensonic.ui.activities.login;

import com.paragon.sensonic.auth.dto.PasswordLessLogin;

public interface LoginNavigator {
    void init();
    void onLoginClick();
    void onCountyCodeClick();
    void onMobileClick();
    void onEmailClick();
    void onShowProgress();
    void onHideProgress();
    void setErrorText(boolean b, String errorText);
    void onSuccess(PasswordLessLogin response);
    void onError(String error);
}
