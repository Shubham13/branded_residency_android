package com.paragon.sensonic.ui.activities.otp

import com.paragon.sensonic.data.OtpResponse

interface OtpNavigator {
    fun init()
    fun onClickVerify()
    fun onSuccess(response: OtpResponse?)
    fun onError(error: String)
    fun onShowProgress()
    fun onHideProgress()}