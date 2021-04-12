package com.digivalet.utils.styler;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.digivalet.utils.GeneralFunctions;

/**
 * Created by Rupesh Saxena
 */

public class StyleBuilder {

    private static int[] colors;

    public static void setStyleOnView(Host host, View view) {

        Styler styler = StyleParser.getInstance(view.getContext()).getStyleForView(host.name(), getStyleId(view));
        GradientDrawable drawable = new GradientDrawable();

        if (styler.getShape() != null && !styler.getShape().matches("null")) {
            if (styler.getShape().matches("rectangle"))
                drawable.setShape(GradientDrawable.RECTANGLE);

            if (styler.getShape().matches("oval"))
                drawable.setShape(GradientDrawable.OVAL);
        }

        if (styler.getBorderWidth() != null && styler.getBorderColor() != null && !styler.getBorderWidth().matches("null") && !styler.getBorderColor().matches("null")) {
            int stockWidth = Integer.parseInt(styler.getBorderWidth());
            int stockColor = Color.parseColor(styler.getBorderColor());
            drawable.setStroke(stockWidth, stockColor);
        }

        if (styler.getTintColor() != null && !styler.getTintColor().matches("null")) {
            int tintColor = Color.parseColor(styler.getTintColor());
            drawable.setTint(tintColor);
        }

        if (styler.getCornerRadius() != null && !styler.getCornerRadius().matches("null")) {
            float radius = Float.parseFloat(styler.getCornerRadius());
            drawable.setCornerRadius(GeneralFunctions.pxToDp(view.getContext(), radius));
        }

        if (styler.getTopLeftCornerRadius() != null && !styler.getTopLeftCornerRadius().matches("null")
                && styler.getTopRightCornerRadius() != null && !styler.getTopRightCornerRadius().matches("null")) {
            float radius = Float.parseFloat(styler.getTopLeftCornerRadius());
            drawable.setCornerRadii(new float[]{GeneralFunctions.pxToDp(view.getContext(), radius), GeneralFunctions.pxToDp(view.getContext(), radius),
                    GeneralFunctions.pxToDp(view.getContext(), radius), GeneralFunctions.pxToDp(view.getContext(), radius), 0, 0, 0, 0});
        }

        if (styler.getBottomLeftCornerRadius() != null && !styler.getBottomLeftCornerRadius().matches("null")
                && styler.getBottomRightCornerRadius() != null && !styler.getBottomRightCornerRadius().matches("null")) {
            float radius = Float.parseFloat(styler.getBottomLeftCornerRadius());
            drawable.setCornerRadii(new float[]{0, 0, 0, 0, GeneralFunctions.pxToDp(view.getContext(), radius), GeneralFunctions.pxToDp(view.getContext(), radius), GeneralFunctions.pxToDp(view.getContext(), radius), GeneralFunctions.pxToDp(view.getContext(), radius)});
        }


        if (styler.getStyle() != null && !styler.getStyle().matches("null")) {
            if (styler.getStyle().matches("plain")) {
                if (styler.getBackgroundColor() != null && !styler.getBackgroundColor().matches("null")) {
                    int backgroundColor = Color.parseColor(styler.getBackgroundColor());
                    drawable.setColor(backgroundColor);
                }
            } else {
                if (styler.getGradientOrientation() != null && !styler.getGradientOrientation().matches("null")) {

                    switch (styler.getGradientOrientation()) {
                        case "top_bottom":
                            drawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                            break;

                        case "tr_bl":
                            drawable.setOrientation(GradientDrawable.Orientation.TR_BL);
                            break;

                        case "right_left":
                            drawable.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
                            break;

                        case "br_tl":
                            drawable.setOrientation(GradientDrawable.Orientation.BR_TL);
                            break;

                        case "bottom_top":
                            drawable.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                            break;

                        case "bl_tr":
                            drawable.setOrientation(GradientDrawable.Orientation.BL_TR);
                            break;

                        case "left_right":
                            drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                            break;

                        case "tl_br":
                            drawable.setOrientation(GradientDrawable.Orientation.TL_BR);
                            break;
                    }
                }

                if (styler.getGradientColors().length >= 1) {
                    if (styler.getGradientColors().length == 1) {
                        colors = new int[1];
                        colors[0] = Integer.parseInt(styler.getGradientColors()[0]);
                    }

                    if (styler.getGradientColors().length == 2) {
                        colors = new int[2];
                        colors[0] = Integer.parseInt(styler.getGradientColors()[0]);
                        colors[1] = Integer.parseInt(styler.getGradientColors()[1]);
                    }

                    if (styler.getGradientColors().length == 3) {
                        colors = new int[3];
                        colors[0] = Color.parseColor(styler.getGradientColors()[0]);
                        colors[1] = Color.parseColor(styler.getGradientColors()[1]);
                        colors[2] = Color.parseColor(styler.getGradientColors()[2]);
                    }

                    drawable.setColors(colors);
                }

            }
        }

        view.setBackground(drawable);

        if (styler.getAlpha() != null && !styler.getAlpha().matches("null"))
            view.setAlpha(Float.parseFloat(styler.getAlpha()));
    }

