package com.nowenui.systemtweakerfree.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryInfo {

    public static final int HEALTH_HAVING_ISSUES = 0;
    public static final int HEALTH_GOOD = 1;
    private final Context context;

    public BatteryInfo(Context context) {
        this.context = context;
    }

    public int getBatteryPercentage() {
        int percentage = 0;
        Intent batteryStatus = getBatteryStatusIntent();
        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            percentage = (int) ((level / (float) scale) * 100);
        }

        return percentage;
    }

    public boolean isDeviceCharging() {
        Intent batteryStatus = getBatteryStatusIntent();
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return (status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL);

    }

    private Intent getBatteryStatusIntent() {
        IntentFilter batFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        return context.registerReceiver(null, batFilter);
    }

    public int getBatteryHealth() {
        int health = HEALTH_HAVING_ISSUES;
        Intent batteryStatus = getBatteryStatusIntent();
        if (batteryStatus != null) {
            health = batteryStatus.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            if (health == BatteryManager.BATTERY_HEALTH_GOOD) {
                health = HEALTH_GOOD;
            } else {
                health = HEALTH_HAVING_ISSUES;
            }
        }
        return health;
    }

    public String getBatteryTechnology() {
        return CheckLibrary
                .checkValidData(getBatteryStatusIntent().getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY));
    }

    public float getBatteryTemperature() {
        float temp = 0.0f;
        Intent batteryStatus = getBatteryStatusIntent();
        if (batteryStatus != null) {
            temp = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10;
        }
        return temp;
    }

    public int getBatteryVoltage() {
        int volt = 0;
        Intent batteryStatus = getBatteryStatusIntent();
        if (batteryStatus != null) {
            volt = batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
        }
        return volt;
    }

}