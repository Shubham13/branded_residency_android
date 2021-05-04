package com.paragon.sensonic.ui.activities.otp

import android.os.CountDownTimer
import android.text.TextUtils
import com.paragon.sensonic.auth.VerifyOtpResponse
import com.paragon.sensonic.data.OtpVerify
import com.paragon.sensonic.databinding.ActivityOtpBinding
import com.paragon.utils.base.BaseViewModel
import com.paragon.utils.local.AppPreference
import com.paragon.utils.local.PreferenceKeys
import com.paragon.utils.networking.NetworkResponseCallback


class OtpViewModel : BaseViewModel<OtpNavigator>() {

    fun init() {
        navigator.init()
        navigator.resendCodeIn30Sec()
    }

    fun onClickVerify() {
        navigator.onClickVerify()
    }

    fun onClickResend() {
        navigator.onClickResend()
    }

    private fun isValidOtp(mViewDataBinding: ActivityOtpBinding): Boolean {
        if (TextUtils.isEmpty(mViewDataBinding.otpEditOne.text.toString())) {
            return false
        } else if (TextUtils.isEmpty(mViewDataBinding.otpEditTwo.text.toString())) {
            return false
        } else if (TextUtils.isEmpty(mViewDataBinding.otpEditThree.text.toString())) {
            return false
        } else if (TextUtils.isEmpty(mViewDataBinding.otpEditFour.text.toString())) {
            return false
        } else if (TextUtils.isEmpty(mViewDataBinding.otpEditFive.text.toString())) {
            return false
        } else return !TextUtils.isEmpty(mViewDataBinding.otpEditSix.text.toString())
    }

    fun resendCodeTimer(mViewDataBinding: ActivityOtpBinding) {
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mViewDataBinding.resendCodeText.text =
                    "Resend Code in " + millisUntilFinished / 1000 + " sec..."
            }

            override fun onFinish() {
                mViewDataBinding.resendCodeText.text = "Resend Code"
            }
        }.start()
    }

    fun changeEditTextFocus(mViewDataBinding: ActivityOtpBinding) {
        mViewDataBinding.otpEditOne.let {
            if (it.length() == 1)
                mViewDataBinding.otpEditTwo.requestFocus()
        }

        mViewDataBinding.otpEditTwo.let {
            if (it.length() == 1)
                mViewDataBinding.otpEditThree.requestFocus()
        }

        mViewDataBinding.otpEditThree.let {
            if (it.length() == 1)
                mViewDataBinding.otpEditFour.requestFocus()
        }

        mViewDataBinding.otpEditFour.let {
            if (it.length() == 1)
                mViewDataBinding.otpEditFive.requestFocus()
        }

        mViewDataBinding.otpEditFive.let {
            if (it.length() == 1)
                mViewDataBinding.otpEditSix.requestFocus()
        }
    }

    fun callOtpVerify(mViewDataBinding: ActivityOtpBinding, appPreference: AppPreference, type: String, value: String) {
        if (isValidOtp(mViewDataBinding)) {
            navigator.onShowProgress()

            var secretLoginCode: String = mViewDataBinding.otpEditOne.text.toString() + "" +
                    mViewDataBinding.otpEditTwo.text.toString() + "" + mViewDataBinding.otpEditThree.text.toString() +
                    "" + mViewDataBinding.otpEditFour.text.toString() + "" + mViewDataBinding.otpEditFive.text.toString() +
                    "" + mViewDataBinding.otpEditSix.text.toString()

            var data: HashMap<String, String> = HashMap()
            data.put("otp",secretLoginCode)
            data.put("type",type)
            data.put(type,value)
            data.put("session",appPreference.getValue(PreferenceKeys.SESSION))

            VerifyOtpResponse().doNetworkRequest(data, object : NetworkResponseCallback<OtpVerify>{
                override fun onResponse(data: OtpVerify?) {
                    navigator.onHideProgress()
                    navigator.onSuccess(data)

                }

                override fun onFailure(error: String?) {
                    navigator.onHideProgress()
                    navigator.onError(error.toString())

                }

            })
        }
    }
}