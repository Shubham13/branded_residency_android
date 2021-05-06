package com.paragon.sensonic.ui.activities.login

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import com.airbnb.paris.Paris
import com.paragon.sensonic.BR
import com.paragon.sensonic.R
import com.paragon.sensonic.auth.dto.PasswordLessLogin
import com.paragon.sensonic.databinding.ActivityLoginBinding
import com.paragon.sensonic.ui.activities.otp.OtpActivity
import com.paragon.sensonic.ui.views.countrypicker.CountryPicker
import com.paragon.utils.GeneralFunctions
import com.paragon.utils.base.BaseActivity
import com.paragon.utils.local.AppPreference
import com.paragon.utils.local.PreferenceKeys

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel?>(), LoginNavigator {
    private val loginViewModel = getVM(LoginViewModel::class.java)
    private var type = "phone"
    private var appPreference: AppPreference? = null
    override fun getBindingVariable(): Int {
        return BR.loginVM
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModel(): LoginViewModel {
        return loginViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        viewModel.init()
    }

    override fun init() {
        val countryPicker = CountryPicker()
        val countryDialCode =
            countryPicker.getCountryCodeFromSim(GeneralFunctions.getCountryMobileCode(this))
        mViewDataBinding.countryCodeText.text = countryDialCode
        appPreference = AppPreference.getInstance(this)
    }

    override fun onLoginClick() {
        mViewDataBinding.errorText.visibility = View.GONE
        if (!type.equals("phone")) {
            loginViewModel.callLoginApi("", mViewDataBinding.mobileEdit.text.toString(), type)
        } else {
            loginViewModel.callLoginApi(
                mViewDataBinding.countryCodeText.text.toString(),
                mViewDataBinding.mobileEdit.text.toString(), type
            )
        }
    }

    override fun onCountyCodeClick() {
        CountryPicker.showPicker(this, true) { country ->
            mViewDataBinding.countryCodeText.text = country.dialCode
        }
    }

    override fun onMobileClick() {
        Paris.style(mViewDataBinding.loginMobile)
            .apply(R.style.barTopBannerPill)
        Paris.style(mViewDataBinding.loginMobile)
            .apply(R.style.subXSmallMedBrandLight)
        Paris.style(mViewDataBinding.loginEmail)
            .apply(R.style.barTabBarTransparent)
        Paris.style(mViewDataBinding.loginEmail)
            .apply(R.style.subXSmallMedBrandSecoundry)
        mViewDataBinding.countryCodeText.visibility = View.VISIBLE
        mViewDataBinding.mobileEdit.hint = getString(R.string.label_mobile_number)
        mViewDataBinding.mobileEdit.inputType = InputType.TYPE_CLASS_NUMBER
        type = "phone"
        mViewDataBinding.mobileEdit.text.clear()
        mViewDataBinding.errorText.visibility = View.GONE
    }

    override fun onEmailClick() {
        Paris.style(mViewDataBinding.loginEmail)
            .apply(R.style.barTopBannerPill)
        Paris.style(mViewDataBinding.loginEmail)
            .apply(R.style.subXSmallMedBrandLight)
        Paris.style(mViewDataBinding.loginMobile)
            .apply(R.style.barTabBarTransparent)
        Paris.style(mViewDataBinding.loginMobile)
            .apply(R.style.subXSmallMedBrandSecoundry)
        mViewDataBinding.countryCodeText.visibility = View.GONE
        mViewDataBinding.mobileEdit.hint = getString(R.string.label_email)
        mViewDataBinding.mobileEdit.inputType =
            InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        type = "email"
        mViewDataBinding.mobileEdit.text.clear()
        mViewDataBinding.errorText.visibility = View.GONE
    }

    override fun onShowProgress() {
        showProgressDialog()
    }

    override fun onHideProgress() {
        hideProgressDialog()
    }

    override fun setErrorText(show: Boolean, errorText: String) {
        if (show) {
            mViewDataBinding.errorText.visibility = View.VISIBLE
            mViewDataBinding.errorText.text = errorText
        } else {
            mViewDataBinding.errorText.visibility = View.GONE
            mViewDataBinding.errorText.text = ""
        }
    }

    override fun onSuccess(response: PasswordLessLogin) {
        Log.e("session", response.data.Session)
        appPreference!!.addValue(PreferenceKeys.SESSION, response.data.Session)
        if (type.equals("phone")) {
            appPreference!!.addValue(
                PreferenceKeys.MOBILE,
                mViewDataBinding.countryCodeText.text.toString() + "-" +
                        mViewDataBinding.mobileEdit.text.toString()
            )
        } else {
            appPreference!!.addValue(
                PreferenceKeys.EMAIL,
                mViewDataBinding.mobileEdit.text.toString()
            )
        }
        getActivityNavigator(this).startAct(OtpActivity::class.java)
    }

    override fun onError(error: String) {
        Log.e("failure", error)
        runOnUiThread { setErrorText(true, error) }
    }
}