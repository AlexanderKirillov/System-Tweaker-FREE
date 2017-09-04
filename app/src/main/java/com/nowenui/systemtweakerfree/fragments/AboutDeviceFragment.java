package com.nowenui.systemtweakerfree.fragments;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mrengineer13.snackbar.SnackBar;
import com.jaredrummler.android.device.DeviceName;
import com.nowenui.systemtweakerfree.R;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

import github.nisrulz.easydeviceinfo.base.EasyBatteryMod;
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import github.nisrulz.easydeviceinfo.base.EasyDisplayMod;
import github.nisrulz.easydeviceinfo.base.EasyIdMod;

public class AboutDeviceFragment extends Fragment {

    private String BatteryHealth;

    public static AboutDeviceFragment newInstance(Bundle bundle) {
        AboutDeviceFragment AboutDevice = new AboutDeviceFragment();

        if (bundle != null) {
            AboutDevice.setArguments(bundle);
        }

        return AboutDevice;
    }

    ////////////////////////////////
    ////// Get heap size ///////////
    ////////////////////////////////
    public static String getHeapSize() {
        try {
            Process proc = Runtime.getRuntime().exec("getprop dalvik.vm.heapsize");
            InputStream is = proc.getInputStream();
            String size = getStringFromInputStream(is);
            if (!size.equals("\n"))
                return size;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "? (unknown)";
    }

    private static String getStringFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;

        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    ///////////////////////////////////
    ////// About processor ///////////
    /////////////////////////////////
    private String ProcessorInfo() {
        StringBuffer sb = new StringBuffer();
        if (new File("/proc/cpuinfo").exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
                String aLine;
                while ((aLine = br.readLine()) != null) {
                    sb.append(aLine + "\n");
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    ///////////////////////////////
    ////// Get CPU name ///////////
    ///////////////////////////////
    public synchronized String getCPUName() {
        String CPUName = "";

        String[] lines = ProcessorInfo().split("\n");

        for (int i = 0; i < lines.length; i++) {

            if (lines[i].contains("Hardware\t:")) {

                CPUName = lines[i].replace("Hardware\t: ", "");
                break;
            }
        }
        return CPUName;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aboutdevice, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        /////////////////////////////////////
        ////// Get battery health ///////////
        /////////////////////////////////////
        EasyBatteryMod easyBatteryMod = new EasyBatteryMod(getContext());
        @github.nisrulz.easydeviceinfo.base.BatteryHealth int batteryHealth = easyBatteryMod.getBatteryHealth();
        Resources res = getResources();
        switch (batteryHealth) {
            case github.nisrulz.easydeviceinfo.base.BatteryHealth.GOOD:
                BatteryHealth = res.getString(R.string.goodbatterystatus);
                break;
            case github.nisrulz.easydeviceinfo.base.BatteryHealth.HAVING_ISSUES:
                BatteryHealth = res.getString(R.string.problems);
                break;
            default:
                BatteryHealth = res.getString(R.string.problems);
                break;
        }

        EasyIdMod easyIdMod = new EasyIdMod(getContext());
        EasyDisplayMod easyDisplayMod = new EasyDisplayMod(getContext());
        MemInfo easyMemoryMod = new MemInfo(getContext());
        EasyDeviceMod easydevicemod = new EasyDeviceMod(getContext());
        TextView tv1 = view.findViewById(R.id.textviewabout1);
        TextView tv2 = view.findViewById(R.id.textviewabout2);
        TextView tv3 = view.findViewById(R.id.textviewabout3);
        TextView tv4 = view.findViewById(R.id.textviewabout4);
        TextView tv5 = view.findViewById(R.id.textviewabout5);

        Button button = view.findViewById(R.id.button);
        button.setBackgroundResource(R.drawable.roundbuttoncal);
        button.setTextColor(Color.WHITE);
        button.setEnabled(false);

        Button button2 = view.findViewById(R.id.button2);
        button2.setBackgroundResource(R.drawable.roundbuttoncal);
        button2.setTextColor(Color.WHITE);
        button2.setEnabled(false);

        Button button4 = view.findViewById(R.id.button4);
        button4.setBackgroundResource(R.drawable.roundbuttoncal);
        button4.setTextColor(Color.WHITE);
        button4.setEnabled(false);

        Button button5 = view.findViewById(R.id.button5);
        button5.setBackgroundResource(R.drawable.roundbuttoncal);
        button5.setTextColor(Color.WHITE);
        button5.setEnabled(false);

        Button button6 = view.findViewById(R.id.button6);
        button6.setBackgroundResource(R.drawable.roundbuttoncal);
        button6.setTextColor(Color.WHITE);
        button6.setEnabled(false);

        Button button7 = view.findViewById(R.id.button7);
        button7.setBackgroundResource(R.drawable.roundbuttoncal);
        button7.setTextColor(Color.WHITE);
        button7.setEnabled(false);

        final Button buttoncheck = view.findViewById(R.id.buttoncheck);
        buttoncheck.setBackgroundResource(R.drawable.roundbuttongood);
        buttoncheck.setTextColor(Color.WHITE);

        final Button buttoncheckremove = view.findViewById(R.id.buttoncheckremove);
        buttoncheckremove.setBackgroundResource(R.drawable.roundbuttongood);
        buttoncheckremove.setTextColor(Color.WHITE);

        final TextView statusik = view.findViewById(R.id.statusik);

        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (mSharedPreference.contains("skipnitd")) {
            buttoncheck.setEnabled(false);
            buttoncheck.setText(R.string.cant);
            buttoncheck.setBackgroundResource(R.drawable.roundbuttonfuck);
            statusik.setVisibility(View.GONE);
            buttoncheckremove.setVisibility(View.GONE);
        } else {
            buttoncheck.setEnabled(true);
        }

        buttoncheckremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttoncheckremove.setEnabled(false);
                buttoncheckremove.setVisibility(View.GONE);
                buttoncheck.setVisibility(View.VISIBLE);

                if (RootTools.isAccessGiven()) {
                    Command command1 = new Command(0,
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                            "rm -f /data/checkinit.log",
                            "rm -f /system/etc/init.d/88checkinit",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                    );
                    try {
                        RootTools.getShell(true).add(command1);
                        statusik.setVisibility(View.GONE);
                    } catch (IOException | RootDeniedException | TimeoutException ex) {
                        ex.printStackTrace();
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                    }
                } else {
                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                }
            }

        });


        ////////////////////////////////////////////
        ////// CHECK INIT.D ////////////////////////
        ///////////////////////////////////////////
        buttoncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttoncheck.setEnabled(false);
                buttoncheck.setVisibility(View.GONE);
                buttoncheckremove.setVisibility(View.VISIBLE);

                if (RootTools.isAccessGiven()) {
                    Command command1 = new Command(0,
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                            "rm -f /data/checkinit.log",
                            "cp /data/data/com.nowenui.systemtweakerfree/files/88checkinit /system/etc/init.d/",
                            "chmod 777 /system/etc/init.d/88checkinit",
                            "reboot",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                    );
                    try {
                        RootTools.getShell(true).add(command1);

                    } catch (IOException | RootDeniedException | TimeoutException ex) {
                        ex.printStackTrace();
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                    }
                } else {
                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                }
            }

        });


        if (new File("/data/checkinit.log").exists()) {
            statusik.setText("INIT.D  WORKING GOOD!");
            statusik.setTextColor(Color.GREEN);
            statusik.setTextSize(20);
            buttoncheck.setVisibility(View.GONE);
        } else {
            statusik.setText(R.string.statusssl);
            statusik.setTextColor(Color.RED);
            statusik.setTextSize(20);
            buttoncheckremove.setVisibility(View.GONE);
        }

        ///////////////////////////////////////////////
        ////// Push all information to user ///////////
        ///////////////////////////////////////////////
        tv1.setText(res.getString(R.string.yourdevice) + " "
                + DeviceName.getDeviceName() + "\n" + res.getString(R.string.model) + " " + android.os.Build.MODEL
                + "\n" + res.getString(R.string.serial) + " " + android.os.Build.SERIAL + "\n"
                + res.getString(R.string.cod) + " " + easydevicemod.getDevice() + "\n" + "\n"
                + res.getString(R.string.android) + " " + android.os.Build.VERSION.RELEASE + " "
                + easydevicemod.getOSCodename() + "\n" + res.getString(R.string.boot)
                + android.os.Build.BOOTLOADER + " " + "\n" + res.getString(R.string.radio)
                + " " + easydevicemod.getRadioVer() + "\n" + res.getString(R.string.rom) + " "
                + easydevicemod.getBuildVersionIncremental() + "\n" + res.getString(R.string.language) + " " + easydevicemod.getLanguage().toUpperCase()
                + "\n" + "• Android ID: " + easyIdMod.getAndroidID() + "\n" + "• PseudoID: "
                + easyIdMod.getPseudoUniqueID() + "\n"
                + res.getString(R.string.sdk) + " " + easydevicemod.getBuildVersionSDK() + "\n");
        tv2.setText(res.getString(R.string.processor) + " " + getCPUName() + "\n"
                + res.getString(R.string.processortype) + " " + android.os.Build.CPU_ABI + "\n");
        tv3.setText("• RAM: " + easyMemoryMod.getTotalRAM() + " Mb"
                + "\n" + "• ROM: " + easyMemoryMod.getTotalInternalMemorySize() + " Mb" + "\n"
                + "• Heap Size: " + getHeapSize().trim() + "\n");
        tv4.setText(res.getString(R.string.batthealth) + " " + BatteryHealth + "\n"
                + res.getString(R.string.chargerconn) + " " + easyBatteryMod.isDeviceCharging() + "\n"
                + res.getString(R.string.batterytype) + " " + easyBatteryMod.getBatteryTechnology() + "\n"
                + res.getString(R.string.voltage) + " " + easyBatteryMod.getBatteryVoltage() + " mV" + "\n"
                + res.getString(R.string.batterytemp) + " " + easyBatteryMod.getBatteryTemperature() + " °C"
                + "\n" + res.getString(R.string.levelbatt) + " " + easyBatteryMod.getBatteryPercentage() + " %");
        tv5.setText(res.getString(R.string.resolution) + " "
                + easyDisplayMod.getResolution() + "\n" + "• DPI: "
                + easyDisplayMod.getDensity() + "\n"
                + res.getString(R.string.cll) + " " + Math.round(easyDisplayMod.getPhysicalSize() * Math.pow(10, 3)) / Math.pow(10, 3) + "\n"
                + "• Screen FPS: " + easyDisplayMod.getRefreshRate() + "\n");

    }

}