    public static void setStyleOnText(Host host, TextView view) {

        TextStyler styler = StyleParser.getInstance(view.getContext()).getStyleForText(host.name(), getStyleId(view));

        if (styler.getTextColor() != null && !styler.getTextColor().matches("null")) {
            view.setTextColor(Color.parseColor(styler.getTextColor()));
        }

        if (styler.getAlpha() != null && !styler.getAlpha().matches("null")) {
            view.setAlpha(Float.parseFloat(styler.getAlpha()));
        }

        if (styler.getThemeName() != null && !styler.getThemeName().matches("null")) {
            view.setTextAppearance(view.getContext(), view.getContext().getResources().getIdentifier(styler.getThemeName(), "style", view.getContext().getPackageName()));
        }
    }

    public static void setStyleOnEditText(Host host, EditText view) {

        TextStyler styler = StyleParser.getInstance(view.getContext()).getStyleForText(host.name(), getStyleId(view));

        if (styler.getTextColor() != null && !styler.getTextColor().matches("null")) {
            view.setTextColor(Color.parseColor(styler.getTextColor()));
            view.setHintTextColor(Color.parseColor(styler.getTextColor()));
        }

        if (styler.getAlpha() != null && !styler.getAlpha().matches("null")) {
            view.setAlpha(Float.parseFloat(styler.getAlpha()));
        }

        if (styler.getThemeName() != null && !styler.getThemeName().matches("null")) {
           view.setTextAppearance(view.getContext(), view.getContext().getResources().getIdentifier(styler.getThemeName(), "style", view.getContext().getPackageName()));
        }
    }

