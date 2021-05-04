package com.paragon.sensonic.ui.activities.login;

import android.text.TextUtils;
import android.util.Patterns;

import com.paragon.sensonic.auth.PasswordLessLoginResponse;
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

    private boolean isValid(ActivityLoginBinding mViewDataBinding, boolean isMobile) {
        if (isMobile) {
            if (TextUtils.isEmpty(mViewDataBinding.mobileEdit.toString())) {
                return false;
            } else if (mViewDataBinding.mobileEdit.length() < 10) {
                return false;
            } else {
                return true;
            }
        } else {
            if (TextUtils.isEmpty(mViewDataBinding.mobileEdit.toString())) {
                return false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(mViewDataBinding.mobileEdit.getText().toString()).matches()) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void callLoginApi(ActivityLoginBinding mViewDataBinding, boolean isMobile) {
        if (isValid(mViewDataBinding, isMobile)) {
            getNavigator().onShowProgress();
            HashMap<String, String> data = new HashMap<>();
            data.put("type","email");
            data.put("email","rupeshsaxena2015@gmail.com");


            new PasswordLessLoginResponse().doNetworkRequest(data, new NetworkResponseCallback() {
                @Override
                public void onResponse(Object object) {
                    getNavigator().onHideProgress();
                }

                @Override
                public void onFailure(String error) {
                    getNavigator().onHideProgress();
                }

                @Override
                public void onInternetDisable() {

                }
            });
        }
    }
}
