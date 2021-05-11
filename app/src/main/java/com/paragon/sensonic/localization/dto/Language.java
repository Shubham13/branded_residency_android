package com.paragon.sensonic.localization.dto;

public class Language {
    private String langCode;
    private String displayName;
    private String position;
    private String image;

    public Language(String langCode, String displayName, String position, String image) {
        this.langCode = langCode;
        this.displayName = displayName;
        this.position = position;
        this.image = image;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
