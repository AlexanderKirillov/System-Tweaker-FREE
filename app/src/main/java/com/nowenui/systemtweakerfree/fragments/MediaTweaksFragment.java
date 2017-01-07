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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.github.mrengineer13.snackbar.SnackBar;
import com.nowenui.systemtweakerfree.R;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MediaTweaksFragment extends Fragment {

    private CheckBox checkbox17, checkbox18, checkbox19, ch21, ch22;


    public static MediaTweaksFragment newInstance(Bundle bundle) {
        MediaTweaksFragment messagesFragment = new MediaTweaksFragment();

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

        View view = inflater.inflate(R.layout.fragment_mediatweaks, parent, false);


        checkbox17 = (CheckBox) view.findViewById(R.id.checkBox17);
        if (text.toString().contains("ro.media.enc.jpeg.quality=100")) {
            checkbox17.setChecked(true);
        } else {
            checkbox17.setChecked(false);
        }
        checkbox17.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.med1)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.warning)
                        .show();
                return true;
            }
        });
        checkbox17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.enc.jpeg.quality/d' /system/build.prop",
                                                                          "echo \"ro.media.enc.jpeg.quality=100\" >> /system/build.prop",
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
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.enc.jpeg.quality/d' /system/build.prop",
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

        checkbox18 = (CheckBox) view.findViewById(R.id.checkBox18);
        if (text.toString().contains("ro.media.dec.jpeg.memcap=8000000")
                && text.toString().contains("ro.media.enc.hprof.vid.bps=8000000")) {
            checkbox18.setChecked(true);
        } else {
            checkbox18.setChecked(false);
        }
        checkbox18.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.med2)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.warning)
                        .show();
                return true;
            }
        });
        checkbox18.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.dec.jpeg.memcap/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.enc.hprof.vid.bps/d' /system/build.prop",
                                                                          "echo \"ro.media.dec.jpeg.memcap=8000000\" >> /system/build.prop",
                                                                          "echo \"ro.media.enc.hprof.vid.bps=8000000\" >> /system/build.prop",
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
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.dec.jpeg.memcap/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.enc.hprof.vid.bps/d' /system/build.prop",
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

        checkbox19 = (CheckBox) view.findViewById(R.id.checkBox19);
        if (text.toString().contains("ro.ril.enable.amr.wideband=1")) {
            checkbox19.setChecked(true);
        } else {
            checkbox19.setChecked(false);
        }
        checkbox19.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.med3)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.warning)
                        .show();
                return true;
            }
        });
        checkbox19.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.enable.amr.wideband/d' /system/build.prop",
                                                                          "echo \"ro.ril.enable.amr.wideband=1\" >> /system/build.prop",
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
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.ril.enable.amr.wideband/d' /system/build.prop",
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

        ch21 = (CheckBox) view.findViewById(R.id.ch21);
        if (text.toString().contains("media.stagefright.enable-player=true")
                && text.toString().contains("media.stagefright.enable-meta=true")
                && text.toString().contains("media.stagefright.enable-scan=true")
                && text.toString().contains("media.stagefright.enable-aac=true")
                && text.toString().contains("media.stagefright.enable-qcp=true")
                && text.toString().contains("media.stagefright.enable-http=true")
                && text.toString().contains("media.stagefright.enable-rtsp=true")
                && text.toString().contains("media.stagefright.enable-record=false")) {
            ch21.setChecked(true);
        } else {
            ch21.setChecked(false);
        }
        ch21.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.med4)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.warning)
                        .show();
                return true;
            }
        });
        ch21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-aac/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-qcp/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-player/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-meta/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-scan/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-http/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-rtsp/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-record/d' /system/build.prop",
                                                                    "echo \"media.stagefright.enable-player=true\" >> /system/build.prop",
                                                                    "echo \"media.stagefright.enable-meta=true\" >> /system/build.prop",
                                                                    "echo \"media.stagefright.enable-scan=true\" >> /system/build.prop",
                                                                    "echo \"media.stagefright.enable-aac=true\" >> /system/build.prop",
                                                                    "echo \"media.stagefright.enable-qcp=true\" >> /system/build.prop",
                                                                    "echo \"media.stagefright.enable-http=true\" >> /system/build.prop",
                                                                    "echo \"media.stagefright.enable-rtsp=true\" >> /system/build.prop",
                                                                    "echo \"media.stagefright.enable-record=false\" >> /system/build.prop",
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
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-aac/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-qcp/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-player/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-meta/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-scan/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-http/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-rtsp/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/media.stagefright.enable-record/d' /system/build.prop",
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

        ch22 = (CheckBox) view.findViewById(R.id.ch22);
        if (text.toString().contains("ro.media.capture.flash=led")
                && text.toString().contains("ro.media.capture.flashMinV=3300000")
                && text.toString().contains("ro.media.capture.torchIntensity=65")
                && text.toString().contains("ro.media.capture.flashIntensity=100")) {
            ch22.setChecked(true);
        } else {
            ch22.setChecked(false);
        }
        ch22.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.med5)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.warning)
                        .show();
                return true;
            }
        });
        ch22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.capture.flash/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.capture.flashMinV/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.capture.torchIntensity/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.capture.flashIntensity/d' /system/build.prop",
                                                                    "echo \"ro.media.capture.flash=led\" >> /system/build.prop",
                                                                    "echo \"ro.media.capture.flashMinV=3300000\" >> /system/build.prop",
                                                                    "echo \"ro.media.capture.torchIntensity=65\" >> /system/build.prop",
                                                                    "echo \"ro.media.capture.flashIntensity=100\" >> /system/build.prop",
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
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.capture.flash/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.capture.flashMinV/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.capture.torchIntensity/d' /system/build.prop",
                                                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.media.capture.flashIntensity/d' /system/build.prop",
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


        return view;
    }


}