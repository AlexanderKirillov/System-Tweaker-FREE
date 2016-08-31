package com.nowenui.systemtweakerfree.fragments;

import android.content.Context;
import android.os.Build;

import java.util.Locale;

public class DeviceInfo {

    private Context context;

    public DeviceInfo(Context context) {
        this.context = context;
    }

    public String getDevice() {
        return CheckLibrary.checkValidData(Build.DEVICE);
    }

    public String getRadioVer() {
        String result = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            result = Build.getRadioVersion();
        }
        return CheckLibrary.checkValidData(result);
    }

    public String getLanguage() {
        return CheckLibrary.checkValidData(Locale.getDefault().getLanguage());
    }

    public String getBuildVersionIncremental() {
        return CheckLibrary.checkValidData(Build.VERSION.INCREMENTAL);
    }

    public int getBuildVersionSDK() {
        return Build.VERSION.SDK_INT;
    }

    public String getOSCodename() {
        String codename;
        switch (Build.VERSION.SDK_INT) {
            case Build.VERSION_CODES.BASE:
                codename = "First Android Version. Yay !";
                break;
            case Build.VERSION_CODES.BASE_1_1:
                codename = "Base Android 1.1";
                break;
            case Build.VERSION_CODES.CUPCAKE:
                codename = "Cupcake";
                break;
            case Build.VERSION_CODES.DONUT:
                codename = "Donut";
                break;
            case Build.VERSION_CODES.ECLAIR:
            case Build.VERSION_CODES.ECLAIR_0_1:
            case Build.VERSION_CODES.ECLAIR_MR1:

                codename = "Eclair";
                break;
            case Build.VERSION_CODES.FROYO:
                codename = "Froyo";
                break;
            case Build.VERSION_CODES.GINGERBREAD:
            case Build.VERSION_CODES.GINGERBREAD_MR1:
                codename = "Gingerbread";
                break;
            case Build.VERSION_CODES.HONEYCOMB:
            case Build.VERSION_CODES.HONEYCOMB_MR1:
            case Build.VERSION_CODES.HONEYCOMB_MR2:
                codename = "Honeycomb";
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
                codename = "Ice Cream Sandwich";
                break;
            case Build.VERSION_CODES.JELLY_BEAN:
            case Build.VERSION_CODES.JELLY_BEAN_MR1:
            case Build.VERSION_CODES.JELLY_BEAN_MR2:
                codename = "Jelly Bean";
                break;
            case Build.VERSION_CODES.KITKAT:
                codename = "Kitkat";
                break;
            case Build.VERSION_CODES.KITKAT_WATCH:
                codename = "Kitkat Watch";
                break;
            case Build.VERSION_CODES.LOLLIPOP:
            case Build.VERSION_CODES.LOLLIPOP_MR1:
                codename = "Lollipop";
                break;
            case Build.VERSION_CODES.M:
                codename = "Marshmallow";
                break;
            default:
                codename = "N/A";
                break;
        }
        return codename;
    }

}
