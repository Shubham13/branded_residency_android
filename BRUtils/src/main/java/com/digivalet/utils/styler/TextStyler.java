package com.digivalet.utils.styler;

class TextStyler {
    private String textColor;
    private String alpha;
    private String themeName;

    public TextStyler(String textColor, String alpha, String themeName) {
        this.textColor = textColor;
        this.alpha = alpha;
        this.themeName = themeName;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
}
