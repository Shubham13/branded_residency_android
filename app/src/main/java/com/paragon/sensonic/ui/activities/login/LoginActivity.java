package com.paragon.sensonic.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.airbnb.paris.Paris;


import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.ActivityLoginBinding;
import com.paragon.sensonic.ui.activities.otp.OtpActivity;
import com.paragon.sensonic.ui.views.countrypicker.CountryPicker;
import com.paragon.utils.GeneralFunctions;
import com.paragon.utils.base.BaseActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    private final LoginViewModel loginViewModel = getVM(LoginViewModel.class);
    private boolean isMobile = true;

    @Override
    public int getBindingVariable() {
        return BR.loginVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        return loginViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().setNavigator(this);
        getViewModel().init();
    }


    @Override
    public void init() {
        CountryPicker countryPicker = new CountryPicker();
        String countryDialCode = countryPicker.getCountryCodeFromSim(GeneralFunctions.getCountryMobileCode(this));
        mViewDataBinding.countryCodeText.setText(countryDialCode);
    }

    @Override
    public void onLoginClick() {
        //if(getViewModel().isValid(mViewDataBinding,isMobile)) {
            Intent intent = new Intent(this, OtpActivity.class);
            startActivity(intent);
        //}
    }

    @Override
    public void onMobileClick() {
        Paris.style(mViewDataBinding.loginMobile)
                .apply(R.style.barTopBannerPill);
        Paris.style(mViewDataBinding.loginMobile)
                .apply(R.style.subXSmallMedBrandLight);

        Paris.style(mViewDataBinding.loginEmail)
                .apply(R.style.barTabBarTransparent);
        Paris.style(mViewDataBinding.loginEmail)
                .apply(R.style.subXSmallMedBrandSecoundry);

        mViewDataBinding.countryCodeText.setVisibility(View.VISIBLE);
        mViewDataBinding.mobileEdit.setHint(getString(R.string.label_mobile_number));
        mViewDataBinding.mobileEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        isMobile = true;
        mViewDataBinding.mobileEdit.getText().clear();
    }

    @Override
    public void onEmailClick() {
        Paris.style(mViewDataBinding.loginEmail)
                .apply(R.style.barTopBannerPill);
        Paris.style(mViewDataBinding.loginEmail)
                .apply(R.style.subXSmallMedBrandLight);

        Paris.style(mViewDataBinding.loginMobile)
                .apply(R.style.barTabBarTransparent);
        Paris.style(mViewDataBinding.loginMobile)
                .apply(R.style.subXSmallMedBrandSecoundry);

        mViewDataBinding.countryCodeText.setVisibility(View.GONE);
        mViewDataBinding.mobileEdit.setHint(getString(R.string.label_email));
        mViewDataBinding.mobileEdit.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        isMobile = false;
        mViewDataBinding.mobileEdit.getText().clear();
    }
}
