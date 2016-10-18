package com.nowenui.systemtweakerfree.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.nowenui.systemtweakerfree.R;
import com.stericson.rootshell.exceptions.RootDeniedException;
import com.stericson.rootshell.execution.Command;
import com.stericson.roottools.RootTools;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MediaServerFragment extends Fragment {

    public Button stopmediaserver, stopmediascanner, start;
    public CheckBox checkbox31, checkbox32;
    private boolean isClicked;

    public static MediaServerFragment newInstance(Bundle bundle) {
        MediaServerFragment messagesFragment = new MediaServerFragment();

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
        setRetainInstance(true);
        RootTools.debugMode = false;

        View view = inflater.inflate(R.layout.fragment_mediascanner, parent, false);

        checkbox31 = (CheckBox) view.findViewById(R.id.checkBox31);
        String check17 = "/etc/init.d/01MediaServelKilling";
        String check17a = "/system/etc/init.d/01MediaServelKilling";
        if (new File(Environment.getRootDirectory() + check17).exists() || new File(check17a).exists() || new File(Environment.getRootDirectory() + check17a).exists()) {
            checkbox31.setChecked(true);
        } else {
            checkbox31.setChecked(false);
        }
        checkbox31.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {

                                                      if (isChecked) {
                                                          if (RootTools.isRootAvailable()) {
                                                              if (RootTools.isAccessGiven()) {
                                                                  Command command1 = new Command(0,
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                          "cp /data/data/com.nowenui.systemtweakerfree/files/01MediaServelKilling /system/etc/init.d/",
                                                                          "chmod 777 /system/etc/init.d/01MediaServelKilling",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                                                                  try {
                                                                      RootTools.getShell(true).add(command1);
                                                                      Toast.makeText(getActivity(), "OK!", Toast.LENGTH_SHORT).show();
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
                                                          if (RootTools.isRootAvailable()) {
                                                              if (RootTools.isAccessGiven()) {
                                                                  Command command1 = new Command(0,
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                          "rm -f /system/etc/init.d/01MediaServelKilling",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                                                                  );
                                                                  try {
                                                                      RootTools.getShell(true).add(command1);
                                                                      Toast.makeText(getActivity(), R.string.disable, Toast.LENGTH_SHORT).show();
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
                                                      }

                                                  }
                                              }

        );

        checkbox32 = (CheckBox) view.findViewById(R.id.checkBox32);
        String check18 = "/etc/init.d/01MediaScannerKilling";
        String check18a = "/system/etc/init.d/01MediaScannerKilling";
        if (new File(Environment.getRootDirectory() + check18).exists() || new File(check18a).exists() || new File(Environment.getRootDirectory() + check18a).exists()) {
            checkbox32.setChecked(true);
        } else {
            checkbox32.setChecked(false);
        }
        checkbox32.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {
                                                      if (isChecked) {

                                                          if (RootTools.isRootAvailable()) {
                                                              if (RootTools.isAccessGiven()) {
                                                                  Command command1 = new Command(0,
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                          "cp /data/data/com.nowenui.systemtweakerfree/files/01MediaScannerKilling /system/etc/init.d/01MediaScannerKilling",
                                                                          "chmod 777 /system/etc/init.d/01MediaScannerKilling",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                                                                  );
                                                                  try {
                                                                      RootTools.getShell(true).add(command1);
                                                                      Toast.makeText(getActivity(), "OK!", Toast.LENGTH_SHORT).show();
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
                                                          if (RootTools.isRootAvailable()) {
                                                              if (RootTools.isAccessGiven()) {
                                                                  Command command1 = new Command(0,
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                          "rm -f /system/etc/init.d/01MediaScannerKilling",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
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
                                                      }

                                                  }
                                              }

        );

        stopmediaserver = (Button) view.findViewById(R.id.stopmediaserver);
        stopmediaserver.setBackgroundResource(R.drawable.roundbuttoncal);
        stopmediaserver.setTextColor(Color.WHITE);
        start = (Button) view.findViewById(R.id.start);
        start.setBackgroundResource(R.drawable.roundbuttoncal);
        start.setTextColor(Color.WHITE);
        stopmediascanner = (Button) view.findViewById(R.id.stopmediascanner);
        stopmediascanner.setBackgroundResource(R.drawable.roundbuttoncal);
        stopmediascanner.setTextColor(Color.WHITE);

        stopmediaserver.setOnClickListener(new View.OnClickListener() {
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
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox killall -9 android.process.media",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox killall -9 mediaserver",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                        );
                        try {
                            RootTools.getShell(true).add(command1);
                            Toast.makeText(getActivity(), R.string.sttoppedmediaserver, Toast.LENGTH_SHORT).show();
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
            }

        });

        stopmediascanner.setOnClickListener(new View.OnClickListener() {
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
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                "pm disable com.android.providers.media/com.android.providers.media.MediaScannerReceiver",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                        );
                        try {
                            RootTools.getShell(true).add(command1);
                            Toast.makeText(getActivity(), R.string.sttoppedmediascanner, Toast.LENGTH_SHORT).show();
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
            }

        });

        start.setOnClickListener(new View.OnClickListener() {
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
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                "pm enable com.android.providers.media/com.android.providers.media.MediaScannerReceiver",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                        );
                        try {
                            RootTools.getShell(true).add(command1);
                            Toast.makeText(getActivity(), R.string.startpedmediaserver, Toast.LENGTH_SHORT).show();
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
            }

        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {

        super.onPause();
    }

}