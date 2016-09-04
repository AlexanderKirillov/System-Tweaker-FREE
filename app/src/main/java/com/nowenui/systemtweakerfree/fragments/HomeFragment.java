package com.nowenui.systemtweakerfree.fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nowenui.systemtweakerfree.R;
import com.nowenui.systemtweakerfree.SplashActivity;

import java.io.File;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment priceListFragment = new HomeFragment();

        if (bundle != null) {
            priceListFragment.setArguments(bundle);
        }

        return priceListFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, parent, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView textview = (TextView) view.findViewById(R.id.textView);
        TextView textview2 = (TextView) view.findViewById(R.id.textView2);
        TextView textview10 = (TextView) view.findViewById(R.id.textView10);
        TextView textview11 = (TextView) view.findViewById(R.id.textView11);


        if (SplashActivity.checksu == 1) {
            textview.setText(R.string.root);
            textview.setBackgroundResource(R.color.textview1good);
            textview.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.successs, 0, 0, 0);
        } else {
            textview.setText(R.string.rootbad);
            textview.setBackgroundResource(R.color.textview1bad);
            textview.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.bad, 0, 0, 0);
        }

        if (SplashActivity.checkbusy == 1) {
            textview2.setText(R.string.busybox);
            textview2.setBackgroundResource(R.color.textview1good);
            textview2.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.successs, 0, 0, 0);
        } else {
            textview2.setText(R.string.busyboxbad);
            textview2.setBackgroundResource(R.color.textview1bad);
            textview2.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.bad, 0, 0, 0);
        }

        boolean installed = xposedappInstalledOrNot("de.robv.android.xposed.installer");

        textview10.setText(isXposedSupport());
        if (textview10.getText().toString().contains("XPOSED INSTALLED!") || textview10.getText().toString().contains("XPOSED УСТАНОВЛЕН!") || installed) {
            textview10.setBackgroundResource(R.color.textview1good);
            textview10.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.successs, 0, 0, 0);
        } else {
            textview10.setBackgroundResource(R.color.textview1bad);
            textview10.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.bad, 0, 0, 0);
        }
        textview11.setText(isInitdSupport());
        if (textview11.getText().toString().contains("INIT.D WORKING!") || textview11.getText().toString().contains("INIT.D РАБОТАЕТ!")) {
            textview11.setBackgroundResource(R.color.textview1good);
            textview11.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.successs, 0, 0, 0);
        } else {
            textview11.setBackgroundResource(R.color.textview1bad);
            textview11.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.bad, 0, 0, 0);
        }
    }

    public int isXposedSupport() {
        String xposed = "XposedBridge.jar";
        String[] locations = {"/system/framework/", "/magisk/xposed/system/framework/"};
        boolean installed2 = xposedappInstalledOrNot("de.robv.android.xposed.installer");
        for (String location : locations) {
            if (new File(location + xposed).exists() || installed2) {
                return R.string.xposed;
            }
        }
        return R.string.xposedbad;
    }

    public int isInitdSupport() {
        File f = new File("/system/etc/init.d");
        File f1 = new File("/system/su.d");
        File f2 = new File("/su/su.d");
        if ((f.exists()) && (f.isDirectory()) || (f1.exists()) && (f1.isDirectory()) || (f2.exists()) && (f2.isDirectory())) {
            return R.string.initd;
        }
        return R.string.initdbad;
    }

    private boolean xposedappInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}