package com.paragon.sensonic.ui.activities.otp

import android.content.Intent
import android.os.Bundle
import com.paragon.sensonic.R
import com.paragon.sensonic.databinding.ActivityOtpBinding
import com.paragon.sensonic.ui.activities.dashboard.DashboardActivity
import com.paragon.utils.base.BaseActivity

class OtpActivity : BaseActivity<ActivityOtpBinding,OtpViewModel>(), OtpNavigator {

    private val otpViewModel : OtpViewModel = getVM(OtpViewModel::class.java)

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

    }

    override fun onClickVerify() {
        //if(viewModel.isValidOtp(mViewDataBinding)) {
            var intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        //}
    }
}