package com.paragon.sensonic.localization;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import com.paragon.sensonic.localization.dto.Language;
import com.paragon.sensonic.utils.AppConstant;
import com.paragon.sensonic.utils.AppPreference;
import com.paragon.sensonic.utils.PreferenceKeys;
import com.paragon.utils.GeneralFunctions;

import org.json.JSONArray;
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
        String result = AppConstant.EMPTY;
        try {
            JSONObject mainNode = new JSONObject(response);
            JSONArray multiLingual = mainNode.getJSONArray("multiLingual");
            for (int i = 0; i < multiLingual.length(); i++) {
                JSONObject item = multiLingual.getJSONObject(i);
                String key = item.getString("tag") + "|" + item.getString("module");
                if (key.equals(tag)) {
                    JSONArray translator = item.getJSONArray("translator");
                    for (int j = 0; j < translator.length(); j++) {
                        JSONObject word = translator.getJSONObject(j);
                        if (word.getString("code").matches(appPreference.getValue(PreferenceKeys.APP_LANGUAGE))) {
                            result = word.getString("value");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setLocale(Context context, String languageCode) {
        try {
            Locale locale = new Locale(languageCode);
            Resources resources = context.getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;
            configuration.setLayoutDirection(locale);
            resources.updateConfiguration(configuration, displayMetrics);
            appPreference.addValue(PreferenceKeys.APP_LANGUAGE, languageCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
