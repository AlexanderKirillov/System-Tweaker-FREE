package com.nowenui.systemtweakerfree.fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jaredrummler.android.device.DeviceName;
import com.nowenui.systemtweakerfree.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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