package com.paragon.sensonic.ui.activities.login;

import android.text.TextUtils;
import android.util.Patterns;

import com.amazonaws.http.HttpMethodName;
import com.paragon.sensonic.data.Login;
import com.paragon.sensonic.data.LoginResponse;
import com.paragon.sensonic.databinding.ActivityLoginBinding;
import com.paragon.sensonic.network.NetworkResponseCallback;
import com.paragon.sensonic.network.AwsInterceptor;
import com.paragon.sensonic.network.OkhttpInstance;
import com.paragon.utils.GeneralFunctions;
import com.paragon.utils.base.BaseViewModel;
import com.paragon.utils.local.AppPreference;
import com.paragon.utils.local.PreferenceKeys;

import static com.paragon.sensonic.network.Constant.BRAND_ID;
import static com.paragon.sensonic.network.Constant.LOGIN_METHOD;
import static com.paragon.sensonic.network.Constant.PROPERTY_ID;
import static com.paragon.sensonic.network.Constant.SCOPE;

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
            Login loginData = new Login(SCOPE, BRAND_ID, PROPERTY_ID, mViewDataBinding.mobileEdit.getText().toString());
            AwsInterceptor awsInterceptor = new AwsInterceptor(HttpMethodName.POST, LOGIN_METHOD,
                    GeneralFunctions.serialize(loginData, Login.class));
            OkhttpInstance.getOkhttpClient(awsInterceptor, new NetworkResponseCallback() {
                @Override
                public void onSuccess(String response) {
                    getNavigator().onHideProgress();
                    getNavigator().onSuccess(GeneralFunctions.deserialize(response,LoginResponse.class));
                }

                @Override
                public void onFailure(String error) {
                    getNavigator().onHideProgress();
                    getNavigator().onError(error);
                }
            });
        }
    }
}
