package com.paragon.utils.styler;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by Rupesh Saxena
 */

class StyleParser {
    private static final String PREFERENCE_NAME = "Styler";
    private static StyleParser instance = null;
    private final String file = "style.json";
    private final Context context;
    private final SharedPreferences sharedpreferences;
    private String style;
    private String[] colors;

    private StyleParser(Context context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        style = loadJSONFromAsset();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(style, style);
        editor.apply();
    }

    public static StyleParser getInstance(Context context) {
        if (instance == null)
            instance = new StyleParser(context);
        return instance;
    }

    public Styler getStyleForView(String host, String styleId) {
        try {
            style = sharedpreferences.getString(style, null);
            JSONObject jsonObject = new JSONObject(style).getJSONObject(host).getJSONObject(styleId);

            if(jsonObject.getJSONArray("gradientColors").length()>0) {
                JSONArray array = jsonObject.getJSONArray("gradientColors");
                colors = new String[]{array.getJSONObject(0).optString("startColor", null), array.getJSONObject(1).optString("centerColor", null), array.getJSONObject(2).optString("endColor", null)};
            }

            return new Styler(
                    jsonObject.optString("style", null),
                    jsonObject.optString("shape", null),
                    jsonObject.optString("backgroundColor", null),
                    jsonObject.optString("alpha", null),
                    jsonObject.optString("tintColor", null),
                    jsonObject.optString("cornerRadius", null),
                    jsonObject.optString("topLeftCornerRadius", null),
                    jsonObject.optString("topRightCornerRadius", null),
                    jsonObject.optString("bottomLeftCornerRadius", null),
                    jsonObject.optString("bottomRightCornerRadius", null),
                    jsonObject.optString("borderWidth", null),
                    jsonObject.optString("borderColor", null),
                    colors,
                    jsonObject.optString("gradientOrientation", null));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TextStyler getStyleForText(String host, String styleId) {
        try {
            style = sharedpreferences.getString(style, null);
            JSONObject jsonObject = new JSONObject(style).getJSONObject(host).getJSONObject(styleId);


            return new TextStyler(
                    jsonObject.optString("textColor", null),
                    jsonObject.optString("alpha", null),
                    jsonObject.optString("themeName", null));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
