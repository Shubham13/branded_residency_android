package com.digivalet.utils.networking;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Rupesh Saxena
 */

class Utils {

    public static boolean isInternetAvailable() {
        try {
            final String TEST_URL = "www.google.com";
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            InetAddress ip = InetAddress.getByName(TEST_URL);
            return !ip.equals("");
        } catch (Exception e) {
            return false;
        }
    }

    public static String urlEncodeUTF8(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

    public static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static String getResponseBody(Response<ResponseBody> response) {

        BufferedReader reader = null;
        String output = null;
        try {

            if (response.body() != null) {
                reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
            } else if (response.errorBody() != null) {
                reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
            }
            output = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    public static String checkJsonErrorBody(JSONObject jobject) {
        try {
            if (jobject.has("error")) {
                Object error = jobject.get("error");
                return getJsonErrorBody(error, jobject);
            } else {
                return jobject.optString("message");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getJsonErrorBody(Object error, JSONObject jobject) {
        try {

            if (error instanceof JSONObject) {
                String message = "";
                String name = "";
                String field = "";
                JSONObject errObj = (JSONObject) error;
                if (errObj.has("field"))
                    field = errObj.optString("field");

                if (errObj.has("message")) {
                    Object msgObj = errObj.get("message");
                    if (msgObj instanceof JSONObject) {
                        name = ((JSONObject) msgObj).optString("name");
                        message = ((JSONObject) msgObj).optString("message");
                    } else {
                        message = errObj.optString("message");
                    }
                } else {
                    try {
                        message = jobject.optString("message");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return field + "\n" + name + "\n" + message;

            } else if (error instanceof JSONArray) {

                try {
                    JSONArray errArray = (JSONArray) error;
                    return errArray.getJSONObject(0).optString("message");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception je) {
            je.printStackTrace();
        }
        return "";
    }

}
