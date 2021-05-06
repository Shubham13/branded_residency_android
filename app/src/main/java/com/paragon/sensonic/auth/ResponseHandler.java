package com.paragon.sensonic.auth;

import com.paragon.sensonic.App;
import com.paragon.sensonic.R;
import com.paragon.utils.GeneralFunctions;
import com.paragon.utils.networking.NetworkResponseCallback;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class ResponseHandler {

    public static void handleResponse(int code, String response, Class<?> mapper, NetworkResponseCallback networkResponseCallback) throws IOException {
        switch (code) {
            case 200:
                networkResponseCallback.onResponse(GeneralFunctions.deserialize(response, mapper));
                break;
            case 401:
            case 403:
                networkResponseCallback.onSessionExpire();
                break;
            case 400:
            case 404:
            case 500:
                handelResponseCode(getStatusCode(response),networkResponseCallback);
                break;
            default:
                networkResponseCallback.onFailure(App.getAppContext().getString(R.string.unknown));
        }
    }

    private static void handelResponseCode(int code, NetworkResponseCallback networkResponseCallback){
        switch (code) {
            case 1000:
                networkResponseCallback.onFailure(App.getAppContext().getString(R.string.something_went_wrong));
                break;
            case 1001:
                networkResponseCallback.onFailure(App.getAppContext().getString(R.string.invalid_parameters));
                break;
            case 1002:
                networkResponseCallback.onFailure(App.getAppContext().getString(R.string.invalid_user_name));
                break;
            case 1006:
                networkResponseCallback.onFailure(App.getAppContext().getString(R.string.user_not_active));
                break;
            case 1005:
                networkResponseCallback.onFailure(App.getAppContext().getString(R.string.otp_expired));
                break;
            case 1008:
                networkResponseCallback.onFailure(App.getAppContext().getString(R.string.error_invalid_otp));
                break;
            case 1003:
                networkResponseCallback.onFailure(App.getAppContext().getString(R.string.token_expired));
                break;
            default:
                networkResponseCallback.onFailure(App.getAppContext().getString(R.string.unknown));
        }
    }

    private static int getStatusCode(String response) {
        int code = 0;
        try {
            JSONObject object = new JSONObject(response);
            code = object.getInt("code");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }
}
