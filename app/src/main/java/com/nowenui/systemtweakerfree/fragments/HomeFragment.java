package com.nowenui.systemtweakerfree.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
            textview2.setOnTouchListener(new View.OnTouchListener() {
                long oldTime = 0;

                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (System.currentTimeMillis() - oldTime < 300) {
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
                        oldTime = System.currentTimeMillis();

                    }
                    return true;
                }
            });
        } else {
            textview2.setText(R.string.busyboxbad);
            textview2.setBackgroundResource(R.color.textview1bad);
            textview2.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.bad, 0, 0, 0);
        }


        textview11.setText(isInitdSupport());
        if (textview11.getText().toString().contains("INIT.D WORKING!") || textview11.getText().toString().contains("INIT.D РАБОТАЕТ!")) {
            textview11.setBackgroundResource(R.color.textview1good);
            textview11.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.successs, 0, 0, 0);
            textview11.setOnTouchListener(new View.OnTouchListener() {
                long oldTime = 0;

                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (System.currentTimeMillis() - oldTime < 300) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.init1)
                                    .setMessage(R.string.init2)
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
                        oldTime = System.currentTimeMillis();

                    }
                    return true;
                }
            });
        } else {
            textview11.setBackgroundResource(R.color.textview1bad);
            textview11.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.bad, 0, 0, 0);
        }
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

}