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
import android.widget.Toast;

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
                                    Toast.makeText(getActivity(), "ROOT NEEDED!", Toast.LENGTH_SHORT).show();
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

        View view = inflater.inflate(R.layout.backup_device, parent, false);

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
                if (RootTools.isBusyboxAvailable()) {
                    if (RootTools.isRootAvailable()) {
                        if (RootTools.isAccessGiven()) {
                            File f = new File("/sdcard/SystemTweakerFREE/backups");
                            if (f.exists() && f.isDirectory()) {
                                Command command1 = new Command(0,
                                        "busybox mount -o rw,remount /proc /system",
                                        "cp /system/build.prop /sdcard/SystemTweakerFREE/backups/buildprop.backup",
                                        "busybox mount -o ro,remount /proc /system");
                                try {
                                    Toast.makeText(getActivity(), R.string.backupsucc, Toast.LENGTH_SHORT).show();
                                    RootTools.getShell(true).add(command1);
                                } catch (IOException | RootDeniedException | TimeoutException ex) {
                                    ex.printStackTrace();
                                    Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Command command1 = new Command(0,
                                        "busybox mount -o rw,remount /proc /system",
                                        "mkdir /sdcard/SystemTweakerFREE/",
                                        "mkdir /sdcard/SystemTweakerFREE/backups",
                                        "cp /system/build.prop /sdcard/SystemTweakerFREE/backups/buildprop.backup",
                                        "busybox mount -o ro,remount /proc /system");
                                try {
                                    Toast.makeText(getActivity(), R.string.backupsucc, Toast.LENGTH_SHORT).show();
                                    RootTools.getShell(true).add(command1);
                                } catch (IOException | RootDeniedException | TimeoutException ex) {
                                    ex.printStackTrace();
                                    Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                }
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
                if (RootTools.isBusyboxAvailable()) {
                    if (RootTools.isRootAvailable()) {
                        if (RootTools.isAccessGiven()) {
                            File f = new File("/sdcard/SystemTweakerFREE/backups/init.d");
                            if (f.exists() && f.isDirectory()) {
                                Command command1 = new Command(0,
                                        "busybox mount -o rw,remount /proc /system",
                                        "rm -r /sdcard/SystemTweakerFREE/backups/init.d",
                                        "mkdir /sdcard/SystemTweakerFREE/",
                                        "mkdir /sdcard/SystemTweakerFREE/backups",
                                        "mkdir /sdcard/SystemTweakerFREE/backups/init.d",
                                        "cp /system/etc/init.d/* /sdcard/SystemTweakerFREE/backups/init.d",
                                        "dos2unix /sdcard/SystemTweakerFREE/backups/init.d/*",
                                        "busybox mount -o ro,remount /proc /system");
                                try {

                                    RootTools.getShell(true).add(command1);
                                    Toast.makeText(getActivity(), R.string.backupsucc, Toast.LENGTH_SHORT).show();
                                } catch (IOException | RootDeniedException | TimeoutException ex) {
                                    ex.printStackTrace();
                                    Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Command command2 = new Command(0,
                                        "busybox mount -o rw,remount /proc /system",
                                        "mkdir /sdcard/SystemTweakerFREE/",
                                        "mkdir /sdcard/SystemTweakerFREE/backups",
                                        "mkdir /sdcard/SystemTweakerFREE/backups/init.d",
                                        "cp /system/etc/init.d/* /sdcard/SystemTweakerFREE/backups/init.d",
                                        "dos2unix /sdcard/SystemTweakerFREE/backups/init.d/*",
                                        "busybox mount -o ro,remount /proc /system");
                                try {
                                    RootTools.getShell(true).add(command2);
                                    Toast.makeText(getActivity(), R.string.backupsucc, Toast.LENGTH_SHORT).show();
                                } catch (IOException | RootDeniedException | TimeoutException ex) {
                                    ex.printStackTrace();
                                    Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.errobusybox, Toast.LENGTH_SHORT).show();
                    RootTools.offerBusyBox(getActivity());
                }
            }
        });

        String gettext = getResources().getString(R.string.lastbackup);


        File file = new File("/sdcard/SystemTweakerFREE/backups/buildprop.backup");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        textViewbackupbuild = (TextView) view.findViewById(R.id.textViewbackupbuild);

        textViewbackupbuild.setText(gettext + " " + sdf.format(file.lastModified()));


        File file1 = new File("/sdcard/SystemTweakerFREE/backups/init.d");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        textViewbackupinitd = (TextView) view.findViewById(R.id.textViewbackupinitd);


        textViewbackupinitd.setText(gettext + " " + sdf1.format(file1.lastModified()));


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
                if (RootTools.isBusyboxAvailable()) {
                    if (RootTools.isRootAvailable()) {
                        if (RootTools.isAccessGiven()) {
                            File f = new File("/sdcard/SystemTweakerFREE/backups/buildprop.backup");
                            if (f.exists()) {
                                Command command1 = new Command(0,
                                        "busybox mount -o rw,remount /proc /system",
                                        "rm /system/build.prop",
                                        "cp /sdcard/SystemTweakerFREE/backups/buildprop.backup /system/build.prop",
                                        "chmod 644 /system/build.prop",
                                        "busybox mount -o ro,remount /proc /system");
                                try {
                                    Toast.makeText(getActivity(), R.string.vosst, Toast.LENGTH_SHORT).show();
                                    RootTools.getShell(true).add(command1);
                                    new AlertDialog.Builder(v.getContext())
                                            .setTitle(R.string.reboot)
                                            .setMessage(R.string.rebootdialog)
                                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (RootTools.isBusyboxAvailable()) {
                                                        if (RootTools.isRootAvailable()) {
                                                            if (RootTools.isAccessGiven()) {
                                                                Command command1 = new Command(0,
                                                                        "reboot");
                                                                try {
                                                                    Toast.makeText(getActivity(), R.string.reboot, Toast.LENGTH_SHORT).show();
                                                                    RootTools.getShell(true).add(command1);
                                                                } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                    ex.printStackTrace();
                                                                    Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(getActivity(), R.string.erroroot, Toast.LENGTH_SHORT).show();
                                                            }

                                                        } else {
                                                            Toast.makeText(getActivity(), R.string.erroroot, Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getActivity(), R.string.errobusybox, Toast.LENGTH_SHORT).show();
                                                        RootTools.offerBusyBox(getActivity());
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
                                    Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
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
                if (RootTools.isBusyboxAvailable()) {
                    if (RootTools.isRootAvailable()) {
                        if (RootTools.isAccessGiven()) {
                            File f = new File("/sdcard/SystemTweakerFREE/backups/init.d");
                            if (f.exists() && f.isDirectory()) {
                                Command command1 = new Command(0,
                                        "busybox mount -o rw,remount /proc /system",
                                        "rm /system/etc/init.d/*",
                                        "cp /sdcard/SystemTweakerFREE/backups/init.d/* /system/etc/init.d/",
                                        "chmod 755 /system/etc/init.d",
                                        "chmod 777 /system/etc/init.d/*",
                                        "busybox mount -o ro,remount /proc /system"
                                );
                                try {
                                    Toast.makeText(getActivity(), R.string.vosst, Toast.LENGTH_SHORT).show();
                                    RootTools.getShell(true).add(command1);
                                    new AlertDialog.Builder(v.getContext())
                                            .setTitle(R.string.reboot)
                                            .setMessage(R.string.rebootdialog)
                                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (RootTools.isBusyboxAvailable()) {
                                                        if (RootTools.isRootAvailable()) {
                                                            if (RootTools.isAccessGiven()) {
                                                                Command command1 = new Command(0,
                                                                        "reboot");
                                                                try {
                                                                    Toast.makeText(getActivity(), R.string.reboot, Toast.LENGTH_SHORT).show();
                                                                    RootTools.getShell(true).add(command1);
                                                                } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                    ex.printStackTrace();
                                                                    Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(getActivity(), R.string.erroroot, Toast.LENGTH_SHORT).show();
                                                            }

                                                        } else {
                                                            Toast.makeText(getActivity(), R.string.erroroot, Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getActivity(), R.string.errobusybox, Toast.LENGTH_SHORT).show();
                                                        RootTools.offerBusyBox(getActivity());
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
                                    Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

}