    public static void setStyleOnView(Host host, String id, View view) {

        Styler styler = StyleParser.getInstance(view.getContext()).getStyleForView(host.name(), id);
        GradientDrawable drawable = new GradientDrawable();

        if (styler.getShape() != null && !styler.getShape().matches("null")) {
            if (styler.getShape().matches("rectangle"))
                drawable.setShape(GradientDrawable.RECTANGLE);

            if (styler.getShape().matches("oval"))
                drawable.setShape(GradientDrawable.OVAL);
        }

        if (styler.getBorderWidth() != null && styler.getBorderColor() != null && !styler.getBorderWidth().matches("null") && !styler.getBorderColor().matches("null")) {
            int stockWidth = Integer.parseInt(styler.getBorderWidth());
            int stockColor = Color.parseColor(styler.getBorderColor());
            drawable.setStroke(stockWidth, stockColor);
        }

        if (styler.getTintColor() != null && !styler.getTintColor().matches("null")) {
            int tintColor = Color.parseColor(styler.getTintColor());
            drawable.setTint(tintColor);
        }

        if (styler.getCornerRadius() != null && !styler.getCornerRadius().matches("null")) {
            float radius = Float.parseFloat(styler.getCornerRadius());
            drawable.setCornerRadius(GeneralFunctions.pxToDp(view.getContext(), radius));
        }

        if (styler.getTopLeftCornerRadius() != null && !styler.getTopLeftCornerRadius().matches("null")
                && styler.getTopRightCornerRadius() != null && !styler.getTopRightCornerRadius().matches("null")) {
            float radius = Float.parseFloat(styler.getTopLeftCornerRadius());
            drawable.setCornerRadii(new float[]{GeneralFunctions.pxToDp(view.getContext(), radius), GeneralFunctions.pxToDp(view.getContext(), radius),
                    GeneralFunctions.pxToDp(view.getContext(), radius), GeneralFunctions.pxToDp(view.getContext(), radius), 0, 0, 0, 0});
        }

        if (styler.getBottomLeftCornerRadius() != null && !styler.getBottomLeftCornerRadius().matches("null")
                && styler.getBottomRightCornerRadius() != null && !styler.getBottomRightCornerRadius().matches("null")) {
            float radius = Float.parseFloat(styler.getBottomLeftCornerRadius());
            drawable.setCornerRadii(new float[]{0, 0, 0, 0, GeneralFunctions.pxToDp(view.getContext(), radius), GeneralFunctions.pxToDp(view.getContext(), radius), GeneralFunctions.pxToDp(view.getContext(), radius), GeneralFunctions.pxToDp(view.getContext(), radius)});
        }


        if (styler.getStyle() != null && !styler.getStyle().matches("null")) {
            if (styler.getStyle().matches("plain")) {
                if (styler.getBackgroundColor() != null && !styler.getBackgroundColor().matches("null")) {
                    int backgroundColor = Color.parseColor(styler.getBackgroundColor());
                    drawable.setColor(backgroundColor);
                }
            } else {
                if (styler.getGradientOrientation() != null && !styler.getGradientOrientation().matches("null")) {

                    switch (styler.getGradientOrientation()) {
                        case "top_bottom":
                            drawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                            break;

                        case "tr_bl":
                            drawable.setOrientation(GradientDrawable.Orientation.TR_BL);
                            break;

                        case "right_left":
                            drawable.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
                            break;

                        case "br_tl":
                            drawable.setOrientation(GradientDrawable.Orientation.BR_TL);
                            break;

                        case "bottom_top":
                            drawable.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                            break;

                        case "bl_tr":
                            drawable.setOrientation(GradientDrawable.Orientation.BL_TR);
                            break;

                        case "left_right":
                            drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                            break;

                        case "tl_br":
                            drawable.setOrientation(GradientDrawable.Orientation.TL_BR);
                            break;
                    }
                }

                if (styler.getGradientColors().length >= 1) {
                    if (styler.getGradientColors().length == 1) {
                        colors = new int[1];
                        colors[0] = Integer.parseInt(styler.getGradientColors()[0]);
                    }

                    if (styler.getGradientColors().length == 2) {
                        colors = new int[2];
                        colors[0] = Integer.parseInt(styler.getGradientColors()[0]);
                        colors[1] = Integer.parseInt(styler.getGradientColors()[1]);
                    }

                    if (styler.getGradientColors().length == 3) {
                        colors = new int[3];
                        colors[0] = Color.parseColor(styler.getGradientColors()[0]);
                        colors[1] = Color.parseColor(styler.getGradientColors()[1]);
                        colors[2] = Color.parseColor(styler.getGradientColors()[2]);
                    }

                    drawable.setColors(colors);
                }

            }
        }

        view.setBackground(drawable);

        if (styler.getAlpha() != null && !styler.getAlpha().matches("null"))
            view.setAlpha(Float.parseFloat(styler.getAlpha()));
    }


    /*utils methods*/
    private static String getStyleId(View view) {
        return view.getContext().getResources().getResourceName(view.getId()).substring(view.getContext().getResources().getResourceName(view.getId()).lastIndexOf("/") + 1);
    }
}