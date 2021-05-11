package com.paragon.sensonic.auth

import android.util.Log
import com.amazonaws.http.HttpMethodName
import com.paragon.sensonic.auth.dto.PasswordLessLogin
import com.paragon.sensonic.auth.dto.RefreshCredential
import com.paragon.sensonic.utils.AppConstant
import com.paragon.utils.GeneralFunctions
import com.paragon.utils.base.BaseModel
import com.paragon.utils.networking.NetworkResponseCallback
import com.paragon.utils.networking.NoConnectivityException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.*

class RefreshTokenResponse : BaseModel<String,String>() {

    override fun doNetworkRequest(data: HashMap<String, String>,
                                  networkResponseCallback: NetworkResponseCallback<*>) {

        var awsInterceptor = AwsInterceptor(AppConstant.BASE_URL,HttpMethodName.POST,
            EndPoints.REFRESH_CREDENTIALS,
            GeneralFunctions.serialize(
                data["refreshToken"]?.let { RefreshCredentialMapper(it) },
                RefreshCredentialMapper::class.java))

        OkHttpClient.getOkHttpClient(awsInterceptor).enqueue(object : Callback{

            override fun onFailure(call: Call, e: IOException) {
                if (e is NoConnectivityException) {
                    networkResponseCallback.onInternetDisable()
                }
                Log.e("failure", call.toString())
                networkResponseCallback.onFailure(call.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()!!.string()
                Log.e("refresh response", body)
                ResponseHandler.handleResponse(response.code(), body,
                    RefreshCredential::class.java, networkResponseCallback
                )
            }

        })
    }
}