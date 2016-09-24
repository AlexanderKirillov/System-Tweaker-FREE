package com.nowenui.systemtweakerfree.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
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
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FstrimFragment extends Fragment {

    private CheckBox system, data, cache, bootfstrim;
    private Button fstrimbutton, install, deletefstrim;
    private boolean isClicked;

    public static FstrimFragment newInstance(Bundle bundle) {
        FstrimFragment messagesFragment = new FstrimFragment();

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

        View view = inflater.inflate(R.layout.fragment_fstrim, parent, false);

        system = (CheckBox) view.findViewById(R.id.system);
        data = (CheckBox) view.findViewById(R.id.data);
        cache = (CheckBox) view.findViewById(R.id.cache);
        fstrimbutton = (Button) view.findViewById(R.id.fstrimbutton);

        install = (Button) view.findViewById(R.id.install);
        install.setBackgroundResource(R.drawable.roundbuttoncal);
        install.setTextColor(Color.WHITE);
        File f = new File("/system/bin/fstrim");
        final Resources res = getResources();
        if (f.exists()) {
            install.setEnabled(false);
            install.setBackgroundResource(R.drawable.roundbuttonfuck);
            install.setText(res.getString(R.string.fstrimlibok));
        } else {

            install.setOnClickListener(new View.OnClickListener() {
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
                                    "cp /data/data/com.nowenui.systemtweakerfree/files/fstrim /system/bin/fstrim",
                                    "chmod 777 /system/bin/fstrim",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system");
                            try {
                                RootTools.getShell(true).add(command1);
                                Toast.makeText(getActivity(), R.string.fileinstall, Toast.LENGTH_SHORT).show();
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
        }

        if (f.exists()) {
            deletefstrim = (Button) view.findViewById(R.id.deletefstrim);
            deletefstrim.setEnabled(true);
            deletefstrim.setTextColor(Color.WHITE);
            deletefstrim.setBackgroundResource(R.drawable.roundbuttoncal);
            deletefstrim.setText(res.getString(R.string.delstrim));
            deletefstrim.setOnClickListener(new View.OnClickListener() {
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
                                    "rm -f /system/bin/fstrim",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system");
                            try {
                                RootTools.getShell(true).add(command1);
                                Toast.makeText(getActivity(), "Ok", Toast.LENGTH_SHORT).show();
                            } catch (IOException | RootDeniedException | TimeoutException ex) {
                                ex.printStackTrace();
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
        } else {
            deletefstrim = (Button) view.findViewById(R.id.deletefstrim);
            deletefstrim.setEnabled(false);
            deletefstrim.setTextColor(Color.WHITE);
            deletefstrim.setBackgroundResource(R.drawable.roundbuttonfuck);
        }

        if (f.exists()) {
            fstrimbutton.setBackgroundResource(R.drawable.roundbuttoncal);
            fstrimbutton.setTextColor(Color.WHITE);
            fstrimbutton.setTextSize(20);
            fstrimbutton.setEnabled(true);
            fstrimbutton.setOnClickListener(new View.OnClickListener() {
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
                    if (system.isChecked() & data.isChecked() == false & cache.isChecked() == false) {
                        if (RootTools.isRootAvailable()) {
                            if (RootTools.isAccessGiven()) {
                                Command command1 = new Command(0,
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /system",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system");
                                try {
                                    RootTools.getShell(true).add(command1);
                                    Toast.makeText(getActivity(), "fstrim /system... OK!", Toast.LENGTH_SHORT).show();
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
                    } else if (data.isChecked() & system.isChecked() == false & cache.isChecked() == false) {
                        if (RootTools.isRootAvailable()) {
                            if (RootTools.isAccessGiven()) {
                                Command command1 = new Command(0,
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount data",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /data",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount data");
                                try {
                                    RootTools.getShell(true).add(command1);
                                    Toast.makeText(getActivity(), "fstrim /data... OK!", Toast.LENGTH_SHORT).show();
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
                    } else if (cache.isChecked() & data.isChecked() == false & system.isChecked() == false) {
                        if (RootTools.isRootAvailable()) {
                            if (RootTools.isAccessGiven()) {
                                Command command1 = new Command(0,
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount cache",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /cache",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount cache");
                                try {
                                    RootTools.getShell(true).add(command1);
                                    Toast.makeText(getActivity(), "fstrim /cache... OK!", Toast.LENGTH_SHORT).show();
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

                    } else if (system.isChecked() & data.isChecked() & cache.isChecked() == false) {
                        if (RootTools.isRootAvailable()) {
                            if (RootTools.isAccessGiven()) {
                                Command command1 = new Command(0,
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount data",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /system",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /data",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount data");
                                try {
                                    RootTools.getShell(true).add(command1);
                                    Toast.makeText(getActivity(), "fstrim /data & /system... OK!", Toast.LENGTH_SHORT).show();
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

                    } else if (system.isChecked() & cache.isChecked() & data.isChecked() == false) {
                        if (RootTools.isRootAvailable()) {
                            if (RootTools.isAccessGiven()) {
                                Command command1 = new Command(0,
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount cache",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /system",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /cache",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount cache");
                                try {
                                    RootTools.getShell(true).add(command1);
                                    Toast.makeText(getActivity(), "fstrim /system & /cache... OK!", Toast.LENGTH_SHORT).show();
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
                    } else if (data.isChecked() & cache.isChecked() & system.isChecked() == false) {
                        if (RootTools.isRootAvailable()) {
                            if (RootTools.isAccessGiven()) {
                                Command command1 = new Command(0,
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount data",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount cache",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /data",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /cache",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount data",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount cache");
                                try {
                                    RootTools.getShell(true).add(command1);
                                    Toast.makeText(getActivity(), "fstrim /data & /cache... OK!", Toast.LENGTH_SHORT).show();
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
                    } else if (system.isChecked() & data.isChecked() & cache.isChecked()) {
                        if (RootTools.isRootAvailable()) {
                            if (RootTools.isAccessGiven()) {
                                Command command1 = new Command(0,
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount data",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount cache",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /system",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /cache",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /data",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount data",
                                        "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount cache");
                                try {
                                    RootTools.getShell(true).add(command1);
                                    Toast.makeText(getActivity(), "fstrim /data & /system & /cache... OK!", Toast.LENGTH_SHORT).show();
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
            });
        } else {
            fstrimbutton.setEnabled(false);
            fstrimbutton.setBackgroundResource(R.drawable.roundbuttonfuck);
            fstrimbutton.setTextColor(Color.WHITE);
            fstrimbutton.setTextSize(20);
        }


        bootfstrim = (CheckBox) view.findViewById(R.id.bootfstrim);
        String check20 = "/etc/init.d/70fstrim";
        String check20a = "/system/etc/init.d/70fstrim";
        if (new File(Environment.getRootDirectory() + check20).exists() || new File(check20a).exists() || new File(Environment.getRootDirectory() + check20a).exists()) {
            bootfstrim.setChecked(true);
        } else {
            bootfstrim.setChecked(false);
        }
        bootfstrim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,
                                                                               boolean isChecked) {
                                                      if (isChecked) {

                                                          if (RootTools.isRootAvailable()) {
                                                              if (RootTools.isAccessGiven()) {
                                                                  Command command1 = new Command(0,
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                          "cp /data/data/com.nowenui.systemtweakerfree/files/70fstrim /system/etc/init.d/70fstrim",
                                                                          "chmod 777 /system/etc/init.d/70fstrim",
                                                                          "dos2unix /system/etc/init.d/70fstrim",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system");
                                                                  try {
                                                                      RootTools.getShell(true).add(command1);
                                                                      Toast.makeText(getActivity(), R.string.ok, Toast.LENGTH_SHORT).show();
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
                                                                          "rm -f /system/etc/init.d/70fstrim",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system"
                                                                  );
                                                                  try {
                                                                      RootTools.getShell(true).add(command1);
                                                                      Toast.makeText(getActivity(), R.string.delete, Toast.LENGTH_SHORT).show();
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

        return view;
    }

}