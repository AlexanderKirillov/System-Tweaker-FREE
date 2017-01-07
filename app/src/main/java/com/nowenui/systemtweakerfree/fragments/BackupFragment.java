package com.nowenui.systemtweakerfree.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeoutException;

public class BackupFragment extends Fragment {

    private Button backup_build, backup_init, repair_build, repair_init;
    private TextView textViewbackupbuild, textViewbackupinitd;
    private boolean isClicked;

    public static BackupFragment newInstance(Bundle bundle) {
        BackupFragment messagesFragment = new BackupFragment();

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
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        RootTools.debugMode = false;

        final View view = inflater.inflate(R.layout.backup_device, parent, false);

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

                                    textViewbackupbuild = (TextView) view.findViewById(R.id.textViewbackupbuild);

                                    textViewbackupbuild.setText(gettext + " " + sdf.format(file.lastModified()));


                                    final File file1 = new File("/sdcard/SystemTweakerFREE/backups/init.d");
                                    final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

                                    textViewbackupinitd = (TextView) view.findViewById(R.id.textViewbackupinitd);


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


        backup_build = (Button) view.findViewById(R.id.backup_build);
        backup_build.setBackgroundResource(R.drawable.roundbuttoncal);
        backup_build.setTextColor(Color.WHITE);
        backup_build.setTextSize(20);
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
                if (RootTools.isRootAvailable()) {
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

                } else {
                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                }
                final String gettext = getResources().getString(R.string.lastbackup);


                final File file = new File("/sdcard/SystemTweakerFREE/backups/buildprop.backup");
                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

                textViewbackupbuild = (TextView) view.findViewById(R.id.textViewbackupbuild);

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

        backup_init = (Button) view.findViewById(R.id.backup_init);
        backup_init.setBackgroundResource(R.drawable.roundbuttoncal);
        backup_init.setTextColor(Color.WHITE);
        backup_init.setTextSize(20);
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
                if (RootTools.isRootAvailable()) {
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
                } else {
                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                }

                final File file1 = new File("/sdcard/SystemTweakerFREE/backups/init.d");
                final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

                textViewbackupinitd = (TextView) view.findViewById(R.id.textViewbackupinitd);

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


        repair_build = (Button) view.findViewById(R.id.repair_build);
        repair_build.setBackgroundResource(R.drawable.roundbuttoncal);
        repair_build.setTextColor(Color.WHITE);
        repair_build.setTextSize(20);
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
                if (RootTools.isRootAvailable()) {
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
                            } catch (IOException | RootDeniedException | TimeoutException ex) {
                                ex.printStackTrace();
                                new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                            }
                        } else {
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
                    } else {
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                    }

                } else {
                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                }
            }
        });

        repair_init = (Button) view.findViewById(R.id.repair_init);
        repair_init.setBackgroundResource(R.drawable.roundbuttoncal);
        repair_init.setTextColor(Color.WHITE);
        repair_init.setTextSize(20);
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
                if (RootTools.isRootAvailable()) {
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
                            } catch (IOException | RootDeniedException | TimeoutException ex) {
                                ex.printStackTrace();
                                new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                            }
                        } else {
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
                    } else {
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                    }

                } else {
                    new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                }
            }
        });


        return view;
    }

}