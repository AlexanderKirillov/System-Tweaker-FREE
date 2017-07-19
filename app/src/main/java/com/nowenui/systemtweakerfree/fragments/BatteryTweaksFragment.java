package com.nowenui.systemtweakerfree.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.github.mrengineer13.snackbar.SnackBar;
import com.nowenui.systemtweakerfree.R;
import com.nowenui.systemtweakerfree.Utility;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BatteryTweaksFragment extends Fragment {

    public static BatteryTweaksFragment newInstance(Bundle bundle) {
        BatteryTweaksFragment BatteryTweaks = new BatteryTweaksFragment();

        if (bundle != null) {
            BatteryTweaks.setArguments(bundle);
        }

        return BatteryTweaks;
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
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(this.getContext())
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
                if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark))
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
                if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack))
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_batterytweaks, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        /////////////////////////////////////////////////
        ////// build.prop ->>> String........ ///////////
        /////////////////////////////////////////////////
        File file = new File("/system/build.prop");

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
        }

        ////////////////////////////////////////////////
        ////// Google Services Tweak........ ///////////
        ////////////////////////////////////////////////
        CheckBox checkbox = view.findViewById(R.id.checkBox);
        String check1 = "/etc/init.d/05FixGoogleServicedrain";
        String check1a = "/system/etc/init.d/05FixGoogleServicedrain";
        if (new File(Environment.getRootDirectory() + check1).exists() || new File(check1a).exists() || new File(Environment.getRootDirectory() + check1a).exists()) {
            checkbox.setChecked(true);
        } else {
            checkbox.setChecked(false);
        }
        checkbox.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery1)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery1)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery1)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }

                return true;
            }
        });
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView,
                                                                             boolean isChecked) {

                                                    if (isChecked) {

                                                        if (RootTools.isAccessGiven()) {
                                                            Command command1 = new Command(0,
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                    "cp /data/data/com.nowenui.systemtweakerfree/files/05FixGoogleServicedrain /system/etc/init.d/",
                                                                    "chmod 777 /system/etc/init.d/05FixGoogleServicedrain",
                                                                    "/system/etc/init.d/05FixGoogleServicedrain",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                                                            try {
                                                                RootTools.getShell(true).add(command1);
                                                                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {

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
                                                                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                        }
                                                                    }, 6000);
                                                                }
                                                                if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {

                                                                    final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                                    dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                                    dialog.setIndeterminate(false);
                                                                    dialog.setCancelable(false);
                                                                    dialog.show();

                                                                    Handler handler = new Handler();
                                                                    handler.postDelayed(new Runnable() {
                                                                        public void run() {
                                                                            dialog.dismiss();
                                                                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                        }
                                                                    }, 6000);
                                                                }
                                                                if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {

                                                                    final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                                    dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                                    dialog.setIndeterminate(false);
                                                                    dialog.setCancelable(false);
                                                                    dialog.show();

                                                                    Handler handler = new Handler();
                                                                    handler.postDelayed(new Runnable() {
                                                                        public void run() {
                                                                            dialog.dismiss();
                                                                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                        }
                                                                    }, 6000);
                                                                }
                                                            } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                ex.printStackTrace();
                                                                new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                            }
                                                        } else {
                                                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                        }


                                                    } else {

                                                        if (RootTools.isAccessGiven()) {
                                                            Command command1 = new Command(0,
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                    "rm -f /system/etc/init.d/05FixGoogleServicedrain",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                                                            try {
                                                                RootTools.getShell(true).add(command1);
                                                                new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                            } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                ex.printStackTrace();
                                                                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {

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
                                                                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                        }
                                                                    }, 6000);
                                                                }
                                                                if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {

                                                                    final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                                    dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                                    dialog.setIndeterminate(false);
                                                                    dialog.setCancelable(false);
                                                                    dialog.show();

                                                                    Handler handler = new Handler();
                                                                    handler.postDelayed(new Runnable() {
                                                                        public void run() {
                                                                            dialog.dismiss();
                                                                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                        }
                                                                    }, 6000);
                                                                }
                                                                if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {

                                                                    final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                                    dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                                    dialog.setIndeterminate(false);
                                                                    dialog.setCancelable(false);
                                                                    dialog.show();

                                                                    Handler handler = new Handler();
                                                                    handler.postDelayed(new Runnable() {
                                                                        public void run() {
                                                                            dialog.dismiss();
                                                                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                        }
                                                                    }, 6000);
                                                                }
                                                            }
                                                        } else {
                                                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                        }

                                                    }

                                                }
                                            }

        );


        ////////////////////////////////////////////////////////////////
        ////// Improve powersaving. Fixes VM overuse........ ///////////
        ////////////////////////////////////////////////////////////////
        CheckBox checkbox2 = view.findViewById(R.id.checkBox2);
        String check2 = "/etc/init.d/00BatteryImprove";
        String check2a = "/system/etc/init.d/00BatteryImprove";
        if (new File(Environment.getRootDirectory() + check2).exists() || new File(check2a).exists() || new File(Environment.getRootDirectory() + check2a).exists()) {
            checkbox2.setChecked(true);
        } else {
            checkbox2.setChecked(false);
        }
        checkbox2.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery2)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery2)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery2)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }

                return true;
            }
        });
        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {
                                                     if (isChecked) {


                                                         if (RootTools.isAccessGiven()) {
                                                             Command command1 = new Command(0,
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                     "cp /data/data/com.nowenui.systemtweakerfree/files/00BatteryImprove /system/etc/init.d/",
                                                                     "chmod 777 /system/etc/init.d/00BatteryImprove",
                                                                     "/system/etc/init.d/00BatteryImprove",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                                                             try {

                                                                 RootTools.getShell(true).add(command1);
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                             } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                 ex.printStackTrace();
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                             }
                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }

                                                     } else {

                                                         if (RootTools.isAccessGiven()) {
                                                             Command command1 = new Command(0,
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                     "rm -f /system/etc/init.d/00BatteryImprove",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                                                             try {
                                                                 RootTools.getShell(true).add(command1);
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                             } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                 ex.printStackTrace();
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                             }
                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     }

                                                 }
                                             }

        );

        ////////////////////////////////
        ////// DynBS........ ///////////
        ////////////////////////////////
        CheckBox dynbs = view.findViewById(R.id.dynbs);
        String check11 = "/etc/init.d/99dynbsd";
        String check11a = "/system/etc/init.d/99dynbsd";
        if (new File(Environment.getRootDirectory() + check11).exists() || new File(check11a).exists() || new File(Environment.getRootDirectory() + check11a).exists()) {
            dynbs.setChecked(true);
        } else {
            dynbs.setChecked(false);
        }
        dynbs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView,
                                                                          boolean isChecked) {
                                                 if (isChecked) {

                                                     if (RootTools.isAccessGiven()) {
                                                         Command command1 = new Command(0,
                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                 "cp /data/data/com.nowenui.systemtweakerfree/files/dynbsd /system/xbin/dynbsd",
                                                                 "chmod 755 /system/xbin/dynbsd",
                                                                 "cp /data/data/com.nowenui.systemtweakerfree/files/99dynbsd /system/etc/init.d/99dynbsd",
                                                                 "chmod 777 /system/etc/init.d/99dynbsd",
                                                                 "/system/etc/init.d/99dynbsd",
                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                                                         );
                                                         try {
                                                             RootTools.getShell(true).add(command1);
                                                             if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {

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
                                                                         new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                     }
                                                                 }, 4000);
                                                             }
                                                             if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {

                                                                 final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                                 dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                                 dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                                 dialog.setIndeterminate(false);
                                                                 dialog.setCancelable(false);
                                                                 dialog.show();

                                                                 Handler handler = new Handler();
                                                                 handler.postDelayed(new Runnable() {
                                                                     public void run() {
                                                                         dialog.dismiss();
                                                                         new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                     }
                                                                 }, 4000);
                                                             }
                                                             if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {

                                                                 final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                                 dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                                 dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                                 dialog.setIndeterminate(false);
                                                                 dialog.setCancelable(false);
                                                                 dialog.show();

                                                                 Handler handler = new Handler();
                                                                 handler.postDelayed(new Runnable() {
                                                                     public void run() {
                                                                         dialog.dismiss();
                                                                         new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                     }
                                                                 }, 4000);
                                                             }


                                                         } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                             ex.printStackTrace();
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     } else {
                                                         new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                     }

                                                 } else {

                                                     if (RootTools.isAccessGiven()) {
                                                         Command command1 = new Command(0,
                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                 "rm -f /system/xbin/dynbsd",
                                                                 "rm -f /system/etc/init.d/99dynbsd",
                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                                                         );
                                                         try {
                                                             RootTools.getShell(true).add(command1);
                                                             if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {

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
                                                                         new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                     }
                                                                 }, 4000);
                                                             }
                                                             if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {

                                                                 final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                                 dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                                 dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                                 dialog.setIndeterminate(false);
                                                                 dialog.setCancelable(false);
                                                                 dialog.show();

                                                                 Handler handler = new Handler();
                                                                 handler.postDelayed(new Runnable() {
                                                                     public void run() {
                                                                         dialog.dismiss();
                                                                         new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                     }
                                                                 }, 4000);
                                                             }
                                                             if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {

                                                                 final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                                 dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                                 dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                                 dialog.setIndeterminate(false);
                                                                 dialog.setCancelable(false);
                                                                 dialog.show();

                                                                 Handler handler = new Handler();
                                                                 handler.postDelayed(new Runnable() {
                                                                     public void run() {
                                                                         dialog.dismiss();
                                                                         new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                     }
                                                                 }, 4000);
                                                             }


                                                         } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                             ex.printStackTrace();
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     } else {
                                                         new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                     }
                                                 }

                                             }
                                         }

        );

        ///////////////////////////////////////////////////
        ////// Wifi Scan Interval tweak........ ///////////
        ///////////////////////////////////////////////////
        CheckBox checkbox3 = view.findViewById(R.id.checkBox3);
        if (text.toString().contains("wifi.supplicant_scan_interval=220")) {
            checkbox3.setChecked(true);
        } else {
            checkbox3.setChecked(false);
        }
        checkbox3.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery3)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery3)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery3)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }

                return true;
            }
        });
        checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {
                                                     if (isChecked) {

                                                         if (RootTools.isAccessGiven()) {
                                                             Command command1 = new Command(0,
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/wifi.supplicant_scan_interval/d' /system/build.prop",
                                                                     "echo \"wifi.supplicant_scan_interval=220\" >> /system/build.prop",
                                                                     "setprop wifi.supplicant_scan_interval 220",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                                                             try {
                                                                 RootTools.getShell(true).add(command1);
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                             } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                 ex.printStackTrace();
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                             }
                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }

                                                     } else {

                                                         if (RootTools.isAccessGiven()) {
                                                             Command command1 = new Command(0,
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/wifi.supplicant_scan_interval/d' /system/build.prop",
                                                                     "echo \"wifi.supplicant_scan_interval=160\" >> /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                                                             );
                                                             try {
                                                                 RootTools.getShell(true).add(command1);
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                             } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                 ex.printStackTrace();
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                             }
                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     }

                                                 }
                                             }

        );

        ////////////////////////////////////////////////
        ////// Enable Power Collapse........ ///////////
        ////////////////////////////////////////////////
        CheckBox checkbox4 = view.findViewById(R.id.checkBox4);
        String lpm = "/etc/init.d/lpm";
        String lpma = "/system/etc/init.d/lpm";
        if (text.toString().contains("ro.ril.disable.power.collapse=0")
                && text.toString().contains("ro.vold.umsdirtyratio=20")
                && text.toString().contains("pm.sleep_mode=1") && (new File(Environment.getRootDirectory() + lpm).exists() || new File(lpma).exists() || new File(Environment.getRootDirectory() + lpma).exists())) {
            checkbox4.setChecked(true);
        } else {
            checkbox4.setChecked(false);
        }
        checkbox4.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {

                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery4)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery4)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery4)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }

                return true;
            }
        });
        checkbox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {

                                                     if (isChecked) {


                                                         if (RootTools.isAccessGiven()) {
                                                             Command command1 = new Command(0,
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.power.collapse/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.disable.power.collapse/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/pm.sleep_mode/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.vold.umsdirtyratio/d' /system/build.prop",
                                                                     "echo \"ro.ril.disable.power.collapse=0\" >> /system/build.prop",
                                                                     "echo \"ro.vold.umsdirtyratio=20\" >> /system/build.prop",
                                                                     "echo \"pm.sleep_mode=1\" >> /system/build.prop",
                                                                     "setprop ro.ril.disable.power.collapse 0",
                                                                     "setprop ro.vold.umsdirtyratio 20",
                                                                     "setprop pm.sleep_mode 1",
                                                                     "cp /data/data/com.nowenui.systemtweakerfree/files/lpm /system/etc/init.d/",
                                                                     "chmod 777 /system/etc/init.d/lpm",
                                                                     "/system/etc/init.d/lpm",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                                                             try {
                                                                 RootTools.getShell(true).add(command1);
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                             } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                 ex.printStackTrace();
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                             }
                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }


                                                     } else {

                                                         if (RootTools.isAccessGiven()) {
                                                             Command command1 = new Command(0,
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.disable.power.collapse/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.vold.umsdirtyratio/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/pm.sleep_mode/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox rm -f /system/etc/init.d/lpm",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                                                             );
                                                             try {
                                                                 RootTools.getShell(true).add(command1);
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                             } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                 ex.printStackTrace();
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                             }
                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     }

                                                 }
                                             }

        );


        //////////////////////////////////////////////
        ////// Enable Power Supply........ ///////////
        //////////////////////////////////////////////
        CheckBox checkbox5 = view.findViewById(R.id.checkBox5);
        if (text.toString().contains("power_supply.wakeup=enable")) {
            checkbox5.setChecked(true);
        } else {
            checkbox5.setChecked(false);
        }
        checkbox5.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery5)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery5)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery5)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }

                return true;
            }
        });
        checkbox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {

                                                     if (isChecked) {


                                                         if (RootTools.isAccessGiven()) {
                                                             Command command1 = new Command(0,
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/power_supply.wakeup/d' /system/build.prop",
                                                                     "echo \"power_supply.wakeup=enable\" >> /system/build.prop",
                                                                     "setprop power_supply.wakeup enable",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                                                             try {
                                                                 RootTools.getShell(true).add(command1);
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                             } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                 ex.printStackTrace();
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                             }
                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }

                                                     } else {

                                                         if (RootTools.isAccessGiven()) {
                                                             Command command1 = new Command(0,
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/power_supply.wakeup/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                                                             );
                                                             try {
                                                                 RootTools.getShell(true).add(command1);
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                             } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                 ex.printStackTrace();
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                             }
                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     }

                                                 }
                                             }

        );

        /////////////////////////////////////////////////////////////////////////
        ////// Enable better management of sensors during deepsleep.. ///////////
        /////////////////////////////////////////////////////////////////////////
        CheckBox checkbox6 = view.findViewById(R.id.checkBox6);
        if (text.toString().contains("ro.ril.sensor.sleep.control=1")) {
            checkbox6.setChecked(true);
        } else {
            checkbox6.setChecked(false);
        }
        checkbox6.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery6)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery6)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery6)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }

                return true;
            }
        });
        checkbox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {

                                                     if (isChecked) {


                                                         if (RootTools.isAccessGiven()) {
                                                             Command command1 = new Command(0,
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.sensor.sleep.control/d' /system/build.prop",
                                                                     "echo \"ro.ril.sensor.sleep.control=1\" >> /system/build.prop",
                                                                     "setprop ro.ril.sensor.sleep.control 1",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                                                             try {
                                                                 RootTools.getShell(true).add(command1);
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                             } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                 ex.printStackTrace();
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                             }
                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }

                                                     } else {

                                                         if (RootTools.isAccessGiven()) {
                                                             Command command1 = new Command(0,
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.sensor.sleep.control/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                                                             );
                                                             try {
                                                                 RootTools.getShell(true).add(command1);
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                             } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                 ex.printStackTrace();
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                             }
                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     }

                                                 }
                                             }

        );

        /////////////////////////////////////////////////
        ////// Disable system logging........ ///////////
        /////////////////////////////////////////////////
        CheckBox checkbox7 = view.findViewById(R.id.checkBox7);
        String check00 = "/etc/init.d/logs";
        String check00a = "/system/etc/init.d/logs";
        if ((text.toString().contains("ro.config.nocheckin=1")
                && text.toString().contains("profiler.force_disable_err_rpt=1")
                && text.toString().contains("debugtool.anrhistory=0")
                && text.toString().contains("profiler.debugmonitor=false")
                && text.toString().contains("profiler.launch=false")
                && text.toString().contains("profiler.hung.dumpdobugreport=false")
                && text.toString().contains("persist.android.strictmode=0")
                && text.toString().contains("logcat.live=disable")
                && text.toString().contains("profiler.force_disable_ulog=1") && (new File(Environment.getRootDirectory() + check00).exists() || new File(check00a).exists() || new File(Environment.getRootDirectory() + check00a).exists()))) {
            checkbox7.setChecked(true);
        } else {
            checkbox7.setChecked(false);
        }
        checkbox7.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery8)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery8)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }
                if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                    new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack))
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.battery8)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }

                return true;
            }
        });
        checkbox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {
                                                     if (isChecked) {


                                                         if (RootTools.isAccessGiven()) {
                                                             Command command1 = new Command(0,
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.config.nocheckin/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/logcat.live/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/profiler.force_disable_err_rpt/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/profiler.force_disable_ulog/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debugtool.anrhistory/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/profiler.debugmonitor/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/profiler.launch/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/profiler.hung.dumpdobugreport/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.android.strictmode/d' /system/build.prop",
                                                                     "echo \"ro.config.nocheckin=1\" >> /system/build.prop",
                                                                     "echo \"logcat.live=disable\" >> /system/build.prop",
                                                                     "echo \"profiler.force_disable_err_rpt=1\" >> /system/build.prop",
                                                                     "echo \"profiler.force_disable_ulog=1\" >> /system/build.prop",
                                                                     "echo \"debugtool.anrhistory=0\" >> /system/build.prop",
                                                                     "echo \"profiler.debugmonitor=false\" >> /system/build.prop",
                                                                     "echo \"profiler.launch=false\" >> /system/build.prop",
                                                                     "echo \"profiler.hung.dumpdobugreport=false\" >> /system/build.prop",
                                                                     "echo \"persist.android.strictmode=0\" >> /system/build.prop",
                                                                     "setprop ro.config.nocheckin 1",
                                                                     "setprop logcat.live disable",
                                                                     "setprop profiler.force_disable_err_rpt 1",
                                                                     "setprop profiler.force_disable_ulog 1",
                                                                     "setprop debugtool.anrhistory 0",
                                                                     "setprop profiler.debugmonitor false",
                                                                     "setprop profiler.launch false",
                                                                     "setprop profiler.hung.dumpdobugreport false",
                                                                     "setprop persist.android.strictmode 0",
                                                                     "cp /data/data/com.nowenui.systemtweakerfree/files/logs /system/etc/init.d/",
                                                                     "chmod 777 /system/etc/init.d/logs",
                                                                     "/system/etc/init.d/logs",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                                                             try {
                                                                 RootTools.getShell(true).add(command1);
                                                                 if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {

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
                                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                         }
                                                                     }, 4000);
                                                                 }
                                                                 if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {

                                                                     final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                                     dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                                     dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                                     dialog.setIndeterminate(false);
                                                                     dialog.setCancelable(false);
                                                                     dialog.show();

                                                                     Handler handler = new Handler();
                                                                     handler.postDelayed(new Runnable() {
                                                                         public void run() {
                                                                             dialog.dismiss();
                                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                         }
                                                                     }, 4000);
                                                                 }
                                                                 if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {

                                                                     final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                                     dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                                     dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                                     dialog.setIndeterminate(false);
                                                                     dialog.setCancelable(false);
                                                                     dialog.show();

                                                                     Handler handler = new Handler();
                                                                     handler.postDelayed(new Runnable() {
                                                                         public void run() {
                                                                             dialog.dismiss();
                                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakenabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                         }
                                                                     }, 4000);
                                                                 }

                                                             } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                 ex.printStackTrace();
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                             }
                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }

                                                     } else {

                                                         if (RootTools.isAccessGiven()) {
                                                             Command command1 = new Command(0,
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.config.nocheckin/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/profiler.force_disable_err_rpt/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/profiler.force_disable_ulog/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/logcat.live/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debugtool.anrhistory/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/profiler.debugmonitor/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/profiler.launch/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/profiler.hung.dumpdobugreport/d' /system/build.prop",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.android.strictmode/d' /system/build.prop",
                                                                     "rm -f /system/etc/init.d/logs",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                     "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                                                             );
                                                             try {
                                                                 RootTools.getShell(true).add(command1);
                                                                 if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {

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
                                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                         }
                                                                     }, 4000);
                                                                 }
                                                                 if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {

                                                                     final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                                     dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                                     dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                                     dialog.setIndeterminate(false);
                                                                     dialog.setCancelable(false);
                                                                     dialog.show();

                                                                     Handler handler = new Handler();
                                                                     handler.postDelayed(new Runnable() {
                                                                         public void run() {
                                                                             dialog.dismiss();
                                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                         }
                                                                     }, 4000);
                                                                 }
                                                                 if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {

                                                                     final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                                     dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                                     dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                                                     dialog.setIndeterminate(false);
                                                                     dialog.setCancelable(false);
                                                                     dialog.show();

                                                                     Handler handler = new Handler();
                                                                     handler.postDelayed(new Runnable() {
                                                                         public void run() {
                                                                             dialog.dismiss();
                                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.tweakdisabled)).withBackgroundColorId(R.color.textview1good).show();
                                                                         }
                                                                     }, 4000);
                                                                 }

                                                             } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                 ex.printStackTrace();
                                                                 new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                                                             }
                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     }

                                                 }
                                             }

        );


    }


}