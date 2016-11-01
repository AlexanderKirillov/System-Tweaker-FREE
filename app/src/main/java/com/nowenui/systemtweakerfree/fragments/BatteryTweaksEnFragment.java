package com.nowenui.systemtweakerfree.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
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
import com.stericson.rootshell.exceptions.RootDeniedException;
import com.stericson.rootshell.execution.Command;
import com.stericson.roottools.RootTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BatteryTweaksEnFragment extends Fragment {

    private CheckBox checkbox2, checkbox3, checkbox4, checkbox5, checkbox7, checkbox6;


    public static BatteryTweaksEnFragment newInstance(Bundle bundle) {
        BatteryTweaksEnFragment messagesFragment = new BatteryTweaksEnFragment();

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
        View view = inflater.inflate(R.layout.fragment_batterytweaks_en, parent, false);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RootTools.debugMode = false;

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

        checkbox2 = (CheckBox) view.findViewById(R.id.checkBox2);
        String check2 = "/etc/init.d/00BatteryImprove";
        String check2a = "/system/etc/init.d/00BatteryImprove";
        if (new File(Environment.getRootDirectory() + check2).exists() || new File(check2a).exists() || new File(Environment.getRootDirectory() + check2a).exists()) {
            checkbox2.setChecked(true);
        } else {
            checkbox2.setChecked(false);
        }
        checkbox2.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
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
                return true;    // <- set to true
            }
        });
        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {
                                                     if (isChecked) {

                                                         if (RootTools.isRootAvailable()) {
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
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }


                                                     } else {
                                                         if (RootTools.isRootAvailable()) {
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

                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     }

                                                 }
                                             }

        );

        checkbox3 = (CheckBox) view.findViewById(R.id.checkBox3);
        if (text.toString().contains("wifi.supplicant_scan_interval=220")) {
            checkbox3.setChecked(true);
        } else {
            checkbox3.setChecked(false);
        }
        checkbox3.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
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
                return true;    // <- set to true
            }
        });
        checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {
                                                     if (isChecked) {

                                                         if (RootTools.isRootAvailable()) {
                                                             if (RootTools.isAccessGiven()) {
                                                                 Command command1 = new Command(0,
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/wifi.supplicant_scan_interval/d' /system/build.prop",
                                                                         "echo \"wifi.supplicant_scan_interval=220\" >> /system/build.prop",
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
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }

                                                     } else {
                                                         if (RootTools.isRootAvailable()) {
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

                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     }

                                                 }
                                             }

        );
        checkbox4 = (CheckBox) view.findViewById(R.id.checkBox4);
        if (text.toString().contains("ro.ril.disable.power.collapse=0")
                && text.toString().contains("ro.vold.umsdirtyratio=20")
                && text.toString().contains("pm.sleep_mode=1")) {
            checkbox4.setChecked(true);
        } else {
            checkbox4.setChecked(false);
        }
        checkbox4.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
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
                return true;    // <- set to true
            }
        });
        checkbox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {

                                                     if (isChecked) {

                                                         if (RootTools.isRootAvailable()) {
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
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }


                                                     } else {
                                                         if (RootTools.isRootAvailable()) {
                                                             if (RootTools.isAccessGiven()) {
                                                                 Command command1 = new Command(0,
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.disable.power.collapse=0/d' /system/build.prop",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.vold.umsdirtyratio/d' /system/build.prop",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/pm.sleep_mode=1/d' /system/build.prop",
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

                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     }

                                                 }
                                             }

        );
        checkbox5 = (CheckBox) view.findViewById(R.id.checkBox5);
        if (text.toString().contains("power_supply.wakeup=enable")) {
            checkbox5.setChecked(true);
        } else {
            checkbox5.setChecked(false);
        }
        checkbox5.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
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
                return true;    // <- set to true
            }
        });
        checkbox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {

                                                     if (isChecked) {
                                                         if (RootTools.isRootAvailable()) {
                                                             if (RootTools.isAccessGiven()) {
                                                                 Command command1 = new Command(0,
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/power_supply.wakeup/d' /system/build.prop",
                                                                         "echo \"power_supply.wakeup=enable\" >> /system/build.prop",
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
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }


                                                     } else {
                                                         if (RootTools.isRootAvailable()) {
                                                             if (RootTools.isAccessGiven()) {
                                                                 Command command1 = new Command(0,
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/power_supply.wakeup=enable/d' /system/build.prop",
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

                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     }

                                                 }
                                             }

        );
        checkbox6 = (CheckBox) view.findViewById(R.id.checkBox6);
        if (text.toString().contains("ro.ril.sensor.sleep.control=1")) {
            checkbox6.setChecked(true);
        } else {
            checkbox6.setChecked(false);
        }
        checkbox6.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
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
                return true;    // <- set to true
            }
        });
        checkbox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {

                                                     if (isChecked) {

                                                         if (RootTools.isRootAvailable()) {
                                                             if (RootTools.isAccessGiven()) {
                                                                 Command command1 = new Command(0,
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.sensor.sleep.control/d' /system/build.prop",
                                                                         "echo \"ro.ril.sensor.sleep.control=1\" >> /system/build.prop",
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
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }


                                                     } else {
                                                         if (RootTools.isRootAvailable()) {
                                                             if (RootTools.isAccessGiven()) {
                                                                 Command command1 = new Command(0,
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.sensor.sleep.control=1/d' /system/build.prop",
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

                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     }

                                                 }
                                             }

        );
        checkbox7 = (CheckBox) view.findViewById(R.id.checkBox7);
        String check00 = "/etc/init.d/logs";
        String check00a = "/system/etc/init.d/logs";
        if ((text.toString().contains("ro.config.nocheckin=1")
                && text.toString().contains("profiler.force_disable_err_rpt=1")
                && text.toString().contains("profiler.force_disable_ulog=1") && (new File(Environment.getRootDirectory() + check00).exists() || new File(check00a).exists() || new File(Environment.getRootDirectory() + check00a).exists()))) {
            checkbox7.setChecked(true);
        } else {
            checkbox7.setChecked(false);
        }
        checkbox7.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
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
                return true;    // <- set to true
            }
        });
        checkbox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {
                                                     if (isChecked) {

                                                         if (RootTools.isRootAvailable()) {
                                                             if (RootTools.isAccessGiven()) {
                                                                 Command command1 = new Command(0,
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox mount -o rw,remount /proc /system",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox sed -i '/ro.config.nocheckin/d' /system/build.prop",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox sed -i '/profiler.force_disable_err_rpt/d' /system/build.prop",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox sed -i '/profiler.force_disable_ulog/d' /system/build.prop",
                                                                         "echo \"ro.config.nocheckin=1\" >> /system/build.prop",
                                                                         "echo \"profiler.force_disable_err_rpt=1\" >> /system/build.prop",
                                                                         "echo \"profiler.force_disable_ulog=1\" >> /system/build.prop",
                                                                         "cp /data/data/com.nowenui.systemtweaker/files/logs /system/etc/init.d/",
                                                                         "chmod 777 /system/etc/init.d/logs",
                                                                         "/system/etc/init.d/logs",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox mount -o ro,remount /proc /system",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox mount -o remount,ro /system");
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
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }

                                                     } else {
                                                         if (RootTools.isRootAvailable()) {
                                                             if (RootTools.isAccessGiven()) {
                                                                 Command command1 = new Command(0,
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox mount -o rw,remount /proc /system",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox sed -i '/ro.config.nocheckin=1/d' /system/build.prop",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox sed -i '/profiler.force_disable_err_rpt=1/d' /system/build.prop",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox sed -i '/profiler.force_disable_ulog=1/d' /system/build.prop",
                                                                         "rm -f /system/etc/init.d/logs",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox mount -o ro,remount /proc /system",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                         "/data/data/com.nowenui.systemtweaker/files/busybox mount -o remount,ro /system"
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

                                                         } else {
                                                             new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                                                         }
                                                     }

                                                 }
                                             }

        );


    }
}