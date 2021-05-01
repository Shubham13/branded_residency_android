package com.paragon.sensonic.ui.activities.login;

import android.text.TextUtils;
import android.util.Patterns;

import com.paragon.sensonic.databinding.ActivityLoginBinding;
import com.paragon.sensonic.ui.activities.dashboard.DashboardNavigator;
import com.paragon.utils.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public void init() {
        getNavigator().init();
    }

    public void onLoginButtonClick(){
        getNavigator().onLoginClick();
    }

    public void onMobileClick(){
        getNavigator().onMobileClick();
    }

    public void onEmailClick(){
        getNavigator().onEmailClick();
    }

    public boolean isValid(ActivityLoginBinding mViewDataBinding, boolean isMobile){
        if(isMobile) {
            if (TextUtils.isEmpty(mViewDataBinding.mobileEdit.toString())) {
                return false;
            } else if (mViewDataBinding.mobileEdit.length() < 10) {
                return false;
            } else {
                return true;
            }
        }else{
            if(TextUtils.isEmpty(mViewDataBinding.mobileEdit.toString())){
                return false;
            }else if(!Patterns.EMAIL_ADDRESS.matcher(mViewDataBinding.mobileEdit.getText().toString()).matches()){
                return false;
            }else{
                return true;
            }
        }
    }
}
