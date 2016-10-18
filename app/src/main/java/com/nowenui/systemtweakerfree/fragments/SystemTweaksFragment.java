package com.nowenui.systemtweakerfree.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import android.widget.TextView;
import android.widget.Toast;

import com.nowenui.systemtweakerfree.R;
import com.onebit.spinner2.Spinner2;
import com.stericson.rootshell.exceptions.RootDeniedException;
import com.stericson.rootshell.execution.Command;
import com.stericson.roottools.RootTools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemTweaksFragment extends Fragment {

    public Integer heap, grow;
    private CheckBox checkbox21, checkbox22, checkbox23, checkbox30, ext4tweak, display_cal, artfix;
    private String k;
    private boolean suc1, suc2, suc3;

    public static SystemTweaksFragment newInstance(Bundle bundle) {
        SystemTweaksFragment messagesFragment = new SystemTweaksFragment();

        if (bundle != null) {
            messagesFragment.setArguments(bundle);
        }

        return messagesFragment;
    }

    public static String getFileSystem(File path) {
        try {
            Process mount = Runtime.getRuntime().exec("mount");
            BufferedReader reader = new BufferedReader(new InputStreamReader(mount.getInputStream()));
            mount.waitFor();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\\s+");
                for (int i = 0; i < split.length - 1; i++) {
                    if (!split[i].equals("/") && path.getAbsolutePath().startsWith(split[i]))
                        return split[i + 1];
                }
            }
            reader.close();
            mount.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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

        artfix = (CheckBox) view.findViewById(R.id.artfix);
        if (text.toString().contains("dalvik.vm.dex2oat-filter=speed") &&
                text.toString().contains("dalvik.vm.image-dex2oat-filter=speed")) {
            artfix.setChecked(true);
        } else {
            artfix.setChecked(false);
        }
        if ((Build.VERSION.SDK_INT >= 21)) {
            artfix.setEnabled(true);
        } else {
            artfix.setEnabled(false);
        }
        artfix.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.art)
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
        artfix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                    "cp /data/data/com.nowenui.systemtweakerfree/files/art_fix /system/etc/",
                                    "chmod 777 /system/etc/art_fix",
                                    "/system/etc/art_fix",
                                    "rm -f /system/etc/art_fix",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                            try {
                                RootTools.getShell(true).add(command1);
                                Toast.makeText(getActivity(), R.string.tweakenabled, Toast.LENGTH_SHORT).show();
                                new AlertDialog.Builder(getContext())
                                        .setTitle(R.string.reboot)
                                        .setMessage(R.string.rebootdalvik)
                                        .setCancelable(false)
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
                                        .setIcon(R.drawable.warning)
                                        .show();
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
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /data",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /data",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /data",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.dex2oat-filter=speed/d' /system/build.prop",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.image-dex2oat-filter=speed/d' /system/build.prop",
                                    "rm -rf /data/dalvik-cache",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /data",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /data",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /data"
                            );
                            try {
                                RootTools.getShell(true).add(command1);
                                Toast.makeText(getActivity(), R.string.tweakdisabled, Toast.LENGTH_SHORT).show();
                                new AlertDialog.Builder(getContext())
                                        .setTitle(R.string.reboot)
                                        .setMessage(R.string.rebootdalvikdis)
                                        .setCancelable(false)
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
                                        .setIcon(R.drawable.warning)
                                        .show();
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

        CheckBox heapopt = (CheckBox) view.findViewById(R.id.heaptweak);
        if (getTotalRAM() <= 256) {
            heap = 64;
            grow = 24;
        }
        if ((getTotalRAM() <= 512) && (getTotalRAM() > 256)) {
            heap = 128;
            grow = 48;
        }
        if ((getTotalRAM() <= 1024) && (getTotalRAM() > 512)) {
            heap = 256;
            grow = 96;
        }
        if ((getTotalRAM() <= 2048) && (getTotalRAM() > 1024)) {
            heap = 512;
            grow = 256;
        }
        if ((getTotalRAM() <= 3072) && (getTotalRAM() > 2048)) {
            heap = 1024;
            grow = 512;
        }
        if ((getTotalRAM() <= 4096) && (getTotalRAM() > 3072)) {
            heap = 2048;
            grow = 1024;
        }
        if (text.toString().contains("dalvik.vm.heapsize=" + heap + "m") &&
                text.toString().contains("dalvik.vm.heaptargetutilization=0.75")
                && text.toString().contains("dalvik.vm.heapgrowthlimit=" + grow + "m") &&
                text.toString().contains("dalvik.vm.check-dex-sum=false") &&
                text.toString().contains("dalvik.vm.checkjni=false") &&
                text.toString().contains("dalvik.vm.execution-mode=jit")) {
            heapopt.setChecked(true);
        } else {
            heapopt.setChecked(false);
        }
        heapopt.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.heaptizer)
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
        heapopt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.heapsize/d' /system/build.prop",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.heaptargetutilization/d' /system/build.prop",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.heapgrowthlimit/d' /system/build.prop",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.check-dex-sum/d' /system/build.prop",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.checkjni=false/d' /system/build.prop",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.execution-mode=jit/d' /system/build.prop",
                                                                       "echo \"dalvik.vm.heapsize=" + heap + "m\" >> /system/build.prop",
                                                                       "echo \"dalvik.vm.heaptargetutilization=0.75\" >> /system/build.prop",
                                                                       "echo \"dalvik.vm.heapgrowthlimit=" + grow + "m\" >> /system/build.prop",
                                                                       "echo \"dalvik.vm.check-dex-sum=false\" >> /system/build.prop",
                                                                       "echo \"dalvik.vm.checkjni=false\" >> /system/build.prop",
                                                                       "echo \"dalvik.vm.execution-mode=jit\" >> /system/build.prop",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
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
                                                       if (RootTools.isRootAvailable()) {
                                                           if (RootTools.isAccessGiven()) {
                                                               Command command1 = new Command(0,
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.heapsize/d' /system/build.prop",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.heaptargetutilization/d' /system/build.prop",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.heapgrowthlimit/d' /system/build.prop",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.check-dex-sum/d' /system/build.prop",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.checkjni=false/d' /system/build.prop",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dalvik.vm.execution-mode=jit/d' /system/build.prop",
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


        display_cal = (CheckBox) view.findViewById(R.id.display_cal);
        String check10 = "/etc/init.d/display";
        String check10a = "/system/etc/init.d/display";
        if (new File(Environment.getRootDirectory() + check10).exists() || new File(check10a).exists() || new File(Environment.getRootDirectory() + check10a).exists()) {
            display_cal.setChecked(true);
        } else {
            display_cal.setChecked(false);
        }
        display_cal.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.sys2)
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
        display_cal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                           "cp /data/data/com.nowenui.systemtweakerfree/files/display /system/etc/init.d/",
                                                                           "chmod 777 /system/etc/init.d/display",
                                                                           "cp /data/data/com.nowenui.systemtweakerfree/files/ad_calib.cfg /system/etc/",
                                                                           "chmod 755 /system/etc/ad_calib.cfg",
                                                                           "/system/etc/init.d/display",
                                                                           "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                           "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                           "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
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
                                                           if (RootTools.isRootAvailable()) {
                                                               if (RootTools.isAccessGiven()) {
                                                                   Command command1 = new Command(0,
                                                                           "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                           "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                           "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                           "rm -f /system/etc/init.d/display",
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


        checkbox21 = (CheckBox) view.findViewById(R.id.checkBox21);
        String ch21 = "/system/etc/init.d/81GPU_rendering";
        if (text.toString().contains("debug.composition.type=gpu")
                && text.toString().contains("debug.sf.hw=1")
                && text.toString().contains("video.accelerate.hw=1")
                && text.toString().contains("debug.performance.tuning=1")
                && text.toString().contains("debug.egl.profiler=1")
                && text.toString().contains("debug.egl.hw=1")
                && text.toString().contains("debug.enabletr=true")
                && text.toString().contains("debug.overlayui.enable=1")
                && text.toString().contains("debug.qctwa.preservebuf=1")
                && text.toString().contains("dev.pm.dyn_samplingrate=1")
                && text.toString().contains("ro.fb.mode=1")
                && text.toString().contains("ro.sf.compbypass.enable=0")
                && new File(ch21).exists()) {
            checkbox21.setChecked(true);
        } else {
            checkbox21.setChecked(false);
        }
        checkbox21.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.sys4)
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
        checkbox21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                          "cp /data/data/com.nowenui.systemtweakerfree/files/81GPU_rendering /system/etc/init.d/",
                                                                          "chmod 777 /system/etc/init.d/81GPU_rendering",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.sf.hw/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.performance.tuning/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/video.accelerate.hw/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.egl.profiler/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.egl.hw/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.composition.type/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.enabletr/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.overlayui.enable/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.qctwa.preservebuf/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dev.pm.dyn_samplingrate/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.fb.mode/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.sf.compbypass.enable/d' /system/build.prop",
                                                                          "echo \"debug.sf.hw=1\" >> /system/build.prop",
                                                                          "echo \"debug.performance.tuning=1\" >> /system/build.prop",
                                                                          "echo \"video.accelerate.hw=1\" >> /system/build.prop",
                                                                          "echo \"debug.egl.profiler=1\" >> /system/build.prop",
                                                                          "echo \"debug.egl.hw=1\" >> /system/build.prop",
                                                                          "echo \"debug.composition.type=gpu\" >> /system/build.prop",
                                                                          "echo \"debug.enabletr=true\" >> /system/build.prop",
                                                                          "echo \"debug.overlayui.enable=1\" >> /system/build.prop",
                                                                          "echo \"debug.qctwa.preservebuf=1\" >> /system/build.prop",
                                                                          "echo \"dev.pm.dyn_samplingrate=1\" >> /system/build.prop",
                                                                          "echo \"ro.fb.mode=1\" >> /system/build.prop",
                                                                          "echo \"ro.sf.compbypass.enable=0\" >> /system/build.prop",
                                                                          "/system/etc/init.d/81GPU_rendering",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
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
                                                          if (RootTools.isRootAvailable()) {
                                                              if (RootTools.isAccessGiven()) {
                                                                  Command command1 = new Command(0,
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                          "rm -f /system/etc/init.d/81GPU_rendering",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.sf.hw/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.performance.tuning/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/video.accelerate.hw/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.egl.profiler/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.egl.hw/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.composition.type/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.enabletr/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.overlayui.enable/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/debug.qctwa.preservebuf/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/dev.pm.dyn_samplingrate/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.fb.mode/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.sf.compbypass.enable/d' /system/build.prop",
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

        checkbox22 = (CheckBox) view.findViewById(R.id.checkBox22);
        if (text.toString().contains("windowsmgr.max_events_per_sec=150") &&
                text.toString().contains("ro.min_pointer_dur=8")
                && text.toString().contains("ro.max.fling_velocity=12000") &&
                text.toString().contains("ro.min.fling_velocity=8000")) {
            checkbox22.setChecked(true);
        } else {
            checkbox22.setChecked(false);
        }
        checkbox22.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.sys5)
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
        checkbox22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/windowsmgr.max_events_per_sec/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.min_pointer_dur/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.max.fling_velocity/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.min.fling_velocity/d' /system/build.prop",
                                                                          "echo \"ro.min_pointer_dur=8\" >> /system/build.prop",
                                                                          "echo \"ro.max.fling_velocity=12000\" >> /system/build.prop",
                                                                          "echo \"ro.min.fling_velocity=8000\" >> /system/build.prop",
                                                                          "echo \"windowsmgr.max_events_per_sec=150\" >> /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
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
                                                          if (RootTools.isRootAvailable()) {
                                                              if (RootTools.isAccessGiven()) {
                                                                  Command command1 = new Command(0,
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/windowsmgr.max_events_per_sec/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.min_pointer_dur/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.max.fling_velocity/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.min.fling_velocity/d' /system/build.prop",
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


        CheckBox touchtweak = (CheckBox) view.findViewById(R.id.touchtweak);
        String check44 = "/etc/init.d/touch";
        String check44a = "/system/etc/init.d/touch";
        if (new File(Environment.getRootDirectory() + check44).exists() || new File(check44a).exists() || new File(Environment.getRootDirectory() + check44a).exists()) {
            touchtweak.setChecked(true);
        } else {
            touchtweak.setChecked(false);
        }
        touchtweak.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.tw)
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
        touchtweak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                          "cp /data/data/com.nowenui.systemtweakerfree/files/touch /system/etc/init.d/",
                                                                          "chmod 777 /system/etc/init.d/touch",
                                                                          "/system/etc/init.d/touch",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
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
                                                          if (RootTools.isRootAvailable()) {
                                                              if (RootTools.isAccessGiven()) {
                                                                  Command command1 = new Command(0,
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                          "rm -f /system/etc/init.d/touch",
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

        CheckBox perfomance = (CheckBox) view.findViewById(R.id.perfomance);
        String check64 = "/etc/init.d/boost";
        String check64a = "/system/etc/init.d/boost";
        if (text.toString().contains("persist.service.lgospd.enable=0")
                && text.toString().contains("persist.service.pcsync.enable=0") &&
                text.toString().contains("touch.pressure.scale=0.001") &&
                text.toString().contains("persist.sys.use_dithering=0")
                && text.toString().contains("persist.sys.use_16bpp_alpha=1")
                && text.toString().contains("ro.vold.umsdirtyratio=1") &&
                new File(Environment.getRootDirectory() + check64).exists() || new File(check64a).exists() || new File(Environment.getRootDirectory() + check64a).exists()) {
            perfomance.setChecked(true);
        } else {
            perfomance.setChecked(false);
        }
        perfomance.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.perf)
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
        perfomance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.sys.use_dithering/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.sys.use_16bpp_alpha/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/touch.pressure.scale=0.001/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.service.pcsync.enable/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.service.lgospd.enable/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.vold.umsdirtyratio/d' /system/build.prop",
                                                                          "echo \"ro.vold.umsdirtyratio=1\" >> /system/build.prop",
                                                                          "echo \"persist.sys.use_dithering=0\" >> /system/build.prop",
                                                                          "echo \"persist.sys.use_16bpp_alpha=1\" >> /system/build.prop",
                                                                          "echo \"persist.service.lgospd.enable=0\" >> /system/build.prop",
                                                                          "echo \"persist.service.pcsync.enable=0\" >> /system/build.prop",
                                                                          "echo \"touch.pressure.scale=0.001\" >> /system/build.prop",
                                                                          "setprop persist.service.lgospd.enable 0",
                                                                          "setprop persist.service.pcsync.enable 0",
                                                                          "cp /data/data/com.nowenui.systemtweakerfree/files/boost /system/etc/init.d/",
                                                                          "chmod 777 /system/etc/init.d/boost",
                                                                          "/system/etc/init.d/boost",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
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
                                                          if (RootTools.isRootAvailable()) {
                                                              if (RootTools.isAccessGiven()) {
                                                                  Command command1 = new Command(0,
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.sys.use_dithering/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.sys.use_16bpp_alpha/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/touch.pressure.scale=0.001/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.service.pcsync.enable/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.service.lgospd.enable/d' /system/build.prop",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.vold.umsdirtyratio/d' /system/build.prop",
                                                                          "rm -f /system/etc/init.d/boost",
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

        CheckBox ramflag = (CheckBox) view.findViewById(R.id.lowram);
        if ((getTotalRAM() <= 1024) && (Build.VERSION.SDK_INT >= 19)) {
            ramflag.setEnabled(true);
        } else {
            ramflag.setEnabled(false);
        }
        if (text.toString().contains("ro.config.low_ram=true")) {
            ramflag.setChecked(true);
        } else {
            ramflag.setChecked(false);
        }
        ramflag.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.sys12)
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
        ramflag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.config.low_ram/d' /system/build.prop",
                                                                       "echo \"ro.config.low_ram=true\" >> /system/build.prop",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
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
                                                       if (RootTools.isRootAvailable()) {
                                                           if (RootTools.isAccessGiven()) {
                                                               Command command1 = new Command(0,
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                       "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/ro.config.low_ram/d' /system/build.prop",
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

        CheckBox ramflageffect = (CheckBox) view.findViewById(R.id.lowrameffects);
        if ((getTotalRAM() <= 1024) && (Build.VERSION.SDK_INT >= 19) && ramflag.isChecked()) {
            ramflageffect.setEnabled(true);
        } else {
            ramflageffect.setEnabled(false);
        }
        if (text.toString().contains("persist.sys.force_highendgfx=true")) {
            ramflageffect.setChecked(true);
        } else {
            ramflageffect.setChecked(false);
        }
        ramflageffect.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.sys13)
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
        ramflageffect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                             "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.sys.force_highendgfx/d' /system/build.prop",
                                                                             "echo \"persist.sys.force_highendgfx=true\" >> /system/build.prop",
                                                                             "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                             "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                             "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
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
                                                             if (RootTools.isRootAvailable()) {
                                                                 if (RootTools.isAccessGiven()) {
                                                                     Command command1 = new Command(0,
                                                                             "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                             "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                             "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                             "/data/data/com.nowenui.systemtweakerfree/files/busybox sed -i '/persist.sys.force_highendgfx/d' /system/build.prop",
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


        checkbox23 = (CheckBox) view.findViewById(R.id.checkBox23);
        String check11 = "/etc/init.d/93zipalign";
        String check11a = "/system/etc/init.d/93zipalign";
        if (new File(Environment.getRootDirectory() + check11).exists() || new File(check11a).exists() || new File(Environment.getRootDirectory() + check11a).exists()) {
            checkbox23.setChecked(true);
        } else {
            checkbox23.setChecked(false);
        }
        checkbox23.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.sys6)
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
        checkbox23.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                          "cp /data/data/com.nowenui.systemtweakerfree/files/zipalign /system/xbin/zipalign",
                                                                          "chmod 755 /system/xbin/zipalign",
                                                                          "cp /data/data/com.nowenui.systemtweakerfree/files/93zipalign /system/etc/init.d/93zipalign",
                                                                          "chmod 777 /system/etc/init.d/93zipalign",
                                                                          "/system/etc/init.d/93zipalign",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
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
                                                          if (RootTools.isRootAvailable()) {
                                                              if (RootTools.isAccessGiven()) {
                                                                  Command command1 = new Command(0,
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                          "rm -f /system/xbin/zipalign",
                                                                          "rm -f /system/etc/init.d/93zipalign",
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

        checkbox30 = (CheckBox) view.findViewById(R.id.checkBox30);
        String check12 = "/etc/init.d/11sqlite";
        String check12a = "/system/etc/init.d/11sqlite";
        if (new File(Environment.getRootDirectory() + check12).exists() || new File(check12a).exists() || new File(Environment.getRootDirectory() + check12a).exists()) {
            checkbox30.setChecked(true);
        } else {
            checkbox30.setChecked(false);
        }
        checkbox30.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.sys7)
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
        checkbox30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                          "cp /data/data/com.nowenui.systemtweakerfree/files/sqlite3 /system/xbin/sqlite3",
                                                                          "chmod 755 /system/xbin/sqlite3",
                                                                          "cp /data/data/com.nowenui.systemtweakerfree/files/11sqlite /system/etc/init.d/11sqlite",
                                                                          "chmod 777 /system/etc/init.d/11sqlite",
                                                                          "/system/etc/init.d/11sqlite",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
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
                                                          if (RootTools.isRootAvailable()) {
                                                              if (RootTools.isAccessGiven()) {
                                                                  Command command1 = new Command(0,
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                          "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                          "rm -f /system/xbin/sqlite3",
                                                                          "rm -f /system/etc/init.d/11sqlite",
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
        ext4tweak = (CheckBox) view.findViewById(R.id.ext4tweak);
        String check13 = "/etc/init.d/ext4";
        String check13a = "/system/etc/init.d/ext4";
        if (new File(Environment.getRootDirectory() + check13).exists() || new File(check13a).exists() || new File(Environment.getRootDirectory() + check13a).exists()) {
            ext4tweak.setChecked(true);
        } else {
            ext4tweak.setChecked(false);
        }
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Process mount = Runtime.getRuntime().exec("mount");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mount.getInputStream()));
                    mount.waitFor();

                    String extPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] split = line.split("\\s+");
                        for (int i = 0; i < split.length - 1; i++) {
                            if (split[i].contentEquals(extPath) ||
                                    split[i].contains("system") ||
                                    split[i].contains("_system")) {
                                String strMount = split[i];
                                String strFileSystem = split[i + 1];
                                k = strFileSystem;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (getFileSystem(Environment.getDataDirectory()).contains("ext4")) {
                    suc1 = true;
                }
                if (getFileSystem(Environment.getDownloadCacheDirectory()).contains("ext4")) {
                    suc2 = true;
                }
                if (k.contains("ext4")) {
                    suc3 = true;
                }

                if (suc1 && suc2 && suc3) {
                    ext4tweak.setEnabled(true);
                } else {
                    ext4tweak.setEnabled(false);
                }
            }
        }, 900);
        ext4tweak.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.sys8)
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
        ext4tweak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


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
                                                                         "cp /data/data/com.nowenui.systemtweakerfree/files/ext4 /system/etc/init.d/",
                                                                         "chmod 777 /system/etc/init.d/ext4",
                                                                         "cp /data/data/com.nowenui.systemtweakerfree/files/tune2fs /system/xbin/",
                                                                         "chmod 777 /system/xbin/tune2fs",
                                                                         "/system/etc/init.d/ext4",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
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
                                                         if (RootTools.isRootAvailable()) {
                                                             if (RootTools.isAccessGiven()) {
                                                                 Command command1 = new Command(0,
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                         "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system",
                                                                         "rm -f /system/etc/init.d/ext4",
                                                                         "rm -f /system/xbin/tune2fs",
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

        TextView textView55 = (TextView) view.findViewById(R.id.textView55);
        textView55.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.tweakabout)
                        .setMessage(R.string.sys9)
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


        final Spinner2 spinner6 = (Spinner2) view.findViewById(R.id.spinner6);

        ArrayAdapter adapter3 =
                ArrayAdapter.createFromResource(getActivity(), R.array.ramlist, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner6.setAdapter(adapter3, false);

        String fucks = "/etc/init.d/ram_gaming";
        String fucksa = "/system/etc/init.d/ram_gaming";
        if (new File(Environment.getRootDirectory() + fucks).exists() || new File(fucksa).exists() || new File(Environment.getRootDirectory() + fucksa).exists()) {
            boolean isLangRU = Locale.getDefault().getLanguage().equals("ru");
            boolean isLangBE = Locale.getDefault().getLanguage().equals("be");
            boolean isLangUK = Locale.getDefault().getLanguage().equals("uk");
            if (isLangRU || isLangBE || isLangUK) {
                final int spinnerPosition2 = adapter3.getPosition("Игровой");
                spinner6.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner6.setSelection(false, spinnerPosition2);
                    }
                });
            } else {
                final int spinnerPosition1 = adapter3.getPosition("Gaming");
                spinner6.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner6.setSelection(false, spinnerPosition1);
                    }
                });
            }


        }
        String fucks10 = "/etc/init.d/ram_balanced";
        String fucks10a = "/system/etc/init.d/ram_balanced";
        if (new File(Environment.getRootDirectory() + fucks10).exists() || new File(fucks10a).exists() || new File(Environment.getRootDirectory() + fucks10a).exists()) {
            boolean isLangRU = Locale.getDefault().getLanguage().equals("ru");
            boolean isLangBE = Locale.getDefault().getLanguage().equals("be");
            boolean isLangUK = Locale.getDefault().getLanguage().equals("uk");
            if (isLangRU || isLangBE || isLangUK) {
                final int spinnerPosition4 = adapter3.getPosition("Сбалансированный");
                spinner6.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner6.setSelection(false, spinnerPosition4);
                    }
                });
            } else {
                final int spinnerPosition3 = adapter3.getPosition("Balanced");
                spinner6.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner6.setSelection(false, spinnerPosition3);
                    }
                });
            }
        }

        String fucks2 = "/etc/init.d/ram_multitasking";
        String fucks2a = "/system/etc/init.d/ram_multitasking";
        boolean isLangRU = Locale.getDefault().getLanguage().equals("ru");
        boolean isLangBE = Locale.getDefault().getLanguage().equals("be");
        boolean isLangUK = Locale.getDefault().getLanguage().equals("uk");
        if (new File(Environment.getRootDirectory() + fucks2).exists() || new File(fucks2a).exists() || new File(Environment.getRootDirectory() + fucks2a).exists()) {
            if (isLangRU || isLangBE || isLangUK) {
                final int spinnerPosition2 = adapter3.getPosition("Многозадачность");
                spinner6.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner6.setSelection(false, spinnerPosition2);
                    }
                });
            } else {
                final int spinnerPosition1 = adapter3.getPosition("Multitasking");
                spinner6.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner6.setSelection(false, spinnerPosition1);
                    }
                });
            }

        }

        spinner6.post(new Runnable() {
            @Override
            public void run() {
                spinner6.setOnItemSelectedSpinner2Listener(new Spinner2.OnItemSelectedSpinner2Listener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String[] choose = getResources().getStringArray(R.array.ramlist);

                        if (choose[position].contains("(стандарт)") || choose[position].contains("(standart)")) {
                            try {
                                Process su = Runtime.getRuntime().exec("su");
                                DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_gaming\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_balanced\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_multitasking\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system\n");
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
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("cp /data/data/com.nowenui.systemtweakerfree/files/ram_gaming /system/etc/init.d/\n");
                                outputStream.flush();
                                outputStream.writeBytes("chmod 777 /system/etc/init.d/ram_gaming\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_balanced\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_multitasking\n");
                                outputStream.flush();
                                outputStream.writeBytes("/system/etc/init.d/ram_gaming");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system\n");
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
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("cp /data/data/com.nowenui.systemtweakerfree/files/ram_balanced /system/etc/init.d/\n");
                                outputStream.flush();
                                outputStream.writeBytes("chmod 777 /system/etc/init.d/ram_balanced\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_gaming\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_multitasking\n");
                                outputStream.flush();
                                outputStream.writeBytes("/system/etc/init.d/ram_balanced");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system\n");
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
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("cp /data/data/com.nowenui.systemtweakerfree/files/ram_multitasking /system/etc/init.d/\n");
                                outputStream.flush();
                                outputStream.writeBytes("chmod 777 /system/etc/init.d/ram_multitasking\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_gaming\n");
                                outputStream.flush();
                                outputStream.writeBytes("rm -f /system/etc/init.d/ram_balanced\n");
                                outputStream.flush();
                                outputStream.writeBytes("/system/etc/init.d/ram_multitasking");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system\n");
                                outputStream.flush();
                                outputStream.writeBytes("exit\n");
                                outputStream.flush();
                                Toast.makeText(getActivity(), R.string.ok, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(getActivity(), R.string.errordev, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });


        return view;
    }

    public Double getTotalRAM() {

        RandomAccessFile reader = null;
        String load = null;
        double mb = 0;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        double totRam = 0;
        String lastValue = "";
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();

            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
            }
            reader.close();

            totRam = Double.parseDouble(value);

            mb = totRam / 1024.0;


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

        }

        return mb;
    }

}