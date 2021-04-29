package com.paragon.utils.networking;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Rupesh Saxena
 */

public class SendRequest {
    private boolean isLoaderShown = false;
    private final String baseUrl;
    private String requestMethod;
    private HashMap<String, String> requestHeader;
    private final HashMap<String, String> parameters;
    private String postParamType = null;
    private final RequestType requestType;
    private final MultipartBody.Part filePart;
    private NetworkResponseCallback<String> responseCallback;
    private static long connectTimeout = 5, readTimeout = 5, writeTimeout = 5;
    private final CompositeDisposable compositeDisposable;

    public SendRequest(String baseUrl, RequestType requestType, HashMap<String, String> parameters, @Nullable String postParamType, @Nullable MultipartBody.Part filePart) {
        this.baseUrl = baseUrl;
        this.requestType = requestType;
        this.parameters = parameters;
        this.postParamType = postParamType;
        this.filePart = filePart;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void setLoaderConfig(boolean isLoaderShown) {
        this.isLoaderShown = isLoaderShown;
    }

    public HashMap<String, String> getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(HashMap<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public void setConnectTimeout(long connectTimeout) {
        SendRequest.connectTimeout = connectTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        SendRequest.readTimeout = readTimeout;
    }

    public void setWriteTimeout(long writeTimeout) {
        SendRequest.writeTimeout = writeTimeout;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }


    public void setResponseCallback(NetworkResponseCallback<String> responseCallback) {
        this.responseCallback = responseCallback;
    }

    public void execute() {
        if (Utils.isInternetAvailable()) {
            if (isLoaderShown) {
                responseCallback.onShowProgress();
            }

            switch (requestType) {
                case GET:
                    performGetCall();
                    break;
                case POST:
                    performPostCall();
                    break;
                case MULTI_PART:
                    performMultiPartCall();
                    break;
            }
        } else {
            responseCallback.onInternetDisable();
        }
    }


    private void performPostCall() {
        ApiInterface apiFactory;
        Single<Response<ResponseBody>> call;

        RequestFactory.setBaseUrl(baseUrl);
        RequestFactory.setConnectTimeout(connectTimeout);
        RequestFactory.setReadTimeout(readTimeout);
        RequestFactory.setWriteTimeout(writeTimeout);

        if (requestHeader != null) {
            apiFactory = RequestFactory.getClientWithHeader(requestHeader).create(ApiInterface.class);
        } else {
            apiFactory = RequestFactory.getClientWithoutHeader().create(ApiInterface.class);
        }

        if (postParamType != null && !TextUtils.isEmpty(postParamType)) {
            //TODO Add support for parsing json object
            call = apiFactory.getResponse(requestMethod, parameters, null);
        } else {
            call = apiFactory.getResponse(requestMethod, new Gson().toJsonTree(parameters).getAsJsonObject());
        }

        compositeDisposable.add(call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<ResponseBody>>() {
                    @Override
                    public void onSuccess(@Nullable Response<ResponseBody> response) {
                        handelResponse(response);
                        compositeDisposable.dispose();
                    }

                    @Override
                    public void onError(@Nullable Throwable e) {
                        responseCallback.onFailure(e.getMessage());
                    }
                }));
    }


    private void performGetCall() {
        ApiInterface apiFactory;
        Single<Response<ResponseBody>> call;

        RequestFactory.setBaseUrl(baseUrl);
        RequestFactory.setConnectTimeout(connectTimeout);
        RequestFactory.setReadTimeout(readTimeout);
        RequestFactory.setWriteTimeout(writeTimeout);

        if (requestHeader != null) {
            apiFactory = RequestFactory.getClientWithHeader(requestHeader).create(ApiInterface.class);
        } else {
            apiFactory = RequestFactory.getClientWithoutHeader().create(ApiInterface.class);
        }


        if (parameters != null) {
            call = apiFactory.getResponse(baseUrl + requestMethod + "?" + Utils.urlEncodeUTF8(parameters));
        } else {
            call = apiFactory.getResponse(baseUrl + requestMethod);
        }

        compositeDisposable.add(call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<ResponseBody>>() {
                    @Override
                    public void onSuccess(@Nullable Response<ResponseBody> response) {
                        handelResponse(response);
                        compositeDisposable.dispose();
                    }

                    @Override
                    public void onError(@Nullable Throwable e) {
                        responseCallback.onFailure(e.getMessage());
                    }
                }));
    }

    private void performMultiPartCall() {
        ApiInterface apiFactory;
        Single<Response<ResponseBody>> call;
        HashMap<String, RequestBody> requestBodyHashMap = new HashMap<>();

        RequestFactory.setBaseUrl(baseUrl);
        RequestFactory.setConnectTimeout(connectTimeout);
        RequestFactory.setReadTimeout(readTimeout);
        RequestFactory.setWriteTimeout(writeTimeout);

        if (requestHeader != null) {
            apiFactory = RequestFactory.getClientWithHeader(requestHeader).create(ApiInterface.class);
        } else {
            apiFactory = RequestFactory.getClientWithoutHeader().create(ApiInterface.class);
        }

        /*create a map of brdata to pass along*/
        for (Map.Entry<String, String> requestMap : parameters.entrySet()) {
            String key = requestMap.getKey();
            String val = requestMap.getValue();
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), val);
            requestBodyHashMap.put(key, requestBody);
        }

        call = apiFactory.getResponse(requestMethod, requestBodyHashMap, filePart);
        compositeDisposable.add(call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<ResponseBody>>() {
                    @Override
                    public void onSuccess(@Nullable Response<ResponseBody> response) {
                        handelResponse(response);
                        compositeDisposable.dispose();
                    }

                    @Override
                    public void onError(@Nullable Throwable e) {
                        responseCallback.onFailure(e.getMessage());
                    }
                }));
    }

    private void handelResponse(Response<ResponseBody> response) {
        if (isLoaderShown) {
            responseCallback.onHideProgress();
        }

        String output = "";
        try {
            if (response != null && response.isSuccessful() && response.code() == 200) {
                output = Utils.getResponseBody(response);
                responseCallback.onResponse(output);
            } else if (response != null) {
                if (response.code() == 401) {
                    responseCallback.onSessionExpire();
                } else if (response.code() == 400 || response.code() == 500 || response.code() == 403 || response.code() == 404) {
                    responseCallback.onFailure(response.message());
                } else {
                    String responseStr = Utils.getResponseBody(response);
                    JSONObject jsonObject = new JSONObject(responseStr);
                    responseCallback.onFailure(Utils.checkJsonErrorBody(jsonObject));
                }
            }
        } catch (Exception e) {
            responseCallback.onFailure(e.getMessage());
        }
    }

}
