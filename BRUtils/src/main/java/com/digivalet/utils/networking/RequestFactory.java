package com.digivalet.utils.networking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rupesh Saxena
 */

class RequestFactory {
    private static String baseUrl;
    private static final String EMPTY = "";
    private static Retrofit retrofit = null;
    private static Retrofit retrofitWithHeader = null;
    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static long connectTimeout = 5, readTimeout = 5, writeTimeout = 5;

    public static Retrofit getClientWithoutHeader() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpClient.addInterceptor(interceptor).connectTimeout(connectTimeout, TimeUnit.SECONDS).
                readTimeout(readTimeout, TimeUnit.SECONDS).
                writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .build();
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientWithHeader(HashMap<String, String> requestHeader) {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.addInterceptor(chain -> {

            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder();

            if (requestHeader != null)
                requestBuilder = original.newBuilder();

            for(Map.Entry<String, String> entry : requestHeader.entrySet()){
                String key=entry.getKey();
                String val=entry.getValue();
                requestBuilder.header(key,val);
            }
            requestBuilder.method(original.method(), original.body());
            Request request = requestBuilder.build();

            return chain.proceed(request);
        });

        httpClientBuilder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(readTimeout, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(writeTimeout, TimeUnit.SECONDS);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(loggingInterceptor);

        List<Interceptor> list = httpClientBuilder.interceptors();

        for (int i = 0; i < list.size(); i++) {
            System.out.print("INTERCEPTOR FOR POSITION " + i + " " + list.get(i).getClass());
        }


            if (retrofitWithHeader == null) {

                retrofitWithHeader = new Retrofit.Builder()
                        .baseUrl(RequestFactory.baseUrl)
                        .client(httpClientBuilder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }


        return retrofitWithHeader;
    }


    public static void setBaseUrl(String baseUrl) {
        RequestFactory.baseUrl = baseUrl;
    }

    public static void setConnectTimeout(long connectTimeout) {
        RequestFactory.connectTimeout = connectTimeout;
    }

    public static void setReadTimeout(long readTimeout) {
        RequestFactory.readTimeout = readTimeout;
    }

    public static void setWriteTimeout(long writeTimeout) {
        RequestFactory.writeTimeout = writeTimeout;
    }
}
