package com.nowenui.systemtweakerfree.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.util.UUID;

public class IdInfo {
    private final Context context;

    public IdInfo(Context context) {
        this.context = context;
    }

    public String getAndroidID() {
        return CheckLibrary
                .checkValidData(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
    }

    @SuppressWarnings("deprecation")
    public String getPseudoUniqueID() {
        String devIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            devIDShort += (Build.SUPPORTED_ABIS[0].length() % 10);
        } else {
            devIDShort += (Build.CPU_ABI.length() % 10);
        }

        devIDShort += (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10)
                + (Build.PRODUCT.length() % 10);

        String serial;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();

            return new UUID(devIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception e) {

            serial = "ESYDV000";
        }

        return new UUID(devIDShort.hashCode(), serial.hashCode()).toString();
    }

    @SuppressWarnings("MissingPermission")
    public String getGSFID() {
        final Uri URI = Uri.parse("content://com.google.android.gsf.gservices");
        final String ID_KEY = "android_id";

        String[] params = {ID_KEY};
        Cursor c = context.getContentResolver().query(URI, null, null, params, null);

        if (c == null) {
            return "N/A";
        } else if (!c.moveToFirst() || c.getColumnCount() < 2) {
            c.close();
            return "N/A";
        }

        try {
            String gsfID = Long.toHexString(Long.parseLong(c.getString(1)));
            c.close();
            return gsfID;
        } catch (NumberFormatException e) {
            c.close();
            return "N/A";
        }
    }
}
