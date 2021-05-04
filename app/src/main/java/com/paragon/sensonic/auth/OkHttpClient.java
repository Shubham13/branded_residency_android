package com.paragon.sensonic.auth;

import okhttp3.Call;
import okhttp3.Request;

import static com.paragon.sensonic.utils.AppConstant.EMPTY;

class OkHttpClient {

    private static okhttp3.OkHttpClient client = null;

    public static Call getOkHttpClient(AwsInterceptor awsInterceptor) {
        client = new okhttp3.OkHttpClient.Builder().addInterceptor(awsInterceptor).build();
        Request request = new Request.Builder()
                .url(awsInterceptor.getBaseUrl() + EMPTY + awsInterceptor.getMethod())
                .method(awsInterceptor.getHttpMethodName(), awsInterceptor.getRequestBody())
                .build();

        return client.newCall(request);
    }
}