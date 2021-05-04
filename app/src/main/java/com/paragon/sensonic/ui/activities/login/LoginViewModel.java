package com.paragon.sensonic.ui.activities.login;

import android.text.TextUtils;
import android.util.Patterns;

import com.paragon.sensonic.auth.PasswordLessLoginResponse;
import com.paragon.sensonic.auth.dto.PasswordLessLogin;
import com.paragon.sensonic.databinding.ActivityLoginBinding;
import com.paragon.utils.base.BaseViewModel;
import com.paragon.utils.networking.NetworkResponseCallback;

import java.util.HashMap;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public void init() {
        getNavigator().init();
    }

    public void onLoginButtonClick() {
        getNavigator().onLoginClick();
    }

    public void onMobileClick() {
        getNavigator().onMobileClick();
    }

    public void onEmailClick() {
        getNavigator().onEmailClick();
    }

    private boolean isValid(String value, String isMobile) {
        if (isMobile.matches("phone")) {
            if (TextUtils.isEmpty(value)) {
                return false;
            } else if (value.length() < 10) {
                return false;
            } else {
                return true;
            }
        } else {
            if (TextUtils.isEmpty(value.toString())) {
                return false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void callLoginApi(String value, String type) {
        if (isValid(value, type)) {
            getNavigator().onShowProgress();
            HashMap<String, String> data = new HashMap<>();
            data.put("type",type);
            data.put(type,value);


            new PasswordLessLoginResponse().doNetworkRequest(data, new NetworkResponseCallback<PasswordLessLogin>() {
                @Override
                public void onResponse(PasswordLessLogin object) {
                    getNavigator().onHideProgress();
                    getNavigator().onSuccess(object);
                }

                @Override
                public void onFailure(String error) {
                    getNavigator().onHideProgress();
                    getNavigator().onError(error);
                }

                @Override
                public void onInternetDisable() {

                }
            });
        }
    }
}
