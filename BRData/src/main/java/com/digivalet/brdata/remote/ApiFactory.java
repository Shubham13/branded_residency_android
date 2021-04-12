package com.digivalet.brdata.remote;

import com.digivalet.utils.logger.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Rupesh Saxena
 */

class ApiFactory {

    private static Retrofit retrofit = null;
    private static Retrofit retrofitWithHeader = null;
    private static final String EMPTY = "";

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit getClientWithoutHeader(String url) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpClient.addInterceptor(interceptor).connectTimeout(5, TimeUnit.MINUTES).
                readTimeout(5, TimeUnit.MINUTES).
                writeTimeout(5, TimeUnit.MINUTES)
                .build();
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofitClientWithHeader(String access_token, String url) {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.addInterceptor(chain -> {

            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder();

            if (!access_token.matches(EMPTY))
            requestBuilder.addHeader("access-token", access_token);
            requestBuilder.addHeader("accept", "application/json");
            requestBuilder.addHeader("Content-Type", "application/json");
            Request request = requestBuilder.build();

            return chain.proceed(request);
        });

        httpClientBuilder.connectTimeout(5, TimeUnit.MINUTES);
        httpClientBuilder.readTimeout(5, TimeUnit.MINUTES);
        httpClientBuilder.writeTimeout(5, TimeUnit.MINUTES);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(loggingInterceptor);

        List<Interceptor> list = httpClientBuilder.interceptors();

        for (int i = 0; i < list.size(); i++) {
            Logger.e(ApiFactory.class.getName(), "INTERCEPTOR FOR POSITION " + i + " " + list.get(i).getClass());
        }

        if (retrofitWithHeader == null) {

            retrofitWithHeader = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitWithHeader;
    }
}
