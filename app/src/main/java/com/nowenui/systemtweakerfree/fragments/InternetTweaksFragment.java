package com.nowenui.systemtweakerfree.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class InternetTweaksFragment extends Fragment {

    private static final String PRE_PATH = "/proc/sys/net/ipv4/";
    private Scanner file_scanner;

    public static InternetTweaksFragment newInstance(Bundle bundle) {
        InternetTweaksFragment InternetTweaks = new InternetTweaksFragment();

        if (bundle != null) {
            InternetTweaks.setArguments(bundle);
        }

        return InternetTweaks;
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
                if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                    new android.app.AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark))
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
                    new android.app.AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack))
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
        return inflater.inflate(R.layout.fragment_internettweaks, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ////////////////////////////////////////
        ////// build.prop -> String ////////////
        ////////////////////////////////////////

        File file2 = new File("/system/build.prop");

        StringBuilder text2 = new StringBuilder();
        try {
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            String line2;

            while ((line2 = br2.readLine()) != null) {
                text2.append(line2);
                text2.append('\n');
            }
            br2.close();
        } catch (IOException e) {
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        ////// Internet Tweaks.///////////////////////////////////////////////////////////////////////
        /// Improving WiFi/3G/2G/4G tweaks, increasing download speed, improving stability ///////////
        //////////////////////////////////////////////////////////////////////////////////////////////
        CheckBox checkbox10 = view.findViewById(R.id.checkBox10);
        if (text2.toString().contains("net.tcp.buffersize.default=4096,87380,256960,4096,16384,256960")
                && text2.toString().contains("net.tcp.buffersize.wifi=4096,87380,256960,4096,16384,256960")
                && text2.toString().contains("net.tcp.buffersize.umts=4096,87380,256960,4096,16384,256960")
                && text2.toString().contains("net.tcp.buffersize.gprs=4096,87380,256960,4096,16384,256960")
                && text2.toString().contains("net.tcp.buffersize.edge=4096,87380,256960,4096,16384,256960")
                && text2.toString().contains("net.tcp.buffersize.hspa=6144,87380,524288,6144,163 84,262144")
                && text2.toString().contains("net.tcp.buffersize.lte=524288,1048576,2097152,5242 88,1048576,2097152")
                && text2.toString().contains("net.tcp.buffersize.hsdpa=6144,87380,1048576,6144,8 7380,1048576")
                && text2.toString().contains("net.tcp.buffersize.evdo_b=6144,87380,1048576,6144, 87380,1048576")) {
            checkbox10.setChecked(true);
        } else {
            checkbox10.setChecked(false);
        }
        checkbox10.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.inet1)
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
                            .setMessage(R.string.inet1)
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
                            .setMessage(R.string.inet1)
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
        checkbox10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {
                                                      if (isChecked) {


                                                          if (RootTools.isAccessGiven()) {
                                                              Command command1 = new Command(0,
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.default/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.wifi/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.umts/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.gprs/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.edge/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.hspa/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.lte/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.hsdpa/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.evdo_b/d' /system/build.prop",
                                                                      "echo \"net.tcp.buffersize.default=4096,87380,256960,4096,16384,256960\" >> /system/build.prop",
                                                                      "echo \"net.tcp.buffersize.wifi=4096,87380,256960,4096,16384,256960\" >> /system/build.prop",
                                                                      "echo \"net.tcp.buffersize.umts=4096,87380,256960,4096,16384,256960\" >> /system/build.prop",
                                                                      "echo \"net.tcp.buffersize.gprs=4096,87380,256960,4096,16384,256960\" >> /system/build.prop",
                                                                      "echo \"net.tcp.buffersize.edge=4096,87380,256960,4096,16384,256960\" >> /system/build.prop",
                                                                      "echo \"net.tcp.buffersize.hspa=6144,87380,524288,6144,163 84,262144\" >> /system/build.prop",
                                                                      "echo \"net.tcp.buffersize.lte=524288,1048576,2097152,5242 88,1048576,2097152\" >> /system/build.prop",
                                                                      "echo \"net.tcp.buffersize.hsdpa=6144,87380,1048576,6144,8 7380,1048576\" >> /system/build.prop",
                                                                      "echo \"net.tcp.buffersize.evdo_b=6144,87380,1048576,6144, 87380,1048576\" >> /system/build.prop",
                                                                      "setprop net.tcp.buffersize.default 4096,87380,256960,4096,16384,256960",
                                                                      "setprop net.tcp.buffersize.wifi 4096,87380,256960,4096,16384,256960",
                                                                      "setprop net.tcp.buffersize.umts 4096,87380,256960,4096,16384,256960",
                                                                      "setprop net.tcp.buffersize.gprs 4096,87380,256960,4096,16384,256960",
                                                                      "setprop net.tcp.buffersize.edge 4096,87380,256960,4096,16384,256960",
                                                                      "setprop net.tcp.buffersize.hspa 6144,87380,524288,6144,163 84,262144",
                                                                      "setprop net.tcp.buffersize.lte 524288,1048576,2097152,5242 88,1048576,2097152",
                                                                      "setprop net.tcp.buffersize.hsdpa 6144,87380,1048576,6144,8 7380,1048576",
                                                                      "setprop net.tcp.buffersize.evdo_b 6144,87380,1048576,6144, 87380,1048576",
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
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.default/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.wifi/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.umts/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.gprs/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.edge/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.hspa/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.lte/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.hsdpa/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.tcp.buffersize.evdo_b/d' /system/build.prop",
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


        ///////////////////////////////////////////////////////////////////
        ////// Improving internet speed ///////////////////////////////////
        ////// Changing the TCP  an other values for the better ///////////
        ///////////////////////////////////////////////////////////////////
        CheckBox checkbox11 = view.findViewById(R.id.checkBox11);
        String check6 = "/etc/init.d/05InternetTweak";
        String check6a = "/system/etc/init.d/05InternetTweak";
        if ((new File(Environment.getRootDirectory() + check6).exists() || new File(check6a).exists() || new File(Environment.getRootDirectory() + check6a).exists()) &&
                text2.toString().contains("ro.ril.hsxpa=2")
                && text2.toString().contains("ro.ril.hep=1")
                && text2.toString().contains("ro.ril.enable.dtm=1")
                && text2.toString().contains("ro.ril.hsdpa.category=10")
                && text2.toString().contains("ro.ril.enable.a53=1")
                && text2.toString().contains("ro.ril.enable.3g.prefix=1")
                && text2.toString().contains("ro.ril.gprsclass=10")
                && text2.toString().contains("ro.ril.hsupa.category=7")
                && text2.toString().contains("ro.ril.hsdpa.category=10") &&
                text2.toString().contains("ro.ril.enable.a52=1")
                && text2.toString().contains("ro.ril.set.mtu1472=1")
                && text2.toString().contains("persist.cust.tel.eons=1")) {
            checkbox11.setChecked(true);
        } else {
            checkbox11.setChecked(false);
        }
        checkbox11.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.inet2)
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
                            .setMessage(R.string.inet2)
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
                            .setMessage(R.string.inet2)
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
        checkbox11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {
                                                      if (isChecked) {
                                                          if (RootTools.isAccessGiven()) {
                                                              Command command1 = new Command(0,
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.hsxpa/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.hep/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.enable.dtm/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.hsdpa.category/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.enable.a53/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.enable.3g.prefix/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.gprsclass/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.hsupa.category/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.hsdpa.category/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.enable.3g.prefix/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.enable.a52/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.set.mtu1472/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.cust.tel.eons/d' /system/build.prop",
                                                                      "echo \"ro.ril.hsxpa=2\" >> /system/build.prop",
                                                                      "echo \"ro.ril.hep=1\" >> /system/build.prop",
                                                                      "echo \"ro.ril.enable.dtm=1\" >> /system/build.prop",
                                                                      "echo \"ro.ril.hsdpa.category=10\" >> /system/build.prop",
                                                                      "echo \"ro.ril.enable.a53=1\" >> /system/build.prop",
                                                                      "echo \"ro.ril.enable.3g.prefix=1\" >> /system/build.prop",
                                                                      "echo \"ro.ril.gprsclass=10\" >> /system/build.prop",
                                                                      "echo \"ro.ril.hsupa.category=7\" >> /system/build.prop",
                                                                      "echo \"ro.ril.hsdpa.category=10\" >> /system/build.prop",
                                                                      "echo \"ro.ril.enable.a52=1\" >> /system/build.prop",
                                                                      "echo \"ro.ril.set.mtu1472=1\" >> /system/build.prop",
                                                                      "echo \"persist.cust.tel.eons=1\" >> /system/build.prop",
                                                                      "setprop ro.ril.hsxpa 2",
                                                                      "setprop ro.ril.hep 1",
                                                                      "setprop ro.ril.enable.dtm 1",
                                                                      "setprop ro.ril.hsdpa.category 10",
                                                                      "setprop ro.ril.enable.a53 1",
                                                                      "setprop ro.ril.enable.3g.prefix 1",
                                                                      "setprop ro.ril.gprsclass 10",
                                                                      "setprop ro.ril.hsupa.category 7",
                                                                      "setprop ro.ril.hsdpa.category 10",
                                                                      "setprop ro.ril.enable.a52 1",
                                                                      "setprop ro.ril.set.mtu1472 1",
                                                                      "setprop persist.cust.tel.eons 1",
                                                                      "cp /data/data/com.nowenui.systemtweakerfree/files/05InternetTweak /system/etc/init.d/",
                                                                      "chmod 777 /system/etc/init.d/05InternetTweak",
                                                                      "/system/etc/init.d/05InternetTweak",
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
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.hsxpa/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.hep/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.enable.dtm/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.hsdpa.category/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.enable.a53/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.enable.3g.prefix/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.gprsclass/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.hsupa.category/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.hsdpa.category/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.enable.3g.prefix/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.enable.a52/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.set.mtu1472/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.cust.tel.eons/d' /system/build.prop",
                                                                      "rm -f /system/etc/init.d/05InternetTweak",
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

        ////////////////////////////
        ////// DNS Tweak ///////////
        ////////////////////////////
        CheckBox checkbox12 = view.findViewById(R.id.checkBox12);
        if (text2.toString().contains("net.dns1=8.8.8.8")
                && text2.toString().contains("net.dns2=8.8.4.4")
                && text2.toString().contains("net.rmnet0.dns1=8.8.8.8")
                && text2.toString().contains("net.rmnet0.dns2=8.8.4.4")
                && text2.toString().contains("net.ppp0.dns1=8.8.8.8")
                && text2.toString().contains("net.ppp0.dns2=8.8.4.4")
                && text2.toString().contains("net.wlan0.dns1=8.8.8.8")
                && text2.toString().contains("net.wlan0.dns2=8.8.4.4")
                && text2.toString().contains("net.eth0.dns1=8.8.8.8")
                && text2.toString().contains("net.eth0.dns2=8.8.4.4")
                && text2.toString().contains("net.gprs.dns1=8.8.8.8")
                && text2.toString().contains("net.gprs.dns2=8.8.4.4")) {
            checkbox12.setChecked(true);
        } else {
            checkbox12.setChecked(false);
        }

        checkbox12.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.inet3)
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
                            .setMessage(R.string.inet3)
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
                            .setMessage(R.string.inet3)
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

        checkbox12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {

                                                      if (isChecked) {

                                                          if (RootTools.isAccessGiven()) {
                                                              Command command1 = new Command(0,
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.dns1/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.dns2/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.rmnet0.dns1/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.rmnet0.dns2/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.ppp0.dns1/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.ppp0.dns2/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.wlan0.dns1/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.wlan0.dns2/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.eth0.dns1/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.eth0.dns2/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.gprs.dns1/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.gprs.dns2/d' /system/build.prop",
                                                                      "echo \"net.dns1=8.8.8.8\" >> /system/build.prop",
                                                                      "echo \"net.dns2=8.8.4.4\" >> /system/build.prop",
                                                                      "echo \"net.rmnet0.dns1=8.8.8.8\" >> /system/build.prop",
                                                                      "echo \"net.rmnet0.dns2=8.8.4.4\" >> /system/build.prop",
                                                                      "echo \"net.ppp0.dns1=8.8.8.8\" >> /system/build.prop",
                                                                      "echo \"net.ppp0.dns2=8.8.4.4\" >> /system/build.prop",
                                                                      "echo \"net.wlan0.dns1=8.8.8.8\" >> /system/build.prop",
                                                                      "echo \"net.wlan0.dns2=8.8.4.4\" >> /system/build.prop",
                                                                      "echo \"net.eth0.dns1=8.8.8.8\" >> /system/build.prop",
                                                                      "echo \"net.eth0.dns2=8.8.4.4\" >> /system/build.prop",
                                                                      "echo \"net.gprs.dns1=8.8.8.8\" >> /system/build.prop",
                                                                      "echo \"net.gprs.dns2=8.8.4.4\" >> /system/build.prop",
                                                                      "setprop net.dns1 8.8.8.8",
                                                                      "setprop net.dns2 8.8.4.4",
                                                                      "setprop net.rmnet0.dns1 8.8.8.8",
                                                                      "setprop net.rmnet0.dns2 8.8.4.4",
                                                                      "setprop net.ppp0.dns1 8.8.8.8",
                                                                      "setprop net.ppp0.dns2 8.8.4.4",
                                                                      "setprop net.wlan0.dns1 8.8.8.8",
                                                                      "setprop net.wlan0.dns2 8.8.4.4",
                                                                      "setprop net.eth0.dns1 8.8.8.8",
                                                                      "setprop net.eth0.dns2 8.8.4.4",
                                                                      "setprop net.gprs.dns1 8.8.8.8",
                                                                      "setprop net.gprs.dns2 8.8.4.4",
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
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.dns1/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.dns2/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.rmnet0.dns1/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.rmnet0.dns2/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.ppp0.dns1/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.ppp0.dns2/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.wlan0.dns1/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.wlan0.dns2/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.eth0.dns1/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.eth0.dns2/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.gprs.dns1/d' /system/build.prop",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/net.gprs.dns2/d' /system/build.prop",
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

        /////////////////////////////////////////////
        ////// Fast Dormancy Enable option///////////
        /////////////////////////////////////////////
        CheckBox checkbox13 = view.findViewById(R.id.checkBox13);
        if (text2.toString().contains("ro.config.hw_fast_dormancy=1")) {
            checkbox13.setChecked(true);
        } else {
            checkbox13.setChecked(false);
        }

        checkbox13.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.inet4)
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
                            .setMessage(R.string.inet4)
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
                            .setMessage(R.string.inet4)
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
        checkbox13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {
                                                      if (isChecked) {


                                                          if (RootTools.isAccessGiven()) {
                                                              Command command1 = new Command(0,
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.config.hw_fast_dormancy/d' /system/build.prop",
                                                                      "echo \"ro.config.hw_fast_dormancy=1\" >> /system/build.prop",
                                                                      "setprop ro.config.hw_fast_dormancy 1",
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
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.config.hw_fast_dormancy/d' /system/build.prop",
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

        //////////////////////////////////////
        ////// IPv6 Support option ///////////
        //////////////////////////////////////
        CheckBox checkbox14 = view.findViewById(R.id.checkBox14);
        if (text2.toString().contains("persist.telephony.support.ipv6=1")) {
            checkbox14.setChecked(true);
        } else {
            checkbox14.setChecked(false);
        }
        checkbox14.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.inet5)
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
                            .setMessage(R.string.inet5)
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
                            .setMessage(R.string.inet5)
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
        checkbox14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {

                                                      if (isChecked) {


                                                          if (RootTools.isAccessGiven()) {
                                                              Command command1 = new Command(0,
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.telephony.support.ipv6/d' /system/build.prop",
                                                                      "echo \"persist.telephony.support.ipv6=1\" >> /system/build.prop",
                                                                      "setprop persist.telephony.support.ipv6 1",
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
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.telephony.support.ipv6/d' /system/build.prop",
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

        //////////////////////////////////////
        ////// IPv4 support option ///////////
        /////////////////////////////////////
        CheckBox checkbox15 = view.findViewById(R.id.checkBox15);
        if (text2.toString().contains("persist.telephony.support.ipv4=1")) {
            checkbox15.setChecked(true);
        } else {
            checkbox15.setChecked(false);
        }
        checkbox15.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.tweakabout)
                            .setMessage(R.string.inet6)
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
                            .setMessage(R.string.inet6)
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
                            .setMessage(R.string.inet6)
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
        checkbox15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {
                                                      if (isChecked) {
                                                          if (RootTools.isAccessGiven()) {
                                                              Command command1 = new Command(0,
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.telephony.support.ipv4/d' /system/build.prop",
                                                                      "echo \"persist.telephony.support.ipv4=1\" >> /system/build.prop",
                                                                      "setprop persist.telephony.support.ipv4 1",
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
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.telephony.support.ipv4/d' /system/build.prop",
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

        ////////////////////////////////////////////////////////////////////////////////
        ////// Improve 3G stability using compression. /////////////////////////////////
        ////// May load processor. It may not be supported by all devices.  ///////////
        ///////////////////////////////////////////////////////////////////////////////
        final CheckBox checkBox20171 = view.findViewById(R.id.checkBox20171);
        String check120171 = "/etc/ppp/options";
        String check20171a = "/system/etc/ppp/options";
        if (new File(Environment.getRootDirectory() + check120171).exists() || new File(check20171a).exists() || new File(Environment.getRootDirectory() + check20171a).exists()) {
            checkBox20171.setChecked(true);
        } else {
            checkBox20171.setChecked(false);
        }
        checkBox20171.post(new Runnable() {
            @Override
            public void run() {
                checkBox20171.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                             @Override
                                                             public void onCheckedChanged(CompoundButton buttonView,
                                                                                          boolean isChecked) {

                                                                 if (isChecked) {


                                                                     if (RootTools.isAccessGiven()) {
                                                                         Command command1 = new Command(0,
                                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                                 "mkdir /system/etc/ppp",
                                                                                 "cp /data/data/com.nowenui.systemtweakerfree/files/options /system/etc/ppp/",
                                                                                 "chmod 555 /system/etc/ppp/options",
                                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                                                                         try {
                                                                             RootTools.getShell(true).add(command1);
                                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.ok)).withBackgroundColorId(R.color.textview1good).show();
                                                                             if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                                                                                 new AlertDialog.Builder(getContext())
                                                                                         .setTitle(R.string.reboot)
                                                                                         .setMessage(R.string.rebootdialog)
                                                                                         .setCancelable(false)
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
                                                                                         .setMessage(R.string.rebootdialog)
                                                                                         .setCancelable(false)
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
                                                                                         .setMessage(R.string.rebootdialog)
                                                                                         .setCancelable(false)
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
                                                                                 "rm -f /system/etc/ppp/options",
                                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                                 "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                                                                         );
                                                                         try {
                                                                             RootTools.getShell(true).add(command1);
                                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.disable)).withBackgroundColorId(R.color.textview1good).show();
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
        });
    }


}