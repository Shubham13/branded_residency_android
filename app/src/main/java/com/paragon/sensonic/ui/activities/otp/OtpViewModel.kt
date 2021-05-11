package com.paragon.sensonic.ui.activities.otp

import android.os.CountDownTimer
import android.text.TextUtils
import com.paragon.sensonic.App
import com.paragon.sensonic.R
import com.paragon.sensonic.auth.PasswordLessLoginResponse
import com.paragon.sensonic.auth.RefreshTokenResponse
import com.paragon.sensonic.auth.VerifyOtpResponse
import com.paragon.sensonic.auth.dto.OtpVerify
import com.paragon.sensonic.auth.dto.PasswordLessLogin
import com.paragon.sensonic.auth.dto.RefreshCredential
import com.paragon.utils.base.BaseViewModel
import com.paragon.sensonic.utils.local.AppPreference
import com.paragon.sensonic.utils.local.PreferenceKeys
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

    private fun isValidOtp(otpValue: String): Boolean {
        if (TextUtils.isEmpty(otpValue)) {
            navigator.setErrorText(true, App.getAppContext().getString(R.string.error_empty_otp))
            return false
        } else if (otpValue.length < 6) {
            navigator.setErrorText(true, App.getAppContext().getString(R.string.error_invalid_otp))
            return false
        } else {
            navigator.setErrorText(false, "")
            return true
        }
    }

    fun resendCodeTimer() {
        object : CountDownTimer(45000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                navigator.resendCodeIn45Sec(
                    App.getAppContext().getString(R.string.label_resend_code_in) + " " +
                            millisUntilFinished / 1000 + " " + App.getAppContext()
                        .getString(R.string.label_secounds),
                    false
                )
            }

            override fun onFinish() {
                navigator.resendCodeIn45Sec(
                    App.getAppContext().getString(R.string.label_resend_code), true
                )
            }
        }.start()
    }


    fun callOtpVerify(otp: String, appPreference: AppPreference, type: String, value: String) {
        if (isValidOtp(otp)) {
            navigator.onShowProgress()
            val secretLoginCode: String = otp
            val data: HashMap<String, String> = HashMap()
            data["otp"] = secretLoginCode
            data["type"] = type
            data[type] = value
            data["session"] = appPreference.getValue(PreferenceKeys.SESSION)

            VerifyOtpResponse().doNetworkRequest(data, object : NetworkResponseCallback<OtpVerify> {
                override fun onResponse(data: OtpVerify) {
                    navigator.apply {
                        onHideProgress()
                        onVerifyOtp(data)
                    }
                }

                override fun onFailure(error: String) {
                    navigator.apply {
                        onHideProgress()
                        onError(error)
                    }
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
                    navigator.apply {
                        onHideProgress()
                        onResendOtp(resendData)
                    }
                }

                override fun onFailure(error: String) {
                    navigator.apply {
                        onHideProgress()
                        onError(error)
                    }
                }

                override fun onInternetDisable() {}
            })
    }

    fun callRefreshTokenApi(){
        navigator.onShowProgress()
        val refreshToken : String = AppPreference.getInstance(App.getAppContext())
            .credentials.refreshToken
        val data = HashMap<String,String>()
        data["refreshToken"] = refreshToken
        RefreshTokenResponse().doNetworkRequest(data,object : NetworkResponseCallback<RefreshCredential>{
            override fun onResponse(response : RefreshCredential?) {
                navigator.apply {
                    onHideProgress()
                    //onSuccess(response)
                }
            }

            override fun onFailure(error: String?) {
                navigator.onHideProgress()
            }

            override fun onInternetDisable() {

            }

        })
    }
}