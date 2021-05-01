package com.paragon.sensonic.ui.activities.otp

import android.text.TextUtils
import com.paragon.sensonic.databinding.ActivityOtpBinding
import com.paragon.utils.base.BaseViewModel

class OtpViewModel : BaseViewModel<OtpNavigator>() {

    fun init(){
        navigator.init()
    }

    fun onClickVerify(){
        navigator.onClickVerify()
    }

    fun isValidOtp(mViewDataBinding: ActivityOtpBinding): Boolean{
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
        }else if(TextUtils.isEmpty(mViewDataBinding.otpEditSix.text.toString())){
            return false
        }else{
            return true
        }
    }
}