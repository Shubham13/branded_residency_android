package com.digivalet.utils.networking;

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

public class BuildRequest{
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
    private Single<Response<ResponseBody>> call;

    public BuildRequest(String baseUrl, RequestType requestType, HashMap<String, String> parameters, @Nullable String postParamType, @Nullable MultipartBody.Part filePart) {
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
        BuildRequest.connectTimeout = connectTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        BuildRequest.readTimeout = readTimeout;
    }

    public void setWriteTimeout(long writeTimeout) {
        BuildRequest.writeTimeout = writeTimeout;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }


    public void setResponseCallback(NetworkResponseCallback<String> responseCallback) {
        this.responseCallback = responseCallback;
    }

    public Single<Response<ResponseBody>> build() {
        if (Utils.isInternetAvailable()) {
            if (isLoaderShown) {
                responseCallback.onShowProgress();
            }

            switch (requestType) {
                case GET:
                    return performGetCall();

                case POST:
                    return performPostCall();

                case MULTI_PART:
                    return performMultiPartCall();

                default:
                    return null;
            }
        } else {
            //TODO show no internet image_picker_dialog
        }

        return null;
    }


    private Single<Response<ResponseBody>> performPostCall() {
        ApiInterface apiFactory;


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
        return call;
    }


    private Single<Response<ResponseBody>> performGetCall() {
        ApiInterface apiFactory;

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

        return call;
    }

    private Single<Response<ResponseBody>> performMultiPartCall() {
        ApiInterface apiFactory;
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

        return call;
    }
}
