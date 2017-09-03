package com.nowenui.systemtweakerfree.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
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
import com.nowenui.systemtweakerfree.Utility;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeoutException;

public class BackupFragment extends Fragment {

    private TextView textViewbackupbuild, textViewbackupinitd;
    private boolean isClicked;

    public static BackupFragment newInstance(Bundle bundle) {
        BackupFragment Backup = new BackupFragment();

        if (bundle != null) {
            Backup.setArguments(bundle);
        }

        return Backup;
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
        return inflater.inflate(R.layout.fragment_backupdevice, parent, false);
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        /////////////////////////////////////////////////////
        ////// Time with Last Backup update timer ///////////
        ////////////////////////////////////////////////////

        final String gettext = getResources().getString(R.string.lastbackup);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final File file = new File("/sdcard/SystemTweakerFREE/backups/buildprop.backup");
                                    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

                                    textViewbackupbuild = view.findViewById(R.id.textViewbackupbuild);

                                    textViewbackupbuild.setText(gettext + " " + sdf.format(file.lastModified()));


                                    final File file1 = new File("/sdcard/SystemTweakerFREE/backups/init.d");
                                    final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

                                    textViewbackupinitd = view.findViewById(R.id.textViewbackupinitd);


                                    textViewbackupinitd.setText(gettext + " " + sdf1.format(file1.lastModified()));

                                }
                            });
                        }
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

        /////////////////////////////////////////
        ////// Backup build.prop file ///////////
        /////////////////////////////////////////
        Button backup_build = view.findViewById(R.id.backup_build);
        backup_build.setBackgroundResource(R.drawable.roundbuttoncal);
        backup_build.setTextColor(Color.WHITE);
        backup_build.setTextSize(15);
        backup_build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if (RootTools.isAccessGiven()) {
                    File f = new File("/sdcard/SystemTweakerFREE/backups");
                    if (f.exists() && f.isDirectory()) {
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                "cp /system/build.prop /sdcard/SystemTweakerFREE/backups/buildprop.backup",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                        try {
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.backupsucc)).withBackgroundColorId(R.color.textview1good).show();
                            RootTools.getShell(true).add(command1);
                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    } else {
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                "mkdir /sdcard/SystemTweakerFREE",
                                "mkdir /sdcard/SystemTweakerFREE/backups",
                                "cp /system/build.prop /sdcard/SystemTweakerFREE/backups/buildprop.backup",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                        try {
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.backupsucc)).withBackgroundColorId(R.color.textview1good).show();
                            RootTools.getShell(true).add(command1);
                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    }
                } else {
                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                }

                final String gettext = getResources().getString(R.string.lastbackup);


                final File file = new File("/sdcard/SystemTweakerFREE/backups/buildprop.backup");
                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

                textViewbackupbuild = view.findViewById(R.id.textViewbackupbuild);

                if (getActivity() == null)
                    return;

                Thread t = new Thread() {

                    @Override
                    public void run() {
                        try {
                            while (!isInterrupted()) {
                                Thread.sleep(500);
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            textViewbackupbuild.setText(gettext + " " + sdf.format(file.lastModified()));
                                        }
                                    });
                                }
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                };

                t.start();


            }
        });


        /////////////////////////////////////////
        ////// Backup init.d folder /////////////
        /////////////////////////////////////////
        Button backup_init = view.findViewById(R.id.backup_init);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (mSharedPreference.contains("skipnitd")) {
            backup_init.setEnabled(false);
            backup_init.setBackgroundResource(R.drawable.roundbuttonfuck);
        } else {
            backup_init.setEnabled(true);
            backup_init.setBackgroundResource(R.drawable.roundbuttoncal);
        }

        backup_init.setTextColor(Color.WHITE);
        backup_init.setTextSize(15);
        backup_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if (RootTools.isAccessGiven()) {
                    File f = new File("/sdcard/SystemTweakerFREE/backups/init.d");
                    if (f.exists() && f.isDirectory()) {
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                "rm -r /sdcard/SystemTweakerFREE/backups/init.d",
                                "mkdir /sdcard/SystemTweakerFREE",
                                "mkdir /sdcard/SystemTweakerFREE/backups",
                                "mkdir /sdcard/SystemTweakerFREE/backups/init.d",
                                "cp /system/etc/init.d/* /sdcard/SystemTweakerFREE/backups/init.d",
                                "dos2unix /sdcard/SystemTweakerFREE/backups/init.d/*",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                        try {

                            RootTools.getShell(true).add(command1);
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.backupsucc)).withBackgroundColorId(R.color.textview1good).show();
                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    } else {
                        Command command2 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                "mkdir /sdcard/SystemTweakerFREE",
                                "mkdir /sdcard/SystemTweakerFREE/backups",
                                "mkdir /sdcard/SystemTweakerFREE/backups/init.d",
                                "cp /system/etc/init.d/* /sdcard/SystemTweakerFREE/backups/init.d",
                                "dos2unix /sdcard/SystemTweakerFREE/backups/init.d/*",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                        );
                        try {
                            RootTools.getShell(true).add(command2);
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.backupsucc)).withBackgroundColorId(R.color.textview1good).show();
                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    }

                } else {
                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                }

                final File file1 = new File("/sdcard/SystemTweakerFREE/backups/init.d");
                final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

                textViewbackupinitd = view.findViewById(R.id.textViewbackupinitd);

                if (getActivity() == null)
                    return;

                Thread t = new Thread() {

                    @Override
                    public void run() {
                        try {
                            while (!isInterrupted()) {
                                Thread.sleep(1000);
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            textViewbackupinitd.setText(gettext + " " + sdf1.format(file1.lastModified()));
                                        }
                                    });
                                }
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                };

                t.start();


            }
        });

        /////////////////////////////////////
        ////// Build.prop restore ///////////
        /////////////////////////////////////
        Button repair_build = view.findViewById(R.id.repair_build);
        repair_build.setBackgroundResource(R.drawable.roundbuttoncal);
        repair_build.setTextColor(Color.WHITE);
        repair_build.setTextSize(15);
        repair_build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if (RootTools.isAccessGiven()) {
                    File f = new File("/sdcard/SystemTweakerFREE/backups/buildprop.backup");
                    if (f.exists()) {
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                "rm /system/build.prop",
                                "cp /sdcard/SystemTweakerFREE/backups/buildprop.backup /system/build.prop",
                                "chmod 644 /system/build.prop",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                        try {
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.vosst)).withBackgroundColorId(R.color.textview1good).show();
                            RootTools.getShell(true).add(command1);
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                                new AlertDialog.Builder(v.getContext())
                                        .setTitle(R.string.reboot)
                                        .setMessage(R.string.rebootdialog)
                                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

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
                                        .setMessage(R.string.rebootdialog)
                                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

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
                                        .setMessage(R.string.rebootdialog)
                                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

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

                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    } else {
                        if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                            new AlertDialog.Builder(v.getContext())
                                    .setTitle(R.string.error)
                                    .setMessage(R.string.backupfuck)
                                    .setNegativeButton(R.string.yasno, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setIcon(R.drawable.warning)
                                    .show();
                        }
                        if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                            new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark))
                                    .setTitle(R.string.error)
                                    .setMessage(R.string.backupfuck)
                                    .setNegativeButton(R.string.yasno, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setIcon(R.drawable.warning)
                                    .show();
                        }
                        if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                            new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack))
                                    .setTitle(R.string.error)
                                    .setMessage(R.string.backupfuck)
                                    .setNegativeButton(R.string.yasno, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setIcon(R.drawable.warning)
                                    .show();
                        }
                    }
                } else {
                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                }

            }
        });

        /////////////////////////////////
        ////// Init.d restore ///////////
        /////////////////////////////////
        Button repair_init = view.findViewById(R.id.repair_init);
        if (mSharedPreference.contains("skipnitd")) {
            repair_init.setEnabled(false);
            repair_init.setBackgroundResource(R.drawable.roundbuttonfuck);
        } else {
            repair_init.setBackgroundResource(R.drawable.roundbuttoncal);
            repair_init.setEnabled(true);
        }

        repair_init.setTextColor(Color.WHITE);
        repair_init.setTextSize(15);
        repair_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if (RootTools.isAccessGiven()) {
                    File f = new File("/sdcard/SystemTweakerFREE/backups/init.d");
                    if (f.exists() && f.isDirectory()) {
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                "rm /system/etc/init.d/*",
                                "cp /sdcard/SystemTweakerFREE/backups/init.d/* /system/etc/init.d/",
                                "chmod 755 /system/etc/init.d",
                                "chmod 777 /system/etc/init.d/*",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                        );
                        try {
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.vosst)).withBackgroundColorId(R.color.textview1good).show();
                            RootTools.getShell(true).add(command1);
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                                new AlertDialog.Builder(v.getContext())
                                        .setTitle(R.string.reboot)
                                        .setMessage(R.string.rebootdialog)
                                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

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
                                        .setMessage(R.string.rebootdialog)
                                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

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
                                        .setMessage(R.string.rebootdialog)
                                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

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
                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    } else {
                        if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                            new AlertDialog.Builder(v.getContext())
                                    .setTitle(R.string.error)
                                    .setMessage(R.string.backupfuck)
                                    .setNegativeButton(R.string.yasno, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setIcon(R.drawable.warning)
                                    .show();
                        }
                        if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                            new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark))
                                    .setTitle(R.string.error)
                                    .setMessage(R.string.backupfuck)
                                    .setNegativeButton(R.string.yasno, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setIcon(R.drawable.warning)
                                    .show();
                        }
                        if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                            new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack))
                                    .setTitle(R.string.error)
                                    .setMessage(R.string.backupfuck)
                                    .setNegativeButton(R.string.yasno, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setIcon(R.drawable.warning)
                                    .show();
                        }
                    }
                } else {
                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                }
            }
        });

        /////////////////////////////////////
        ////// Clear Dalvik-cache ///////////
        /////////////////////////////////////
        Button dalvickclear = view.findViewById(R.id.dalvickclear);
        dalvickclear.setBackgroundResource(R.drawable.roundbuttoncal);
        dalvickclear.setTextColor(Color.WHITE);
        dalvickclear.setTextSize(15);
        dalvickclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if (RootTools.isAccessGiven()) {
                    Command command1 = new Command(0,
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                            "rm -rf /data/dalvik-cache",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                    );
                    try {
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.ok)).withBackgroundColorId(R.color.textview1good).show();
                        RootTools.getShell(true).add(command1);
                        if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                            new AlertDialog.Builder(v.getContext())
                                    .setTitle(R.string.reboot)
                                    .setMessage(R.string.rebootdialog)
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

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
                                    .setMessage(R.string.rebootdialog)
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

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
                                    .setMessage(R.string.rebootdialog)
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

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
                    } catch (IOException | RootDeniedException | TimeoutException ex) {
                        ex.printStackTrace();
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                    }
                } else {
                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                }
            }
        });

        /////////////////////////////////////
        ////// Clear cache button ///////////
        /////////////////////////////////////
        Button cacheclear = view.findViewById(R.id.cacheclear);
        cacheclear.setBackgroundResource(R.drawable.roundbuttoncal);
        cacheclear.setTextColor(Color.WHITE);
        cacheclear.setTextSize(15);
        cacheclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if (RootTools.isAccessGiven()) {
                    Command command1 = new Command(0,
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                            "rm -rf /cache",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                            "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                    );
                    try {
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.ok)).withBackgroundColorId(R.color.textview1good).show();
                        RootTools.getShell(true).add(command1);
                        if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                            new AlertDialog.Builder(v.getContext())
                                    .setTitle(R.string.reboot)
                                    .setMessage(R.string.rebootdialog)
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

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
                                    .setMessage(R.string.rebootdialog)
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

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
                                    .setMessage(R.string.rebootdialog)
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

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
                    } catch (IOException | RootDeniedException | TimeoutException ex) {
                        ex.printStackTrace();
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                    }
                } else {
                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                }

            }
        });
    }

}