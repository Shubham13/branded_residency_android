package com.paragon.sensonic.ui.activities.otp

import android.os.Bundle
import android.util.Log
import android.view.View
import com.paragon.sensonic.R
import com.paragon.sensonic.auth.dto.Credentials
import com.paragon.sensonic.auth.dto.OtpVerify
import com.paragon.sensonic.auth.dto.PasswordLessLogin
import com.paragon.sensonic.auth.dto.User
import com.paragon.sensonic.databinding.ActivityOtpBinding
import com.paragon.sensonic.ui.activities.profile.ProfileActivity
import com.paragon.utils.GeneralFunctions
import com.paragon.utils.base.BaseActivity
import com.paragon.sensonic.utils.local.AppPreference
import com.paragon.sensonic.utils.local.PreferenceKeys

class OtpActivity : BaseActivity<ActivityOtpBinding, OtpViewModel>(), OtpNavigator {

    private val otpViewModel: OtpViewModel = getVM(OtpViewModel::class.java)
    private lateinit var appPreference: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        viewModel.init()
    }

    override fun getBindingVariable(): Int {
        return com.paragon.sensonic.BR.otpVM
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_otp
    }

    override fun getViewModel(): OtpViewModel {
        return otpViewModel
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
    }

    override fun init() {
        appPreference = AppPreference.getInstance(this)
        viewModel.resendCodeTimer()
    }

    override fun onClickBack() {
        onBackPressed()
    }

    override fun onClickVerify() {
        if (appPreference.getValue(PreferenceKeys.EMAIL).isNullOrEmpty())
            viewModel.callOtpVerify(
                mViewDataBinding.otpView.text.toString(),
                appPreference,
                "phone",
                appPreference.getValue(PreferenceKeys.MOBILE)
            )
        else
            viewModel.callOtpVerify(
                mViewDataBinding.otpView.text.toString(),
                appPreference,
                "email",
                appPreference.getValue(PreferenceKeys.EMAIL)
            )

    }

    override fun onClickResend() {
        if (!appPreference.getValue(PreferenceKeys.EMAIL).isNullOrEmpty()) {
            viewModel.callResendApi(
                appPreference.getValue(PreferenceKeys.EMAIL),
                "email"
            )
        } else {
            viewModel.callResendApi(
                appPreference.getValue(PreferenceKeys.MOBILE), "phone"
            )
        }
    }

    override fun resendCodeIn45Sec(s: String, enable : Boolean) {
        mViewDataBinding.resendCodeText.isEnabled = enable
        mViewDataBinding.resendCodeText.text = s
    }

    override fun onVerifyOtp(verify: OtpVerify?) {
        Log.e("response", verify?.data?.credentials.toString())
        appPreference.addValue(PreferenceKeys.CREDENTIALS, GeneralFunctions.serialize(verify?.data?.credentials,Credentials::class.java))
        appPreference.addValue(PreferenceKeys.USER, GeneralFunctions.serialize(verify?.data?.user,User::class.java))
        getActivityNavigator(this).startActClearTask(ProfileActivity::class.java)
    }

    override fun onResendOtp(response: PasswordLessLogin) {
        appPreference.addValue(PreferenceKeys.SESSION, response.data.Session)
        runOnUiThread { setErrorText(false, "") }
    }


    override fun onError(error: String) {
        Log.e("error", error)
        runOnUiThread { setErrorText(true, error) }
    }

    override fun setErrorText(show: Boolean, error: String) {
        if(show){
            mViewDataBinding.labelError.visibility = View.VISIBLE
            mViewDataBinding.labelError.text = error
        }else{
            mViewDataBinding.labelError.visibility = View.GONE
            mViewDataBinding.labelError.text = error
        }
    }

    override fun onShowProgress() {
        showProgressDialog()
    }

    override fun onHideProgress() {
        hideProgressDialog()
    }

}