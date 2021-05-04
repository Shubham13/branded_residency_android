package com.paragon.sensonic.ui.activities.otp

import com.paragon.sensonic.data.OtpVerify

interface OtpNavigator {
    fun init()
    fun onClickVerify()
    fun onClickResend()
    fun resendCodeIn30Sec()
    fun onSuccess(verify: OtpVerify?)
    fun onError(error: String)
    fun onShowProgress()
    fun onHideProgress()}