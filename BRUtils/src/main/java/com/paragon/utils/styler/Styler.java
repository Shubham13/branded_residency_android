package com.paragon.utils.styler;

import java.io.Serializable;

/**
 * Created by Rupesh Saxena
 */

class Styler implements Serializable {
    private String style;
    private String shape;
    private String backgroundColor;
    private String alpha;
    private String tintColor;
    private String cornerRadius;
    private String topLeftCornerRadius;
    private String topRightCornerRadius;
    private String bottomLeftCornerRadius;
    private String bottomRightCornerRadius;
    private String borderWidth;
    private String borderColor;
    private String[] gradientColors;
    private String gradientOrientation;

    public Styler(String style, String shape, String backgroundColor, String alpha, String tintColor, String cornerRadius, String topLeftCornerRadius, String topRightCornerRadius, String bottomLeftCornerRadius, String bottomRightCornerRadius, String borderWidth, String borderColor, String[] gradientColors, String gradientOrientation) {
        this.style = style;
        this.shape = shape;
        this.backgroundColor = backgroundColor;
        this.alpha = alpha;
        this.tintColor = tintColor;
        this.cornerRadius = cornerRadius;
        this.topLeftCornerRadius = topLeftCornerRadius;
        this.topRightCornerRadius = topRightCornerRadius;
        this.bottomLeftCornerRadius = bottomLeftCornerRadius;
        this.bottomRightCornerRadius = bottomRightCornerRadius;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
        this.gradientColors = gradientColors;
        this.gradientOrientation = gradientOrientation;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public String getTintColor() {
        return tintColor;
    }

    public void setTintColor(String tintColor) {
        this.tintColor = tintColor;
    }

    public String getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(String cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public String getTopLeftCornerRadius() {
        return topLeftCornerRadius;
    }

    public void setTopLeftCornerRadius(String topLeftCornerRadius) {
        this.topLeftCornerRadius = topLeftCornerRadius;
    }

    public String getTopRightCornerRadius() {
        return topRightCornerRadius;
    }

    public void setTopRightCornerRadius(String topRightCornerRadius) {
        this.topRightCornerRadius = topRightCornerRadius;
    }

    public String getBottomLeftCornerRadius() {
        return bottomLeftCornerRadius;
    }

    public void setBottomLeftCornerRadius(String bottomLeftCornerRadius) {
        this.bottomLeftCornerRadius = bottomLeftCornerRadius;
    }

    public String getBottomRightCornerRadius() {
        return bottomRightCornerRadius;
    }

    public void setBottomRightCornerRadius(String bottomRightCornerRadius) {
        this.bottomRightCornerRadius = bottomRightCornerRadius;
    }

    public String getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(String borderWidth) {
        this.borderWidth = borderWidth;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String[] getGradientColors() {
        return gradientColors;
    }

    public void setGradientColors(String[] gradientColors) {
        this.gradientColors = gradientColors;
    }

    public String getGradientOrientation() {
        return gradientOrientation;
    }

    public void setGradientOrientation(String gradientOrientation) {
        this.gradientOrientation = gradientOrientation;
    }
}

