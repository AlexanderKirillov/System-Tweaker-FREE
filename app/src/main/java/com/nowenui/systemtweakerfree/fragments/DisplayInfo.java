package com.nowenui.systemtweakerfree.fragments;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DisplayInfo {
    private final Context context;

    public DisplayInfo(Context context) {
        this.context = context;
    }

    public String getDensity() {
        String densityStr = null;
        final int density = context.getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                densityStr = "LDPI (120 DPI)";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                densityStr = "MDPI (160 DPI)";
                break;
            case DisplayMetrics.DENSITY_TV:
                densityStr = "TVDPI";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                densityStr = "HDPI (240 DPI)";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                densityStr = "XHDPI(320 DPI)";
                break;
            case DisplayMetrics.DENSITY_400:
                densityStr = "XMHDPI";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                densityStr = "XXHDPI (480 DPI)";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                densityStr = "XXXHDPI (640 DPI)";
                break;
            default:
                break;
        }
        return CheckLibrary.checkValidData(densityStr);
    }

    public String getResolution() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return CheckLibrary.checkValidData(metrics.heightPixels + "x" + metrics.widthPixels);
    }
}
