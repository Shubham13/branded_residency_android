package com.paragon.sensonic.ui.activities.login;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import com.airbnb.paris.Paris;
import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;
import com.paragon.sensonic.auth.dto.PasswordLessLogin;
import com.paragon.sensonic.databinding.ActivityLoginBinding;
import com.paragon.sensonic.ui.activities.otp.OtpActivity;
import com.paragon.sensonic.ui.views.countrypicker.Country;
import com.paragon.sensonic.ui.views.countrypicker.CountryPicker;
import com.paragon.sensonic.ui.views.countrypicker.listeners.OnCountryPickerListener;
import com.paragon.utils.GeneralFunctions;
import com.paragon.utils.base.BaseActivity;
import com.paragon.utils.local.AppPreference;
import com.paragon.utils.local.PreferenceKeys;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    private final LoginViewModel loginViewModel = getVM(LoginViewModel.class);
    private String type = "phone";
    private AppPreference appPreference;
    private Context context;

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
        context = this;
        getViewModel().init();
    }


    @Override
    public void init() {
        CountryPicker countryPicker = new CountryPicker();
        String countryDialCode = countryPicker.getCountryCodeFromSim(GeneralFunctions.getCountryMobileCode(this));
        mViewDataBinding.countryCodeText.setText(countryDialCode);
        appPreference = AppPreference.getInstance(this);
    }

    @Override
    public void onLoginClick() {
        if (!type.matches("phone")) {
            loginViewModel.callLoginApi(context,"", mViewDataBinding.mobileEdit.getText().toString(), type);
        } else {
            loginViewModel.callLoginApi(context, mViewDataBinding.countryCodeText.getText().toString(),
                    mViewDataBinding.mobileEdit.getText().toString(), type);
        }
    }

    @Override
    public void onCountyCodeClick() {
        CountryPicker.showPicker(this, true, new OnCountryPickerListener() {
            @Override
            public void onSelectCountry(Country country) {
                mViewDataBinding.countryCodeText.setText(country.getDialCode());
            }
        });
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
        type = "phone";
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
        type = "email";
        mViewDataBinding.mobileEdit.getText().clear();
    }

    @Override
    public void onShowProgress() {
        showProgressDialog();
    }

    @Override
    public void onHideProgress() {
        hideProgressDialog();
    }

    @Override
    public void setErrorText(boolean show, String errorText) {
        if(show) {
            mViewDataBinding.errorText.setVisibility(View.VISIBLE);
            mViewDataBinding.errorText.setText(errorText);
        }else{
            mViewDataBinding.errorText.setVisibility(View.GONE);
            mViewDataBinding.errorText.setText("");
        }
    }

    @Override
    public void onSuccess(PasswordLessLogin response) {
        appPreference.addValue(PreferenceKeys.SESSION, response.getData().getSession());
        if (type.matches("phone")) {
            appPreference.addValue(PreferenceKeys.MOBILE,
                    mViewDataBinding.countryCodeText.getText().toString() + "-" +
                            mViewDataBinding.mobileEdit.getText().toString());
        } else {
            appPreference.addValue(PreferenceKeys.EMAIL, mViewDataBinding.mobileEdit.getText().toString());
        }

        getActivityNavigator(this).startAct(OtpActivity.class);
    }

    @Override
    public void onError(String error) {
        Log.e("failure", error);
    }
}
