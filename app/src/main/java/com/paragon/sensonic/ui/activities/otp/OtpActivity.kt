package com.paragon.sensonic.ui.activities.otp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.paragon.sensonic.R
import com.paragon.sensonic.data.LoginResponse
import com.paragon.sensonic.data.OtpResponse
import com.paragon.sensonic.databinding.ActivityOtpBinding
import com.paragon.sensonic.ui.activities.dashboard.DashboardActivity
import com.paragon.utils.base.BaseActivity
import com.paragon.utils.local.AppPreference
import com.paragon.utils.local.PreferenceKeys

class OtpActivity : BaseActivity<ActivityOtpBinding,OtpViewModel>(), OtpNavigator {

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
    }

    override fun onClickVerify() {
        viewModel.callOtpApi(mViewDataBinding,appPreference)
    }

    override fun onSuccess(response: OtpResponse?) {
        Log.e("response",response?.data?.credentials.toString())
        appPreference.addValue(PreferenceKeys.CREDENTIALS,response?.data?.credentials.toString())
        appPreference.addValue(PreferenceKeys.USER,response?.data?.user.toString())
        getActivityNavigator(this).startAct(DashboardActivity::class.java)
    }

    override fun onError(error: String) {
        Log.e("error",error)
    }

    override fun onShowProgress() {
        showProgressDialog()
    }

    override fun onHideProgress() {
        hideProgressDialog()
    }
}