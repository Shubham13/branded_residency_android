package com.paragon.sensonic.ui.activities.otp

import android.text.TextUtils
import com.amazonaws.http.HttpMethodName
import com.paragon.sensonic.data.LoginResponse
import com.paragon.sensonic.data.OtpRequest
import com.paragon.sensonic.data.OtpResponse
import com.paragon.sensonic.databinding.ActivityOtpBinding
import com.paragon.sensonic.network.AwsInterceptor
import com.paragon.sensonic.network.Constant
import com.paragon.sensonic.network.Constant.*
import com.paragon.sensonic.network.NetworkResponseCallback
import com.paragon.sensonic.network.OkhttpInstance
import com.paragon.utils.GeneralFunctions
import com.paragon.utils.base.BaseViewModel
import com.paragon.utils.local.AppPreference
import com.paragon.utils.local.PreferenceKeys

class OtpViewModel : BaseViewModel<OtpNavigator>() {

    fun init(){
        navigator.init()
    }

    fun onClickVerify(){
        navigator.onClickVerify()
    }

    private fun isValidOtp(mViewDataBinding: ActivityOtpBinding): Boolean{
        if(TextUtils.isEmpty(mViewDataBinding.otpEditOne.text.toString())){
            return false
        }else if(TextUtils.isEmpty(mViewDataBinding.otpEditTwo.text.toString())){
            return false
        }else if(TextUtils.isEmpty(mViewDataBinding.otpEditThree.text.toString())){
            return false
        }else if(TextUtils.isEmpty(mViewDataBinding.otpEditFour.text.toString())){
            return false
        }else if(TextUtils.isEmpty(mViewDataBinding.otpEditFive.text.toString())){
            return false
        }else return !TextUtils.isEmpty(mViewDataBinding.otpEditSix.text.toString())
    }

    fun callOtpApi(mViewDataBinding: ActivityOtpBinding, appPreference: AppPreference){
        if(isValidOtp(mViewDataBinding)){
            navigator.onShowProgress()
            var secretLoginCode : String = mViewDataBinding.otpEditOne.text.toString()+""+
                    mViewDataBinding.otpEditTwo.text.toString()+""+mViewDataBinding.otpEditThree.text.toString()+
                    ""+mViewDataBinding.otpEditFour.text.toString()+""+mViewDataBinding.otpEditFive.text.toString()+
                    ""+mViewDataBinding.otpEditSix.text.toString()
            val otpRequest = OtpRequest(appPreference.getValue(PreferenceKeys.SESSION),
                BRAND_ID,secretLoginCode, PROPERTY_ID,appPreference.getValue(PreferenceKeys.EMAIL), SCOPE)
            val awsInterceptor = AwsInterceptor(
                HttpMethodName.POST, OTP_METHOD,
                GeneralFunctions.serialize(otpRequest, OtpRequest::class.java)
            )
            OkhttpInstance.getOkhttpClient(awsInterceptor, object : NetworkResponseCallback {
                override fun onSuccess(response: String) {
                    navigator.onHideProgress()
                    navigator.onSuccess(GeneralFunctions.deserialize(response, OtpResponse::class.java))
                }

                override fun onFailure(error: String) {
                    navigator.onError(error)
                    navigator.onHideProgress()
                }
            })
        }
    }
}