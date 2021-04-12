package com.digivalet.utils.networking;

import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Rupesh Saxena
 */

interface ApiInterface {

    @Headers("Content-Type: application/vnd.digivalet.v1+json")
    @POST("{requestMethod}")
    Single<Response<ResponseBody>> getResponse(@Path(value = "requestMethod", encoded = true) String requestMethod, @Body JsonObject object);


    @POST("{requestMethod}")
    Single<Response<ResponseBody>> getResponse(@Path(value = "requestMethod", encoded = true) String requestMethod, @QueryMap HashMap<String, String> map, @Body JsonObject object);

    @GET
    Single<Response<ResponseBody>> getResponse(@Url String url);

    @Multipart
    @POST("requestMethod")
    Single<Response<ResponseBody>> getResponse(@Path(value = "requestMethod", encoded = true) String requestMethod, @PartMap HashMap<String, RequestBody> partMap, @Part MultipartBody.Part file);
}
