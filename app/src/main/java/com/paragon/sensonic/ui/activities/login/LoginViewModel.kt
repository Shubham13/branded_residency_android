package com.paragon.sensonic.ui.activities.login

import android.text.TextUtils
import android.util.Patterns
import com.paragon.sensonic.App
import com.paragon.sensonic.R
import com.paragon.sensonic.auth.PasswordLessLoginResponse
import com.paragon.sensonic.auth.RefreshTokenResponse
import com.paragon.sensonic.auth.dto.Credentials
import com.paragon.sensonic.auth.dto.PasswordLessLogin
import com.paragon.utils.GeneralFunctions
import com.paragon.utils.base.BaseViewModel
import com.paragon.sensonic.utils.local.AppPreference
import com.paragon.sensonic.utils.local.PreferenceKeys
import com.paragon.utils.networking.NetworkResponseCallback
import kotlin.collections.HashMap

class LoginViewModel : BaseViewModel<LoginNavigator>() {

    fun init() {
        navigator.init()
    }

    fun onLoginButtonClick() {
        navigator.onLoginClick()
    }

    fun onCountyCodeClick() {
        navigator.onCountyCodeClick()
    }

    fun onMobileClick() {
        navigator.onMobileClick()
    }

    fun onEmailClick() {
        navigator.onEmailClick()
    }

    private fun isValid(code: String, value: String, isMobile: String): Boolean {
        return if (isMobile.equals("phone")) {
            if (TextUtils.isEmpty(value)) {
                navigator.setErrorText(
                    true,
                    App.getAppContext().getString(R.string.error_empty_mobile)
                )
                false
            } else if (!GeneralFunctions.isValidPhoneNumber(value)) {
                navigator.setErrorText(
                    true,
                    App.getAppContext().getString(R.string.error_incorrect_mobile)
                )
                false
            } else if (!GeneralFunctions.validateUsing_libphonenumber(code, value)) {
                navigator.setErrorText(
                    true,
                    App.getAppContext().getString(R.string.error_incorrect_mobile)
                )
                false
            } else {
                navigator.setErrorText(false, "")
                true
            }
        } else {
            if (TextUtils.isEmpty(value)) {
                navigator.setErrorText(
                    true,
                    App.getAppContext().getString(R.string.error_empty_email)
                )
                false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                navigator.setErrorText(
                    true,
                    App.getAppContext().getString(R.string.error_incorrect)
                )
                false
            } else {
                navigator.setErrorText(false, "")
                true
            }
        }
    }

    fun callLoginApi(code: String, value: String, type: String) {
        if (isValid(code, value, type)) {
            navigator.onShowProgress()
            val data = HashMap<String, String>()
            data["type"] = type
            if (TextUtils.isEmpty(code)) {
                data[type] = value
            } else {
                data[type] = "$code-$value"
            }
            PasswordLessLoginResponse().doNetworkRequest(
                data, object : NetworkResponseCallback<PasswordLessLogin> {

                    override fun onResponse(response: PasswordLessLogin) {
                        navigator.apply {
                            onHideProgress()
                            onSuccess(response)
                        }
                    }

                    override fun onFailure(error: String) {
                        navigator.apply {
                            onHideProgress()
                            if (error == App.getAppContext().getString(R.string.invalid_user_name)) {
                                if (type == "email") {
                                    onError(App.getAppContext().getString(R.string.error_invalid_email))
                                } else {
                                    onError(App.getAppContext().getString(R.string.error_not_register_mobile))
                                }
                            }
                        }
                    }

                    override fun onInternetDisable() {}

                })
        }
    }


}