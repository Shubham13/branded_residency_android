package com.paragon.sensonic.network;

import com.paragon.utils.GeneralFunctions;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpInstance {

    private static OkHttpClient client = null;
    private static String BASE_URL = "https://api-qa.digivaletliving.com/auth/v1/";

    public static void getOkhttpClient(AwsInterceptor awsInterceptor, NetworkResponseCallback apiInterface){
        client = new OkHttpClient.Builder().addInterceptor(awsInterceptor).build();
        Request request = new Request.Builder()
                .url(BASE_URL+""+awsInterceptor.getMethod())
                .method("POST", awsInterceptor.getRequestBody())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                apiInterface.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                apiInterface.onSuccess(response.body().string());
            }
        });
    }

}
