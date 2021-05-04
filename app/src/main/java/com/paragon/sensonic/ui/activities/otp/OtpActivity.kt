package com.paragon.sensonic.ui.activities.otp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.paragon.sensonic.R
import com.paragon.sensonic.data.OtpResponse
import com.paragon.sensonic.databinding.ActivityOtpBinding
import com.paragon.sensonic.ui.activities.dashboard.DashboardActivity
import com.paragon.utils.base.BaseActivity
import com.paragon.utils.local.AppPreference
import com.paragon.utils.local.PreferenceKeys

class OtpActivity : BaseActivity<ActivityOtpBinding,OtpViewModel>(), OtpNavigator, TextWatcher {

    private val otpViewModel : OtpViewModel = getVM(OtpViewModel::class.java)
    private lateinit var appPreference : AppPreference

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

    override fun init() {
        appPreference = AppPreference.getInstance(this)
        mViewDataBinding.otpEditOne.addTextChangedListener(this)
        mViewDataBinding.otpEditTwo.addTextChangedListener(this)
        mViewDataBinding.otpEditThree.addTextChangedListener(this)
        mViewDataBinding.otpEditFour.addTextChangedListener(this)
        mViewDataBinding.otpEditFive.addTextChangedListener(this)
        mViewDataBinding.otpEditSix.addTextChangedListener(this)
    }

    override fun onClickVerify() {
       /* if(appPreference.getBoolean(PreferenceKeys.ISMOBILE)) {
            viewModel.callMobileOtpApi(mViewDataBinding, appPreference)
        }else{
            viewModel.callEmailOtpApi(mViewDataBinding, appPreference)
        }*/
    }

    override fun onClickResend() {
    }

    override fun resendCodeIn30Sec() {
        viewModel.resendCodeTimer(mViewDataBinding)
    }

    override fun onSuccess(response: OtpResponse?) {
        Log.e("response",response?.data?.credentials.toString())
        appPreference.addValue(PreferenceKeys.CREDENTIALS,response?.data?.credentials.toString())
        appPreference.addValue(PreferenceKeys.USER,response?.data?.user.toString())
        getActivityNavigator(this).startActClearTask(DashboardActivity::class.java)
    }

    override fun onError(error: String) {
        mViewDataBinding.labelError.text = error
        Log.e("error",error)
    }

    override fun onShowProgress() {
        showProgressDialog()
    }

    override fun onHideProgress() {
        hideProgressDialog()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(editable: Editable?) {
        viewModel.changeEditTextFocus(mViewDataBinding)
    }
}