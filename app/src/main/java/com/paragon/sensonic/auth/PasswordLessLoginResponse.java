package com.paragon.sensonic.auth;

import com.amazonaws.http.HttpMethodName;
import com.paragon.sensonic.utils.AppConstant;
import com.paragon.utils.GeneralFunctions;
import com.paragon.utils.base.BaseModel;
import com.paragon.utils.networking.NetworkResponseCallback;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Rupesh Saxena
 */

public class PasswordLessLoginResponse extends BaseModel<String, String> {

    @Override
    public void doNetworkRequest(HashMap<String, String> data, NetworkResponseCallback networkResponseCallback) {
        if (GeneralFunctions.isInternetAvailable()) {
            AwsInterceptor awsInterceptor;
            if (data.get("type").matches("email")) {
                awsInterceptor = new AwsInterceptor(AppConstant.BASE_URL, HttpMethodName.POST, EndPoints.PASSWORD_LESS_LOGIN,
                        GeneralFunctions.serialize(new EmailLoginMapper(AppConstant.SCOPE,
                                AppConstant.BRAND_ID,
                                AppConstant.PROPERTY_ID,
                                data.get("email")), EmailLoginMapper.class));
            } else {
                awsInterceptor = new AwsInterceptor(AppConstant.BASE_URL, HttpMethodName.POST, EndPoints.PASSWORD_LESS_LOGIN,
                        GeneralFunctions.serialize(new MobileLoginMapper(AppConstant.SCOPE,
                                AppConstant.BRAND_ID,
                                AppConstant.PROPERTY_ID,
                                data.get("phone")), MobileLoginMapper.class));
            }

            OkHttpClient.getOkHttpClient(awsInterceptor).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    networkResponseCallback.onFailure(call.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    switch (response.code()) {
                        case 401:
                        case 403:
                            networkResponseCallback.onFailure("Invalid Token");
                            break;

                        case 200:
                            break;
                    }
                }
            });

        } else {
            networkResponseCallback.onInternetDisable();
        }

    }
}

