package com.nowenui.systemtweakerfree.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.nowenui.systemtweakerfree.R;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

public class SystemTweaksFragment extends Fragment {

    private CheckBox checkbox16, checkbox20, checkbox21, checkbox22, checkbox23, checkbox30, ext4tweak, display_cal;


    public static SystemTweaksFragment newInstance(Bundle bundle) {
        SystemTweaksFragment messagesFragment = new SystemTweaksFragment();

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

        View view = inflater.inflate(R.layout.fragment_systemtweaks, parent, false);


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
        checkbox16 = (CheckBox) view.findViewById(R.id.checkBox16);
        if (text.toString().contains("ro.HOME_APP_ADJ=1")) {
            checkbox16.setChecked(true);
        } else {
            checkbox16.setChecked(false);
        }
        checkbox16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {
                                                      if (isChecked) {

                                                          if (RootTools.isBusyboxAvailable()) {
                                                              if (RootTools.isRootAvailable()) {
                                                                  if (RootTools.isAccessGiven()) {
                                                                      Command command1 = new Command(0,
                                                                              "busybox mount -o rw,remount /proc /system",
                                                                              "busybox sed -i '/ro.HOME_APP_ADJ/d' /system/build.prop",
                                                                              "echo \"ro.HOME_APP_ADJ=1\" >> /system/build.prop",
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
                                                                              "busybox sed -i '/ro.HOME_APP_ADJ/d' /system/build.prop",
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

        display_cal = (CheckBox) view.findViewById(R.id.display_cal);
        String check10 = "/system/etc/init.d/display";
        if (new File(check10).exists()) {
            display_cal.setChecked(true);
        } else {
            display_cal.setChecked(false);
        }
        display_cal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView,
                                                                                boolean isChecked) {
                                                       if (isChecked) {

                                                           if (RootTools.isBusyboxAvailable()) {
                                                               if (RootTools.isRootAvailable()) {
                                                                   if (RootTools.isAccessGiven()) {
                                                                       Command command1 = new Command(0,
                                                                               "busybox mount -o rw,remount /proc /system",
                                                                               "cp /sdcard/Android/data/com.nowenui.systemtweaker/files/display /system/etc/init.d/",
                                                                               "chmod 777 /system/etc/init.d/display",
                                                                               "dos2unix /system/etc/init.d/display",
                                                                               "cp /sdcard/Android/data/com.nowenui.systemtweaker/files/ad_calib.cfg /system/etc/",
                                                                               "chmod 755 /system/etc/ad_calib.cfg",
                                                                               "sh /system/etc/init.d/display",
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
                                                                               "rm -f /system/etc/init.d/display",
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


        checkbox20 = (CheckBox) view.findViewById(R.id.checkBox20);
        if (text.toString().contains("ro.kernel.android.checkjni=0")) {
            checkbox20.setChecked(true);
        } else {
            checkbox20.setChecked(false);
        }
        checkbox20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {

                                                      if (isChecked) {

                                                          if (RootTools.isBusyboxAvailable()) {
                                                              if (RootTools.isRootAvailable()) {
                                                                  if (RootTools.isAccessGiven()) {
                                                                      Command command1 = new Command(0,
                                                                              "busybox mount -o rw,remount /proc /system",
                                                                              "busybox sed -i '/ro.kernel.android.checkjni/d' /system/build.prop",
                                                                              "echo \"ro.kernel.android.checkjni=0\" >> /system/build.prop",
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
                                                                              "busybox sed -i '/ro.kernel.android.checkjni/d' /system/build.prop",
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

        checkbox21 = (CheckBox) view.findViewById(R.id.checkBox21);
        String ch21 = "/system/etc/init.d/81GPU_rendering";
        if (text.toString().contains("debug.composition.type=gpu")
                && text.toString().contains("debug.sf.hw=1")
                && text.toString().contains("debug.performance.tuning=1")
                && text.toString().contains("debug.egl.profiler=1")
                && text.toString().contains("debug.egl.hw=1")
                && new File(ch21).exists()) {
            checkbox21.setChecked(true);
        } else {
            checkbox21.setChecked(false);
        }
        checkbox21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {
                                                      if (isChecked) {

                                                          if (RootTools.isBusyboxAvailable()) {
                                                              if (RootTools.isRootAvailable()) {
                                                                  if (RootTools.isAccessGiven()) {
                                                                      Command command1 = new Command(0,
                                                                              "busybox mount -o rw,remount /proc /system",
                                                                              "cp /sdcard/Android/data/com.nowenui.systemtweaker/files/81GPU_rendering /system/etc/init.d/",
                                                                              "chmod 777 /system/etc/init.d/81GPU_rendering",
                                                                              "dos2unix /system/etc/init.d/81GPU_rendering",
                                                                              "sh /system/etc/init.d/81GPU_rendering",
                                                                              "busybox mount -o rw,remount /proc /system",
                                                                              "busybox sed -i '/debug.sf.hw/d' /system/build.prop",
                                                                              "busybox sed -i '/debug.performance.tuning/d' /system/build.prop",
                                                                              "busybox sed -i '/video.accelerate.hw/d' /system/build.prop",
                                                                              "busybox sed -i '/debug.egl.profiler/d' /system/build.prop",
                                                                              "busybox sed -i '/debug.egl.hw/d' /system/build.prop",
                                                                              "busybox sed -i '/debug.composition.type/d' /system/build.prop",
                                                                              "echo \"debug.sf.hw=1\" >> /system/build.prop",
                                                                              "echo \"debug.performance.tuning=1\" >> /system/build.prop",
                                                                              "echo \"video.accelerate.hw=1\" >> /system/build.prop",
                                                                              "echo \"debug.egl.profiler=1\" >> /system/build.prop",
                                                                              "echo \"debug.egl.hw=1\" >> /system/build.prop",
                                                                              "echo \"debug.composition.type=gpu\" >> /system/build.prop",
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
                                                                              "rm -f /system/etc/init.d/81GPU_rendering",
                                                                              "busybox sed -i '/debug.sf.hw/d' /system/build.prop",
                                                                              "busybox sed -i '/debug.performance.tuning/d' /system/build.prop",
                                                                              "busybox sed -i '/video.accelerate.hw/d' /system/build.prop",
                                                                              "busybox sed -i '/debug.egl.profiler/d' /system/build.prop",
                                                                              "busybox sed -i '/debug.egl.hw/d' /system/build.prop",
                                                                              "busybox sed -i '/debug.composition.type/d' /system/build.prop",
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

        checkbox22 = (CheckBox) view.findViewById(R.id.checkBox22);
        if (text.toString().contains("windowsmgr.max_events_per_sec=150")) {
            checkbox22.setChecked(true);
        } else {
            checkbox22.setChecked(false);
        }
        checkbox22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {
                                                      if (isChecked) {

                                                          if (RootTools.isBusyboxAvailable()) {
                                                              if (RootTools.isRootAvailable()) {
                                                                  if (RootTools.isAccessGiven()) {
                                                                      Command command1 = new Command(0,
                                                                              "busybox mount -o rw,remount /proc /system",
                                                                              "busybox sed -i '/windowsmgr.max_events_per_sec/d' /system/build.prop",
                                                                              "echo \"windowsmgr.max_events_per_sec=150\" >> /system/build.prop",
                                                                              "busybox mount -o ro,remount /proc /system"
                                                                      );
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
                                                                              "busybox sed -i '/windowsmgr.max_events_per_sec/d' /system/build.prop",
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

        checkbox23 = (CheckBox) view.findViewById(R.id.checkBox23);
        String check11 = "/system/etc/init.d/93zipalign";
        if (new File(check11).exists()) {
            checkbox23.setChecked(true);
        } else {
            checkbox23.setChecked(false);
        }
        checkbox23.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {

                                                      if (isChecked) {

                                                          if (RootTools.isBusyboxAvailable()) {
                                                              if (RootTools.isRootAvailable()) {
                                                                  if (RootTools.isAccessGiven()) {
                                                                      Command command1 = new Command(0,
                                                                              "busybox mount -o rw,remount /proc /system",
                                                                              "cp /sdcard/Android/data/com.nowenui.systemtweaker/files/zipalign /system/xbin/zipalign",
                                                                              "chmod 755 /system/xbin/zipalign",
                                                                              "cp /sdcard/Android/data/com.nowenui.systemtweaker/files/93zipalign /system/etc/init.d/93zipalign",
                                                                              "chmod 777 /system/etc/init.d/93zipalign",
                                                                              "dos2unix /system/etc/init.d/93zipalign",
                                                                              "sh /system/etc/init.d/93zipalign",
                                                                              "busybox mount -o ro,remount /proc /system"
                                                                      );
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
                                                                              "rm -f /system/xbin/zipalign",
                                                                              "rm -f /system/etc/init.d/93zipalign",
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

        checkbox30 = (CheckBox) view.findViewById(R.id.checkBox30);
        String check12 = "/system/etc/init.d/11sqlite";
        if (new File(check12).exists()) {
            checkbox30.setChecked(true);
        } else {
            checkbox30.setChecked(false);
        }
        checkbox30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {
                                                      if (isChecked) {

                                                          if (RootTools.isBusyboxAvailable()) {
                                                              if (RootTools.isRootAvailable()) {
                                                                  if (RootTools.isAccessGiven()) {
                                                                      Command command1 = new Command(0,
                                                                              "busybox mount -o rw,remount /proc /system",
                                                                              "cp /sdcard/Android/data/com.nowenui.systemtweaker/files/sqlite3 /system/xbin/sqlite3",
                                                                              "chmod 755 /system/xbin/sqlite3",
                                                                              "cp /sdcard/Android/data/com.nowenui.systemtweaker/files/11sqlite /system/etc/init.d/11sqlite",
                                                                              "chmod 777 /system/etc/init.d/11sqlite",
                                                                              "dos2unix /system/etc/init.d/11sqlite",
                                                                              "sh /system/etc/init.d/11sqlite",
                                                                              "busybox mount -o ro,remount /proc /system"
                                                                      );
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
                                                                              "rm -f /system/xbin/sqlite3",
                                                                              "rm -f /system/etc/init.d/11sqlite",
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

        ext4tweak = (CheckBox) view.findViewById(R.id.ext4tweak);
        String check13 = "/system/etc/init.d/ext4";
        if (new File(check13).exists()) {
            ext4tweak.setChecked(true);
        } else {
            ext4tweak.setChecked(false);
        }
        ext4tweak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {

                                                     if (isChecked) {

                                                         if (RootTools.isBusyboxAvailable()) {
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "busybox mount -o rw,remount /proc /system",
                                                                             "cp /sdcard/Android/data/com.nowenui.systemtweaker/files/ext4 /system/etc/init.d/",
                                                                             "chmod 777 /system/etc/init.d/ext4",
                                                                             "dos2unix /system/etc/init.d/ext4",
                                                                             "cp /sdcard/Android/data/com.nowenui.systemtweaker/files/tune2fs /system/xbin/",
                                                                             "chmod 777 /system/xbin/tune2fs",
                                                                             "sh /system/etc/init.d/ext4",
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
                                                                             "rm -f /system/etc/init.d/ext4",
                                                                             "rm -f /system/xbin/tune2fs",
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


        final Spinner spinner6 = (Spinner) view.findViewById(R.id.spinner6);

        ArrayAdapter adapter3 =
                ArrayAdapter.createFromResource(getActivity(), R.array.ramlist, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner6.setAdapter(adapter3);
        String fucks = "/system/etc/init.d/ram_gaming";
        if (new File(fucks).exists()) {
            boolean isLangRU = Locale.getDefault().getLanguage().equals("ru");
            boolean isLangBE = Locale.getDefault().getLanguage().equals("be");
            boolean isLangUK = Locale.getDefault().getLanguage().equals("uk");
            if (isLangRU || isLangBE || isLangUK) {
                int spinnerPosition2 = adapter3.getPosition("Игровой");
                spinner6.setSelection(spinnerPosition2);
            } else {
                int spinnerPosition1 = adapter3.getPosition("Gaming");
                spinner6.setSelection(spinnerPosition1);
            }


        }
        String fucks10 = "/system/etc/init.d/ram_balanced";
        if (new File(fucks10).exists()) {
            boolean isLangRU = Locale.getDefault().getLanguage().equals("ru");
            boolean isLangBE = Locale.getDefault().getLanguage().equals("be");
            boolean isLangUK = Locale.getDefault().getLanguage().equals("uk");
            if (isLangRU || isLangBE || isLangUK) {
                int spinnerPosition4 = adapter3.getPosition("Сбалансированный");
                spinner6.setSelection(spinnerPosition4);
            } else {
                int spinnerPosition3 = adapter3.getPosition("Balanced");
                spinner6.setSelection(spinnerPosition3);
            }
        }

        String fucks2 = "/system/etc/init.d/ram_multitasking";
        boolean isLangRU = Locale.getDefault().getLanguage().equals("ru");
        boolean isLangBE = Locale.getDefault().getLanguage().equals("be");
        boolean isLangUK = Locale.getDefault().getLanguage().equals("uk");
        if (new File(fucks2).exists()) {
            if (isLangRU || isLangBE || isLangUK) {
                int spinnerPosition2 = adapter3.getPosition("Многозадачность");
                spinner6.setSelection(spinnerPosition2);
            } else {
                int spinnerPosition1 = adapter3.getPosition("Multitasking");
                spinner6.setSelection(spinnerPosition1);
            }

        }


        spinner6.post(new Runnable() {
            @Override
            public void run() {
                spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String[] choose = getResources().getStringArray(R.array.ramlist);

                        if (choose[position].contains("(стандарт)") || choose[position].contains("(standart)")) {
                            try {
                                Process su = Runtime.getRuntime().exec("su");
                                DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
                                outputStream.writeBytes("busybox mount -o rw,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_gaming\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_balanced\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_multitasking\n");
                                outputStream.flush();
                                outputStream.writeBytes("busybox mount -o ro,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("exit\n");
                                outputStream.flush();
                                Toast.makeText(getActivity(), R.string.ok, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
                            }
                        }
                        if ((choose[position].contains("Gaming")) || (choose[position].contains("Игровой"))) {
                            try {
                                Process su = Runtime.getRuntime().exec("su");
                                DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
                                outputStream.writeBytes("busybox mount -o rw,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("cp /sdcard/Android/data/com.nowenui.systemtweaker/files/ram_gaming /system/etc/init.d/\n");
                                outputStream.flush();
                                outputStream.writeBytes("chmod 777 /system/etc/init.d/ram_gaming\n");
                                outputStream.flush();
                                outputStream.writeBytes("dos2unix /system/etc/init.d/ram_gaming\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_balanced\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_multitasking\n");
                                outputStream.flush();
                                outputStream.writeBytes("sh /system/etc/init.d/ram_gaming");
                                outputStream.flush();
                                outputStream.writeBytes("busybox mount -o ro,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("exit\n");
                                outputStream.flush();
                                Toast.makeText(getActivity(), R.string.ok, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
                            }
                        }
                        if ((choose[position].contains("Balanced")) || (choose[position].contains("Сбалансированный"))) {
                            try {
                                Process su = Runtime.getRuntime().exec("su");
                                DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
                                outputStream.writeBytes("busybox mount -o rw,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("cp /sdcard/Android/data/com.nowenui.systemtweaker/files/ram_balanced /system/etc/init.d/\n");
                                outputStream.flush();
                                outputStream.writeBytes("chmod 777 /system/etc/init.d/ram_balanced\n");
                                outputStream.flush();
                                outputStream.writeBytes("dos2unix /system/etc/init.d/ram_balanced\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_gaming\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_multitasking\n");
                                outputStream.flush();
                                outputStream.writeBytes("sh /system/etc/init.d/ram_balanced");
                                outputStream.flush();
                                outputStream.writeBytes("busybox mount -o ro,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("exit\n");
                                outputStream.flush();
                                Toast.makeText(getActivity(), R.string.ok, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
                            }
                        }
                        if ((choose[position].contains("Multitasking")) || (choose[position].contains("Многозадачность"))) {
                            try {
                                Process su = Runtime.getRuntime().exec("su");
                                DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
                                outputStream.writeBytes("busybox mount -o rw,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("cp /sdcard/Android/data/com.nowenui.systemtweaker/files/ram_multitasking /system/etc/init.d/\n");
                                outputStream.flush();
                                outputStream.writeBytes("chmod 777 /system/etc/init.d/ram_multitasking\n");
                                outputStream.flush();
                                outputStream.writeBytes("dos2unix /system/etc/init.d/ram_multitasking\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_gaming\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_balanced\n");
                                outputStream.flush();
                                outputStream.writeBytes("sh /system/etc/init.d/ram_multitasking");
                                outputStream.flush();
                                outputStream.writeBytes("busybox mount -o ro,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("exit\n");
                                outputStream.flush();
                                Toast.makeText(getActivity(), R.string.ok, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });


        return view;
    }

}