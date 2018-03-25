package com.nowenui.systemtweakerfree.fragments;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nowenui.systemtweakerfree.R;
import com.nowenui.systemtweakerfree.SplashActivity;
import com.nowenui.systemtweakerfree.Utility;

import java.io.File;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment Home = new HomeFragment();

        if (bundle != null) {
            Home.setArguments(bundle);
        }

        return Home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        TextView textview = view.findViewById(R.id.textView);
        TextView textview2 = view.findViewById(R.id.textView2);
        TextView textview11 = view.findViewById(R.id.textView11);
        TextView sel = view.findViewById(R.id.sel);


        //////////////////////////////////
        ////// Check SuperUser ///////////
        //////////////////////////////////
        if (SplashActivity.checksu == 1) {
            textview.setText(R.string.root);
            textview.setBackgroundResource(R.drawable.roundbuttonmaingood);
            textview.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.successs, 0, 0, 0);
        } else {
            textview.setText(R.string.rootbad);
            textview.setBackgroundResource(R.drawable.roundbuttonbad);
            textview.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.bad, 0, 0, 0);
        }

        //////////////////////////////////
        ////// SELinux check /////////////
        //////////////////////////////////

        if ((Build.VERSION.SDK_INT >= 18) && (SplashActivity.isSELinuxPresent() == 1)) {
            if (SplashActivity.selinuxstatus == 1) {
                sel.setText("SELINUX: PERMISSIVE");
                sel.setBackgroundResource(R.drawable.roundbuttonmaingood);
                sel.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.successs, 0, 0, 0);
            } else {
                sel.setText("SELINUX: ENFORCING");
                sel.setBackgroundResource(R.drawable.roundbuttonwarning);
                sel.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.bad, 0, 0, 0);
            }
        } else {
            sel.setVisibility(View.GONE);
        }


        ///////////////////////////////
        ////// Check BusyBox //////////
        ///////////////////////////////
        if (SplashActivity.checkbusy == 1) {
            textview2.setText(R.string.busybox);
            textview2.setBackgroundResource(R.drawable.roundbuttonmaingood);
            textview2.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.successs, 0, 0, 0);
            textview2.setOnTouchListener(new View.OnTouchListener() {
                long oldTime = 0;

                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (System.currentTimeMillis() - oldTime < 300) {
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle(R.string.busy1)
                                        .setMessage(R.string.busy2)
                                        .setIcon(R.drawable.info).setCancelable(true)
                                        .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                builder.setTitle(R.string.busy1)
                                        .setMessage(R.string.busy2)
                                        .setIcon(R.drawable.info).setCancelable(true)
                                        .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                builder.setTitle(R.string.busy1)
                                        .setMessage(R.string.busy2)
                                        .setIcon(R.drawable.info).setCancelable(true)
                                        .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }
                        oldTime = System.currentTimeMillis();

                    }
                    return true;
                }
            });
        } else {
            textview2.setText(R.string.busyboxbad);
            textview2.setBackgroundResource(R.drawable.roundbuttonbad);
            textview2.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.bad, 0, 0, 0);
        }

        ///////////////////////////////////////
        ////// Check init.d support ///////////
        ///////////////////////////////////////
        textview11.setText(isInitdSupport());
        if (textview11.getText().toString().contains("INIT.D WORKING!") || textview11.getText().toString().contains("ПАПКА INIT.D \nПРИСУТСТВУЕТ!")) {
            textview11.setBackgroundResource(R.drawable.roundbuttonmaingood);
            textview11.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.successs, 0, 0, 0);
        } else {
            textview11.setBackgroundResource(R.drawable.roundbuttonbad);
            textview11.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.bad, 0, 0, 0);
        }
    }

    public int isInitdSupport() {
        File sud = new File("/system/su.d");
        File initd = new File("/system/etc/init.d");
        if ((initd.exists()) && (initd.isDirectory()) || (sud.exists()) && (sud.isDirectory())) {
            return R.string.initd;
        }
        return R.string.initdbad;
    }


}