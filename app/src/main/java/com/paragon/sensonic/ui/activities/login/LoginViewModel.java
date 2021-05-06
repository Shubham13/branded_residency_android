package com.paragon.sensonic.ui.activities.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.paragon.sensonic.R;
import com.paragon.sensonic.auth.PasswordLessLoginResponse;
import com.paragon.sensonic.auth.dto.PasswordLessLogin;
import com.paragon.utils.GeneralFunctions;
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

    public void onCountyCodeClick(){
        getNavigator().onCountyCodeClick();
    }

    public void onMobileClick() {
        getNavigator().onMobileClick();
    }

    public void onEmailClick() {
        getNavigator().onEmailClick();
    }

    private boolean isValid(Context context, String code, String value, String isMobile) {
        if (isMobile.matches("phone")) {
            if (TextUtils.isEmpty(value)) {
                getNavigator().setErrorText(true, context.getString(R.string.error_empty_mobile_number));
                return false;
            } else if (!GeneralFunctions.isValidPhoneNumber(value)) {
                getNavigator().setErrorText(true, context.getString(R.string.error_invalid_mobile_number));
                return false;
            } else if(!GeneralFunctions.validateUsing_libphonenumber(code,value)){
                getNavigator().setErrorText(true, context.getString(R.string.error_invalid_mobile_number));
                return false;
            }
            else {
                getNavigator().setErrorText(false, "");
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

    public void callLoginApi(Context context, String code, String value, String type) {
        if (isValid(context, code, value, type)) {
            getNavigator().onShowProgress();
            HashMap<String, String> data = new HashMap<>();
            data.put("type",type);
            if(TextUtils.isEmpty(code)) {
                data.put(type, value);
            }else{
                data.put(type, code+"-"+value);
            }

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
