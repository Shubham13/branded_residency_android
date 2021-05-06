package com.paragon.sensonic.ui.activities.otp

import android.content.Context
import android.os.CountDownTimer
import android.text.TextUtils
import com.paragon.sensonic.R
import com.paragon.sensonic.auth.PasswordLessLoginResponse
import com.paragon.sensonic.auth.VerifyOtpResponse
import com.paragon.sensonic.auth.dto.PasswordLessLogin
import com.paragon.sensonic.data.OtpVerify
import com.paragon.utils.base.BaseViewModel
import com.paragon.utils.local.AppPreference
import com.paragon.utils.local.PreferenceKeys
import com.paragon.utils.networking.NetworkResponseCallback


class OtpViewModel : BaseViewModel<OtpNavigator>() {

    fun init() {
        navigator.init()
    }

    fun onClickVerify() {
        navigator.onClickVerify()
    }

    fun onClickBack() {
        navigator.onClickBack()
    }

    fun onClickResend() {
        navigator.onClickResend()
    }

    private fun isValidOtp(context: Context, otpValue: String): Boolean {
        if (TextUtils.isEmpty(otpValue)) {
            navigator.setErrorText(true, context.getString(R.string.error_empty_otp))
            return false
        } else if(otpValue.length<6){
            navigator.setErrorText(true, context.getString(R.string.error_invalid_otp))
            return false
        }else{
            navigator.setErrorText(false, "")
            return true
        }
    }

    fun resendCodeTimer(context: Context) {
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                navigator.resendCodeIn30Sec(
                    context.getString(R.string.label_resend_code_in) + " " +
                            millisUntilFinished / 1000 + " " + context.getString(R.string.label_secounds)
                )
            }

            override fun onFinish() {
                navigator.resendCodeIn30Sec(context.getString(R.string.label_resend_code))
            }
        }.start()
    }


    fun callOtpVerify(context: Context, otp: String, appPreference: AppPreference,
                      type: String, value: String) {
        if (isValidOtp(context,otp)) {
            navigator.onShowProgress()

            var secretLoginCode: String = otp
            var data: HashMap<String, String> = HashMap()
            data.put("otp", secretLoginCode)
            data.put("type", type)
            data.put(type, value)
            data.put("session", appPreference.getValue(PreferenceKeys.SESSION))

            VerifyOtpResponse().doNetworkRequest(data, object : NetworkResponseCallback<OtpVerify> {
                override fun onResponse(data: OtpVerify?) {
                    navigator.onHideProgress()
                    navigator.onVerifyOtp(data)

                }

                override fun onFailure(error: String?) {
                    navigator.onHideProgress()
                    navigator.onError(error.toString())

                }

            })
        }
    }

    fun callResendApi(value: String, type: String) {
        navigator.onShowProgress()
        val data = java.util.HashMap<String, String>()
        data["type"] = type
        data[type] = value

        PasswordLessLoginResponse().doNetworkRequest(data,
            object : NetworkResponseCallback<PasswordLessLogin> {
                override fun onResponse(resendData: PasswordLessLogin) {
                    navigator.onHideProgress()
                    navigator.onResendOtp(resendData)
                }

                override fun onFailure(error: String) {
                    navigator.onHideProgress()
                    navigator.onError(error)
                }

                override fun onInternetDisable() {}
            })
    }
}