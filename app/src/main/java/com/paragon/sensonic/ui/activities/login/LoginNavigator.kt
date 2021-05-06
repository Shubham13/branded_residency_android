package com.paragon.sensonic.ui.activities.login

import com.paragon.sensonic.auth.dto.PasswordLessLogin

interface LoginNavigator {
    fun init()
    fun onLoginClick()
    fun onCountyCodeClick()
    fun onMobileClick()
    fun onEmailClick()
    fun onShowProgress()
    fun onHideProgress()
    fun setErrorText(b: Boolean, errorText: String)
    fun onSuccess(response: PasswordLessLogin)
    fun onError(error: String)
}