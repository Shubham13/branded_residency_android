package com.paragon.sensonic.ui.activities.otp

import com.paragon.sensonic.auth.dto.PasswordLessLogin
import com.paragon.sensonic.data.OtpVerify

interface OtpNavigator {
    fun init()
    fun onClickBack()
    fun onClickVerify()
    fun onClickResend()
    fun resendCodeIn45Sec(s: String,enable : Boolean)
    fun onVerifyOtp(verify: OtpVerify?)
    fun onResendOtp(resend : PasswordLessLogin)
    fun onError(error: String)
    fun setErrorText(show : Boolean, error: String)
    fun onShowProgress()
    fun onHideProgress()}