package com.paragon.sensonic.localization;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import com.paragon.sensonic.localization.dto.Language;
import com.paragon.sensonic.utils.AppConstant;
import com.paragon.utils.GeneralFunctions;
import com.paragon.utils.local.AppPreference;
import com.paragon.utils.local.PreferenceKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class LanguageManager {

    private AppPreference appPreference;
    private String response;
    private final String fileName = "language";
    private final String fileExt = "json";


    public LanguageManager(Context context) {
        appPreference = AppPreference.getInstance(context);
        if (!appPreference.getValue(PreferenceKeys.LANGUAGE_JSON).matches(AppConstant.EMPTY)) {
            response = appPreference.getValue(PreferenceKeys.LANGUAGE_JSON);
        } else {
            response = GeneralFunctions.readFileByAssets(context, fileName, fileExt);
            appPreference.addValue(PreferenceKeys.LANGUAGE_JSON, response);
        }
    }

    public ArrayList<Language> getAvailableLanguages() {

        ArrayList<Language> list = new ArrayList<>();

        try {
            JSONObject mainNode = new JSONObject(response);
            JSONArray availableLanguages = mainNode.getJSONArray("availableLanguages");
            for (int i = 0; i < availableLanguages.length(); i++) {
                JSONObject item = availableLanguages.getJSONObject(i);
                list.add(new Language(item.getString("langCode"),
                        item.getString("displayName"),
                        item.getString("position"),
                        item.getString("image")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public String getTranslation(String tag) {

        return null;
    }

    public  void setLocale(Context context, String languageCode) {
        try {
            Locale locale = new Locale(languageCode);
            Resources resources = context.getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;

            if (Build.VERSION.SDK_INT >= 17) {
                configuration.setLayoutDirection(locale);
            }

            resources.updateConfiguration(configuration, displayMetrics);
            appPreference.addValue(PreferenceKeys.APP_LANGUAGE, languageCode);
        } catch (NullPointerException n) {
            n.printStackTrace();
        } catch (RuntimeException rte) {
            rte.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
