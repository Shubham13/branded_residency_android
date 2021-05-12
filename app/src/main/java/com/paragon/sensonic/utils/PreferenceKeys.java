package com.paragon.sensonic.utils;


/**
 * The enum Preference keys.
 */
public enum PreferenceKeys {
    /*USER INFO*/
    SESSION("Session"),
    MOBILE("Mobile"),
    EMAIL("Email"),
    CREDENTIALS("Credentials"),
    USER("User"),
    LANGUAGE_JSON("LanguageJson"),
    APP_LANGUAGE("SelectedLanguage");

    private String text;

    PreferenceKeys(final String text) {
        this.text = text;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}

