package com.paragon.utils.local;


/**
 * The enum Preference keys.
 */
public enum PreferenceKeys {

    /*USER INFO*/
    SESSION("Session"),
    MOBILE("Mobile"),
    ISMOBILE("Is_mobile"),
    EMAIL("Email"),
    CREDENTIALS("Credentials"),
    USER("User");

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

