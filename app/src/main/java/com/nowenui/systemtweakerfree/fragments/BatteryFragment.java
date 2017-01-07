package com.nowenui.systemtweakerfree.fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mrengineer13.snackbar.SnackBar;
import com.nowenui.systemtweakerfree.R;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BatteryFragment extends Fragment {
    private TextView status = null;
    private TextView level = null;
    private boolean isClicked;
    private Button calibrate;
    public BroadcastReceiver onBattery = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            final int pct =
                    100 * intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 1)
                            / intent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);

            level.setText(String.valueOf(pct) + "%");

            if ((pct == 100)) {
                level.setBackgroundResource(R.drawable.roundbuttongood);
            } else {
                level.setBackgroundResource(R.drawable.roundbuttonbad);
                calibrate.setBackgroundResource(R.drawable.roundbuttonfuck);
                calibrate.setEnabled(false);
            }

            switch (intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    status.setText(R.string.yes);
                    status.setBackgroundResource(R.drawable.roundbuttongood);
                    calibrate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            if (isClicked) {
                                return;
                            }
                            isClicked = true;
                            v.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    isClicked = false;
                                }
                            }, 1000);

                            calibrate.setEnabled(false);
                            calibrate.setBackgroundResource(R.drawable.roundbuttonfuck);

                            if (pct == 100) {
                                calibrate.setBackgroundResource(R.drawable.roundbuttoncal);
                                if (RootTools.isRootAvailable()) {
                                    if (RootTools.isAccessGiven()) {
                                        Command command1 = new Command(0,
                                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /data",
                                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /data", "mount -o rw,remount /data",
                                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /data",
                                                "rm -f /data/system/batterystats.bin",
                                                "rm -f /data/system/batterystats-checkin.bin",
                                                "rm -f /data/system/batterystats-daily.xml",
                                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /data",
                                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /data", "mount -o ro,remount /data",
                                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /data"
                                        );
                                        try {
                                            RootTools.getShell(true).add(command1);
                                            final ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                                            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                            dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                            dialog.setIndeterminate(false);
                                            dialog.setCancelable(false);
                                            dialog.show();

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                public void run() {
                                                    dialog.dismiss();
                                                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.calsucess)).withBackgroundColorId(R.color.textview1good).show();
                                                    new AlertDialog.Builder(v.getContext())
                                                            .setTitle(R.string.reboot)
                                                            .setMessage(R.string.rebootdialog)
                                                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    if (RootTools.isRootAvailable()) {
                                                                        if (RootTools.isAccessGiven()) {
                                                                            Command command1 = new Command(0,
                                                                                    "reboot");
                                                                            try {
                                                                                new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.reboot)).withBackgroundColorId(R.color.textview1good).show();
                                                                                RootTools.getShell(true).add(command1);
                                                                            } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                                ex.printStackTrace();
                                                                                new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                                            }
                                                                        } else {
                                                                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.erroroot)).withBackgroundColorId(R.color.textview1bad).show();
                                                                        }

                                                                    } else {
                                                                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.erroroot)).withBackgroundColorId(R.color.textview1bad).show();
                                                                    }
                                                                }
                                                            })
                                                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            })
                                                            .setIcon(R.drawable.warning)
                                                            .show();
                                                }
                                            }, 4000);

                                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                                            ex.printStackTrace();
                                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                        }
                                    } else {
                                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.erroroot)).withBackgroundColorId(R.color.textview1bad).show();
                                    }

                                } else {
                                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.erroroot)).withBackgroundColorId(R.color.textview1bad).show();
                                }

                            } else
                                calibrate.setEnabled(false);
                            calibrate.setBackgroundResource(R.drawable.roundbuttonfuck);
                        }

                    });
                    break;

                case BatteryManager.BATTERY_STATUS_FULL:
                    int plugged =
                            intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

                    if (plugged == BatteryManager.BATTERY_PLUGGED_AC
                            || plugged == BatteryManager.BATTERY_PLUGGED_USB) {
                        status.setText(R.string.yes);
                        calibrate.setEnabled(true);
                        calibrate.setBackgroundResource(R.drawable.roundbuttoncal);
                        status.setBackgroundResource(R.drawable.roundbuttongood);
                        calibrate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                if (isClicked) {
                                    return;
                                }
                                isClicked = true;
                                v.postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        isClicked = false;
                                    }
                                }, 1000);

                                calibrate.setEnabled(false);
                                calibrate.setBackgroundResource(R.drawable.roundbuttonfuck);
                                if (pct == 100) {
                                    calibrate.setBackgroundResource(R.drawable.roundbuttoncal);
                                    if (RootTools.isRootAvailable()) {
                                        if (RootTools.isAccessGiven()) {
                                            Command command1 = new Command(0,
                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /data",
                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /data", "mount -o rw,remount /data",
                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /data",
                                                    "rm -f /data/system/batterystats.bin",
                                                    "rm -f /data/system/batterystats-checkin.bin",
                                                    "rm -f /data/system/batterystats-daily.xml",
                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /data",
                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /data", "mount -o ro,remount /data",
                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /data");
                                            try {
                                                RootTools.getShell(true).add(command1);
                                                final ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                dialog.setIndeterminate(false);
                                                dialog.setCancelable(false);
                                                dialog.show();

                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    public void run() {
                                                        dialog.dismiss();
                                                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.calsucess)).withBackgroundColorId(R.color.textview1good).show();
                                                        new AlertDialog.Builder(v.getContext())
                                                                .setTitle(R.string.reboot)
                                                                .setMessage(R.string.rebootdialog)
                                                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        if (RootTools.isRootAvailable()) {
                                                                            if (RootTools.isAccessGiven()) {
                                                                                Command command1 = new Command(0,
                                                                                        "reboot");
                                                                                try {
                                                                                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.reboot)).withBackgroundColorId(R.color.textview1good).show();
                                                                                    RootTools.getShell(true).add(command1);
                                                                                } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                                    ex.printStackTrace();
                                                                                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                                                }
                                                                            } else {
                                                                                new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.erroroot)).withBackgroundColorId(R.color.textview1bad).show();
                                                                            }

                                                                        } else {
                                                                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.erroroot)).withBackgroundColorId(R.color.textview1bad).show();
                                                                        }
                                                                    }
                                                                })
                                                                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        dialog.dismiss();
                                                                    }
                                                                })
                                                                .setIcon(R.drawable.warning)
                                                                .show();
                                                    }
                                                }, 4000);

                                            } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                ex.printStackTrace();
                                                new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                            }
                                        } else {
                                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.erroroot)).withBackgroundColorId(R.color.textview1bad).show();
                                        }

                                    } else {
                                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.erroroot)).withBackgroundColorId(R.color.textview1bad).show();
                                    }

                                } else {
                                    calibrate.setEnabled(false);
                                    calibrate.setBackgroundResource(R.drawable.roundbuttonfuck);
                                }
                            }

                        });
                    } else {
                        status.setText(R.string.no);
                        calibrate.setBackgroundResource(R.drawable.roundbuttonfuck);
                        calibrate.setEnabled(false);

                    }
                    break;


                default:
                    status.setText(R.string.no);
                    calibrate.setEnabled(false);
                    status.setBackgroundResource(R.drawable.roundbuttonbad);
                    calibrate.setBackgroundResource(R.drawable.roundbuttonfuck);
                    break;


            }
        }
    };

    public static BatteryFragment newInstance(Bundle bundle) {
        BatteryFragment messagesFragment = new BatteryFragment();

        if (bundle != null) {
            messagesFragment.setArguments(bundle);
        }

        return messagesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_user:
                new android.app.AlertDialog.Builder(this.getContext())
                        .setTitle(R.string.reboot)
                        .setMessage(R.string.rebootactionbar)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot"});
                                    proc.waitFor();
                                } catch (Exception ex) {
                                    new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED!").withBackgroundColorId(R.color.textview1bad).show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.warning)
                        .show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {

        RootTools.debugMode = false;

        View result = inflater.inflate(R.layout.batt, parent, false);

        status = (TextView) result.findViewById(R.id.status);
        level = (TextView) result.findViewById(R.id.level);

        calibrate = (Button) result.findViewById(R.id.calibrate);

        calibrate.setBackgroundResource(R.drawable.roundbuttoncal);
        calibrate.setTextColor(Color.WHITE);
        calibrate.setTextSize(20);

        return (result);
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter f = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        getActivity().registerReceiver(onBattery, f);
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(onBattery);

        super.onPause();
    }
}