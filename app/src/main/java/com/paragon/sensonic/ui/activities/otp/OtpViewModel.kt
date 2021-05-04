package com.paragon.sensonic.ui.activities.otp

import android.os.CountDownTimer
import android.text.TextUtils
import com.amazonaws.http.HttpMethodName
import com.paragon.sensonic.data.OtpMobileRequest
import com.paragon.sensonic.data.OtpRequest
import com.paragon.sensonic.data.OtpResponse
import com.paragon.sensonic.databinding.ActivityOtpBinding
import com.paragon.utils.GeneralFunctions
import com.paragon.utils.base.BaseViewModel
import com.paragon.utils.local.AppPreference
import com.paragon.utils.local.PreferenceKeys


class OtpViewModel : BaseViewModel<OtpNavigator>() {

    fun init(){
        navigator.init()
        navigator.resendCodeIn30Sec()
    }

    fun onClickVerify(){
        navigator.onClickVerify()
    }

    fun onClickResend(){
        navigator.onClickResend()
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

    fun resendCodeTimer(mViewDataBinding: ActivityOtpBinding){
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mViewDataBinding.resendCodeText.text = "Resend Code in " + millisUntilFinished / 1000 +" sec..."
            }

            override fun onFinish() {
                mViewDataBinding.resendCodeText.text = "Resend Code"
            }
        }.start()
    }

    fun changeEditTextFocus(mViewDataBinding: ActivityOtpBinding){
        mViewDataBinding.otpEditOne.let {
            if(it.length()==1)
                mViewDataBinding.otpEditTwo.requestFocus()
        }

        mViewDataBinding.otpEditTwo.let {
            if(it.length()==1)
                mViewDataBinding.otpEditThree.requestFocus()
        }

        mViewDataBinding.otpEditThree.let {
            if(it.length()==1)
                mViewDataBinding.otpEditFour.requestFocus()
        }

        mViewDataBinding.otpEditFour.let {
            if(it.length()==1)
                mViewDataBinding.otpEditFive.requestFocus()
        }

        mViewDataBinding.otpEditFive.let {
            if(it.length()==1)
                mViewDataBinding.otpEditSix.requestFocus()
        }
    }

    /*fun callEmailOtpApi(mViewDataBinding: ActivityOtpBinding, appPreference: AppPreference){
        if(isValidOtp(mViewDataBinding)){
            navigator.onShowProgress()
            var secretLoginCode : String = mViewDataBinding.otpEditOne.text.toString()+""+
                    mViewDataBinding.otpEditTwo.text.toString()+""+mViewDataBinding.otpEditThree.text.toString()+
                    ""+mViewDataBinding.otpEditFour.text.toString()+""+mViewDataBinding.otpEditFive.text.toString()+
                    ""+mViewDataBinding.otpEditSix.text.toString()
            val otpRequest = OtpRequest(
                appPreference.getValue(PreferenceKeys.SESSION),
                BRAND_ID,
                secretLoginCode,
                PROPERTY_ID,
                appPreference.getValue(PreferenceKeys.EMAIL),
                SCOPE
            )
            val awsInterceptor = AwsInterceptor(
                HttpMethodName.POST, OTP_METHOD,
                GeneralFunctions.serialize(otpRequest, OtpRequest::class.java)
            )
            OkhttpInstance.getOkhttpClient(awsInterceptor, object {
                override fun onSuccess(response: String) {
                    navigator.onHideProgress()
                    navigator.onSuccess(
                        GeneralFunctions.deserialize(
                            response,
                            OtpResponse::class.java
                        )
                    )
                }

                override fun onFailure(error: String) {
                    navigator.onError(error)
                    navigator.onHideProgress()
                }
            })
        }
    }

    fun callMobileOtpApi(mViewDataBinding: ActivityOtpBinding, appPreference: AppPreference){
        if(isValidOtp(mViewDataBinding)){
            navigator.onShowProgress()
            var secretLoginCode : String = mViewDataBinding.otpEditOne.text.toString()+""+
                    mViewDataBinding.otpEditTwo.text.toString()+""+mViewDataBinding.otpEditThree.text.toString()+
                    ""+mViewDataBinding.otpEditFour.text.toString()+""+mViewDataBinding.otpEditFive.text.toString()+
                    ""+mViewDataBinding.otpEditSix.text.toString()
            val otpRequest = OtpMobileRequest(
                appPreference.getValue(PreferenceKeys.SESSION),
                BRAND_ID,
                secretLoginCode,
                PROPERTY_ID,
                appPreference.getValue(PreferenceKeys.MOBILE),
                SCOPE
            )
            val awsInterceptor = AwsInterceptor(
                HttpMethodName.POST, OTP_METHOD,
                GeneralFunctions.serialize(otpRequest, OtpMobileRequest::class.java)
            )
            OkhttpInstance.getOkhttpClient(awsInterceptor, object {
                override fun onSuccess(response: String) {
                    navigator.onHideProgress()
                    navigator.onSuccess(
                        GeneralFunctions.deserialize(
                            response,
                            OtpResponse::class.java
                        )
                    )
                }

                override fun onFailure(error: String) {
                    navigator.onError(error)
                    navigator.onHideProgress()
                }
            })
        }
    }*/
}