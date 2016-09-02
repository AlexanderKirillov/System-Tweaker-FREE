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

import github.nisrulz.easydeviceinfo.EasyBatteryMod;
import github.nisrulz.easydeviceinfo.EasyDeviceMod;
import github.nisrulz.easydeviceinfo.EasyDisplayMod;
import github.nisrulz.easydeviceinfo.EasyIdMod;

public class AboutFragment extends Fragment {

    private String BatteryHealth;

    public static AboutFragment newInstance(Bundle bundle) {
        AboutFragment messagesFragment = new AboutFragment();

        if (bundle != null) {
            messagesFragment.setArguments(bundle);
        }

        return messagesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        ;

        View view = inflater.inflate(R.layout.about_device, parent, false);

        EasyBatteryMod easyBatteryMod = new EasyBatteryMod(getContext());
        int battery_health = easyBatteryMod.getBatteryHealth();
        Resources res = getResources();
        switch (battery_health) {
            case EasyBatteryMod.HEALTH_GOOD:
                BatteryHealth = res.getString(R.string.goodbatterystatus);
                break;
            case EasyBatteryMod.HEALTH_HAVING_ISSUES:
                BatteryHealth = res.getString(R.string.problems);
                break;
            default:
                BatteryHealth = res.getString(R.string.problems);
                break;
        }

        return view;
    }

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


    public synchronized String getCPUName() {
        String CPUName = "";

        String[] lines = ProcessorInfo().split("\n");

        for (int i = 0; i < lines.length; i++) {

            String temp = lines[i];

            if (lines[i].contains("Hardware\t:")) {

                CPUName = lines[i].replace("Hardware\t: ", "");
                break;
            }
        }
        return CPUName;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        EasyBatteryMod easyBatteryMod = new EasyBatteryMod(getContext());

        EasyIdMod easyIdMod = new EasyIdMod(getContext());
        EasyDisplayMod easyDisplayMod = new EasyDisplayMod(getContext());
        MemInfo easyMemoryMod = new MemInfo(getContext());
        EasyDeviceMod easydevicemod = new EasyDeviceMod(getContext());
        TextView tv1 = (TextView) view.findViewById(R.id.textviewabout1);
        TextView tv2 = (TextView) view.findViewById(R.id.textviewabout2);
        TextView tv3 = (TextView) view.findViewById(R.id.textviewabout3);
        TextView tv4 = (TextView) view.findViewById(R.id.textviewabout4);
        TextView tv5 = (TextView) view.findViewById(R.id.textviewabout5);

        Button button = (Button) view.findViewById(R.id.button);
        button.setBackgroundResource(R.drawable.roundbuttoncal);
        button.setTextColor(Color.WHITE);
        button.setEnabled(false);

        Button button2 = (Button) view.findViewById(R.id.button2);
        button2.setBackgroundResource(R.drawable.roundbuttoncal);
        button2.setTextColor(Color.WHITE);
        button2.setEnabled(false);

        Button button3 = (Button) view.findViewById(R.id.button3);
        button3.setBackgroundResource(R.drawable.roundbuttoncal);
        button3.setTextColor(Color.WHITE);
        button3.setEnabled(false);

        Button button4 = (Button) view.findViewById(R.id.button4);
        button4.setBackgroundResource(R.drawable.roundbuttoncal);
        button4.setTextColor(Color.WHITE);
        button4.setEnabled(false);

        Button button5 = (Button) view.findViewById(R.id.button5);
        button5.setBackgroundResource(R.drawable.roundbuttoncal);
        button5.setTextColor(Color.WHITE);
        button5.setEnabled(false);

        Resources res = getResources();


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
                + easyIdMod.getPseudoUniqueID() + "\n" + "• GSF ID: " + easyIdMod.getGSFID()
                + "\n"
                + res.getString(R.string.sdk) + " " + easydevicemod.getBuildVersionSDK() + "\n");
        tv2.setText(res.getString(R.string.processor) + " " + getCPUName() + "\n"
                + res.getString(R.string.processortype) + " " + android.os.Build.CPU_ABI + "\n");
        tv3.setText("• RAM: " + easyMemoryMod.getTotalRAM() + " Mb"
                + "\n" + "• ROM: " + easyMemoryMod.getTotalInternalMemorySize() + " Mb" + "\n"
                + "• Heap Size: " + HeapSize.HardwareInfo.getHeapSize().trim() + "\n");
        tv4.setText(res.getString(R.string.batthealth) + " " + BatteryHealth + "\n"
                + res.getString(R.string.chargerconn) + " " + easyBatteryMod.isDeviceCharging() + "\n"
                + res.getString(R.string.batterytype) + " " + easyBatteryMod.getBatteryTechnology() + "\n"
                + res.getString(R.string.voltage) + " " + easyBatteryMod.getBatteryVoltage() + " mV" + "\n"
                + res.getString(R.string.batterytemp) + " " + easyBatteryMod.getBatteryTemperature() + " °C"
                + "\n" + res.getString(R.string.levelbatt) + " " + easyBatteryMod.getBatteryPercentage() + " %");
        tv5.setText(res.getString(R.string.resolution) + " "
                + easyDisplayMod.getResolution() + "\n" + "• DPI: "
                + easyDisplayMod.getDensity() + "\n");
    }

}