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
import android.widget.Toast;

import com.nowenui.systemtweakerfree.R;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BatteryTweaksFragment extends Fragment {

    private CheckBox checkbox, checkbox2, checkbox3, checkbox4, checkbox5, checkbox8, checkbox7, checkbox6;


    public static BatteryTweaksFragment newInstance(Bundle bundle) {
        BatteryTweaksFragment messagesFragment = new BatteryTweaksFragment();

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
        View view = inflater.inflate(R.layout.fragment_batterytweaks, parent, false);

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

        checkbox = (CheckBox) view.findViewById(R.id.checkBox);
        String check1 = "/etc/init.d/05FixGoogleServicedrain";
        String check1a = "/system/etc/init.d/05FixGoogleServicedrain";
        if (new File(Environment.getRootDirectory() + check1).exists() || new File(check1a).exists() || new File(Environment.getRootDirectory() + check1a).exists()) {
            checkbox.setChecked(true);
        } else {
            checkbox.setChecked(false);
        }
        checkbox2 = (CheckBox) view.findViewById(R.id.checkBox2);
        String check2 = "/etc/init.d/00BatteryImprove";
        String check2a = "/system/etc/init.d/00BatteryImprove";
        if (new File(Environment.getRootDirectory() + check2).exists() || new File(check2a).exists() || new File(Environment.getRootDirectory() + check2a).exists()) {
            checkbox2.setChecked(true);
        } else {
            checkbox2.setChecked(false);
        }
        checkbox.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
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
                return true;    // <- set to true
            }
        });
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView,
                                                                             boolean isChecked) {

                                                    if (isChecked) {

                                                        if (RootTools.isBusyboxAvailable()) {
                                                            if (RootTools.isRootAvailable()) {
                                                                if (RootTools.isAccessGiven()) {
                                                                    Command command1 = new Command(0,
                                                                            "busybox mount -o rw,remount /proc /system",
                                                                            "cp  /sdcard/SystemTweakerFREE/05FixGoogleServicedrain /system/etc/init.d/",
                                                                            "chmod 777 /system/etc/init.d/05FixGoogleServicedrain",
                                                                            "dos2unix /system/etc/init.d/05FixGoogleServicedrain",
                                                                            "sh /system/etc/init.d/05FixGoogleServicedrain",
                                                                            "busybox mount -o ro,remount /proc /system");
                                                                    try {
                                                                        RootTools.getShell(true).add(command1);
                                                                        Toast.makeText(getActivity(), R.string.tweakenabled, Toast.LENGTH_SHORT).show();
                                                                    } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                        ex.printStackTrace();
                                                                        Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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

                                                    } else {
                                                        if (RootTools.isBusyboxAvailable()) {
                                                            if (RootTools.isRootAvailable()) {
                                                                if (RootTools.isAccessGiven()) {
                                                                    Command command1 = new Command(0,
                                                                            "busybox mount -o rw,remount /proc /system",
                                                                            "rm -f /system/etc/init.d/05FixGoogleServicedrain",
                                                                            "busybox mount -o ro,remount /proc /system");
                                                                    try {
                                                                        RootTools.getShell(true).add(command1);
                                                                        Toast.makeText(getActivity(), R.string.tweakdisabled, Toast.LENGTH_SHORT).show();
                                                                    } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                        ex.printStackTrace();
                                                                        Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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

                                                }
                                            }

        );
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

                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "cp  /sdcard/SystemTweakerFREE/00BatteryImprove /system/etc/init.d/",
                                                                             "chmod 777 /system/etc/init.d/00BatteryImprove",
                                                                             "dos2unix /system/etc/init.d/00BatteryImprove",
                                                                             "sh /system/etc/init.d/00BatteryImprove",
                                                                             "busybox mount -o ro,remount /proc /system");
                                                                     try {

                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakenabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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


                                                     } else {
                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "rm -f /system/etc/init.d/00BatteryImprove",
                                                                             "busybox mount -o ro,remount /proc /system");
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakdisabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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

                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "busybox sed -i '/wifi.supplicant_scan_interval/d' /system/build.prop",
                                                                             "echo \"wifi.supplicant_scan_interval=220\" >> /system/build.prop",
                                                                             "busybox mount -o ro,remount /proc /system");
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakenabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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


                                                     } else {
                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "busybox sed -i '/wifi.supplicant_scan_interval/d' /system/build.prop",
                                                                             "echo \"wifi.supplicant_scan_interval=160\" >> /system/build.prop",
                                                                             "busybox mount -o ro,remount /proc /system"
                                                                     );
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakdisabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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

                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "busybox sed -i '/ro.ril.power.collapse/d' /system/build.prop",
                                                                             "busybox sed -i '/ro.ril.disable.power.collapse/d' /system/build.prop",
                                                                             "busybox sed -i '/pm.sleep_mode/d' /system/build.prop",
                                                                             "busybox sed -i '/ro.vold.umsdirtyratio/d' /system/build.prop",
                                                                             "echo \"ro.ril.disable.power.collapse=0\" >> /system/build.prop",
                                                                             "echo \"ro.vold.umsdirtyratio=20\" >> /system/build.prop",
                                                                             "echo \"pm.sleep_mode=1\" >> /system/build.prop",
                                                                             "busybox mount -o ro,remount /proc /system");
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakenabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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


                                                     } else {
                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "busybox sed -i '/ro.ril.disable.power.collapse=0/d' /system/build.prop",
                                                                             "busybox sed -i '/ro.vold.umsdirtyratio/d' /system/build.prop",
                                                                             "busybox sed -i '/pm.sleep_mode=1/d' /system/build.prop",
                                                                             "busybox mount -o ro,remount /proc /system"
                                                                     );
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakdisabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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

                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "busybox sed -i '/power_supply.wakeup/d' /system/build.prop",
                                                                             "echo \"power_supply.wakeup=enable\" >> /system/build.prop",
                                                                             "busybox mount -o ro,remount /proc /system");
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakenabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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


                                                     } else {
                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "busybox sed -i '/power_supply.wakeup=enable/d' /system/build.prop",
                                                                             "busybox mount -o ro,remount /proc /system"
                                                                     );
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakdisabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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

                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "busybox sed -i '/ro.ril.sensor.sleep.control/d' /system/build.prop",
                                                                             "echo \"ro.ril.sensor.sleep.control=1\" >> /system/build.prop",
                                                                             "busybox mount -o ro,remount /proc /system");
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakenabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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


                                                     } else {
                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "busybox sed -i '/ro.ril.sensor.sleep.control=1/d' /system/build.prop",
                                                                             "busybox mount -o ro,remount /proc /system"
                                                                     );
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakdisabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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

                                                 }
                                             }

        );
        checkbox8 = (CheckBox) view.findViewById(R.id.checkBox8);
        if (text.toString().contains("ro.mot.eri.losalert.delay=1000")) {
            checkbox8.setChecked(true);
        } else {
            checkbox8.setChecked(false);
        }
        checkbox8.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.battery7)
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
        checkbox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {
                                                     if (isChecked) {

                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "busybox sed -i '/ro.mot.eri.losalert.delay/d' /system/build.prop",
                                                                             "echo \"ro.mot.eri.losalert.delay=1000\" >> /system/build.prop",
                                                                             "busybox mount -o ro,remount /proc /system");
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakenabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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


                                                     } else {
                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "busybox sed -i '/ro.mot.eri.losalert.delay=1000/d' /system/build.prop",
                                                                             "busybox mount -o ro,remount /proc /system"
                                                                     );
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakdisabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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

                                                 }
                                             }

        );

        checkbox7 = (CheckBox) view.findViewById(R.id.checkBox7);
        if (text.toString().contains("ro.config.nocheckin=1")
                && text.toString().contains("profiler.force_disable_err_rpt=1")
                && text.toString().contains("profiler.force_disable_ulog=1")) {
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

                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "busybox sed -i '/ro.config.nocheckin/d' /system/build.prop",
                                                                             "busybox sed -i '/profiler.force_disable_err_rpt/d' /system/build.prop",
                                                                             "busybox sed -i '/profiler.force_disable_ulog/d' /system/build.prop",
                                                                             "echo \"ro.config.nocheckin=1\" >> /system/build.prop",
                                                                             "echo \"profiler.force_disable_err_rpt=1\" >> /system/build.prop",
                                                                             "echo \"profiler.force_disable_ulog=1\" >> /system/build.prop",
                                                                             "busybox mount -o ro,remount /proc /system");
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakenabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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


                                                     } else {
                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "busybox sed -i '/ro.config.nocheckin=1/d' /system/build.prop",
                                                                             "busybox sed -i '/profiler.force_disable_err_rpt=1/d' /system/build.prop",
                                                                             "busybox sed -i '/profiler.force_disable_ulog=1/d' /system/build.prop",
                                                                             "busybox mount -o ro,remount /proc /system"
                                                                     );
                                                                     try {
                                                                         RootTools.getShell(true).add(command1);
                                                                         Toast.makeText(getActivity(), R.string.tweakdisabled, Toast.LENGTH_SHORT).show();
                                                                     } catch (IOException | RootDeniedException | TimeoutException ex) {
                                                                         ex.printStackTrace();
                                                                         Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
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

                                                 }
                                             }

        );


    }


}