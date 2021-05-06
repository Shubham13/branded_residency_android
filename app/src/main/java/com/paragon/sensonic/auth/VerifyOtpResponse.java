package com.paragon.sensonic.auth;

import com.amazonaws.http.HttpMethodName;
import com.paragon.sensonic.auth.dto.PasswordLessLogin;
import com.paragon.sensonic.data.OtpVerify;
import com.paragon.sensonic.utils.AppConstant;
import com.paragon.utils.GeneralFunctions;
import com.paragon.utils.base.BaseModel;
import com.paragon.utils.logger.Logger;
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
                ResponseHandler.handleResponse(response, OtpVerify.class, networkResponseCallback);
            }
        });
    }
}

