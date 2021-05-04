package com.paragon.utils.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The type App preference.
 */
public class AppPreference {

    private static final String PREFS_FILE_NAME = "local_pref";
    private static SharedPreferences settings;
    private static AppPreference instance;

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static AppPreference getInstance(Context context) {
        if (instance == null) {
            settings = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
            instance = new AppPreference();
            return instance;
        }
        return instance;
    }

    /**
     * Add value.
     *
     * @param preferencesKey the preferences key
     * @param text           the text
     */
    public void addValue(PreferenceKeys preferencesKey, String text) {
        Editor editor;
        editor = settings.edit();
        editor.putString(preferencesKey.getKey(), text);
        editor.apply();
    }

    /**
     * Add boolean.
     *
     * @param preferencesKey the preferences key
     * @param value          the value
     */
    public void addBoolean(PreferenceKeys preferencesKey, Boolean value) {
        try {
            Editor editor;
            editor = settings.edit();
            editor.putBoolean(preferencesKey.getKey(), value);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add int.
     *
     * @param preferencesKey the preferences key
     * @param value          the value
     */
    public void addInt(PreferenceKeys preferencesKey, Integer value) {
        try {
            Editor editor;
            editor = settings.edit();
            editor.putInt(preferencesKey.getKey(), value);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets value.
     *
     * @param preferencesKey the preferences key
     * @return the value
     */
    public String getValue(PreferenceKeys preferencesKey) {
        String text;
        text = settings.getString(preferencesKey.getKey(), "");
        return text;
    }

    /**
     * Gets int.
     *
     * @param preferencesKey the preferences key
     * @return the int
     */
    public int getInt(PreferenceKeys preferencesKey) {
        return settings.getInt(preferencesKey.getKey(), 0);
    }

    /**
     * Add list.
     *
     * @param preferencesKey the preferences key
     * @param storedList     the stored list
     */
    public void addList(PreferenceKeys preferencesKey, ArrayList<String> storedList) {
        //Set the values
        Set<String> set = new HashSet<>();
        set.addAll(storedList);
        Editor editor;
        editor = settings.edit();
        editor.putStringSet(preferencesKey.getKey(), set);
        editor.apply();
    }

    /**
     * Gets boolean.
     *
     * @param preferencesKey the preferences key
     * @return the boolean
     */
    public boolean getBoolean(PreferenceKeys preferencesKey) {
        return settings.getBoolean(preferencesKey.getKey(), false);
    }

    /**
     * Gets list.
     *
     * @param preferencesKey the preferences key
     * @return the list
     */
    public Set<String> getList(PreferenceKeys preferencesKey) {

        Set<String> set = null;
        try {
            //Retrieve the values
            set = settings.getStringSet(preferencesKey.getKey(), null);

            return set;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    /**
     * Clear shared preference.
     */
    public void clearSharedPreference() {
        Editor editor;
        editor = settings.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * Remove value.
     *
     * @param preferencesKey the preferences key
     */
    public void removeValue(PreferenceKeys preferencesKey) {
        Editor editor;
        editor = settings.edit();
        editor.putString(preferencesKey.getKey(), "");
        editor.apply();
    }

    /**
     * Remove value of list.
     *
     * @param preferencesKey the preferences key
     */
    public void removeValueOfList(PreferenceKeys preferencesKey) {
        Set<String> set = new HashSet<>();
        Editor editor;
        editor = settings.edit();
        editor.putStringSet(preferencesKey.getKey(), set);
        editor.apply();
    }
}
