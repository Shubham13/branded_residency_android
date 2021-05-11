package com.paragon.sensonic.auth;

import android.util.Log;

import com.amazonaws.http.HttpMethodName;
import com.paragon.sensonic.App;
import com.paragon.sensonic.R;
import com.paragon.sensonic.auth.dto.OtpVerify;
import com.paragon.sensonic.auth.dto.OtpVerifySession;

import com.paragon.sensonic.utils.AppConstant;
import com.paragon.utils.GeneralFunctions;
import com.paragon.utils.base.BaseModel;
import com.paragon.sensonic.utils.local.AppPreference;
import com.paragon.sensonic.utils.local.PreferenceKeys;
import com.paragon.utils.networking.NetworkResponseCallback;
import com.paragon.utils.networking.NoConnectivityException;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class VerifyOtpResponse extends BaseModel<String, String> {

    @Override
    public void doNetworkRequest(HashMap<String, String> data, NetworkResponseCallback networkResponseCallback) {
        AwsInterceptor awsInterceptor;
        if (data.get("type").matches("email")) {

            awsInterceptor = new AwsInterceptor(AppConstant.BASE_URL, HttpMethodName.POST, EndPoints.VERIFY_CHALLANGE,
                    GeneralFunctions.serialize(new EmailOtpVerify(data.get("session"),
                            AppConstant.BRAND_ID,
                            data.get("otp"),
                            AppConstant.PROPERTY_ID,
                            data.get("email"),
                            AppConstant.SCOPE), EmailOtpVerify.class));
        } else {
            awsInterceptor = new AwsInterceptor(AppConstant.BASE_URL, HttpMethodName.POST, EndPoints.VERIFY_CHALLANGE,
                    GeneralFunctions.serialize(new MobileOtpVerify(data.get("session"),
                            AppConstant.BRAND_ID,
                            data.get("otp"),
                            AppConstant.PROPERTY_ID,
                            data.get("phone"),
                            AppConstant.SCOPE), MobileOtpVerify.class));
        }

        OkHttpClient.getOkHttpClient(awsInterceptor).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof NoConnectivityException) {
                    networkResponseCallback.onInternetDisable();
                }

                networkResponseCallback.onFailure(call.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                Log.e("response",body);
                OtpVerify otpVerify = GeneralFunctions.deserialize(body,OtpVerify.class);
                if(otpVerify.getCode()==1008){
                    OtpVerifySession otpVerifySession =
                            GeneralFunctions.deserialize(body,OtpVerifySession.class);
                    AppPreference.getInstance(App.getAppContext()).addValue(PreferenceKeys.SESSION,
                            otpVerifySession.getData().getSession());
                    networkResponseCallback.onFailure(App.getAppContext().getString(R.string.error_invalid_otp));
                }else{
                    ResponseHandler.handleResponse(response.code(), body, OtpVerify.class, networkResponseCallback);
                }
            }
        });
    }
}

