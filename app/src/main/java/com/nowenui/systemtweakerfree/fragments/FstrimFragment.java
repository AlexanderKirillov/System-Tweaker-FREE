package com.nowenui.systemtweakerfree.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.github.mrengineer13.snackbar.SnackBar;
import com.nowenui.systemtweakerfree.R;
import com.nowenui.systemtweakerfree.Utility;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FstrimFragment extends Fragment {

    public String one, two, three, four, sysonly, dataonly, cacheonly;
    private boolean isClicked;

    public static FstrimFragment newInstance(Bundle bundle) {
        FstrimFragment Fstrim = new FstrimFragment();

        if (bundle != null) {
            Fstrim.setArguments(bundle);
        }

        return Fstrim;
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
        return inflater.inflate(R.layout.fragment_fstrim, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        Button fstrimbutton = view.findViewById(R.id.fstrimbutton);
        final CheckBox system = view.findViewById(R.id.system);
        final CheckBox data = view.findViewById(R.id.data);
        final CheckBox cache = view.findViewById(R.id.cache);

        ////////////////////////////////
        ////// FSTRIM option ///////////
        ////////////////////////////////
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

                ///////////////////////////////////////
                ////// System FSTRIM option ///////////
                ///////////////////////////////////////
                if (system.isChecked() & data.isChecked() == false & cache.isChecked() == false) {

                    if (RootTools.isAccessGiven()) {
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                        Command command2 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /system") {
                            @Override
                            public void commandOutput(int id, final String line) {
                                super.commandOutput(id, line);
                                sysonly += line + "\n";
                            }
                        };
                        try {
                            RootTools.getShell(true).add(command1);
                            RootTools.getShell(true).add(command2);
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                                final ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /system... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(sysonly.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        getContext());
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        sysonly = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /system... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(sysonly.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        sysonly = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /system... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(sysonly.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        sysonly = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }

                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    } else {
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                    }

                    /////////////////////////////////////
                    ////// Data FSTRIM option ///////////
                    /////////////////////////////////////
                } else if (data.isChecked() & system.isChecked() == false & cache.isChecked() == false) {

                    if (RootTools.isAccessGiven()) {
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /data", "mount -o rw,remount /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /data", "mount -o ro,remount /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /data");
                        Command command2 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /data") {
                            @Override
                            public void commandOutput(int id, final String line) {
                                super.commandOutput(id, line);
                                dataonly += line + "\n";

                            }
                        };
                        try {
                            RootTools.getShell(true).add(command1);
                            RootTools.getShell(true).add(command2);
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                                final ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /data... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(dataonly.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        getContext());
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        dataonly = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /data... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(dataonly.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        dataonly = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /data... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(dataonly.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        dataonly = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    } else {
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                    }

                    //////////////////////////////////////
                    ////// Cache FSTRIM option ///////////
                    //////////////////////////////////////
                } else if (cache.isChecked() & data.isChecked() == false & system.isChecked() == false) {

                    if (RootTools.isAccessGiven()) {
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /cache");
                        Command command2 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /cache") {
                            @Override
                            public void commandOutput(int id, final String line) {
                                super.commandOutput(id, line);
                                cacheonly += line + "\n";

                            }
                        };

                        try {
                            RootTools.getShell(true).add(command1);
                            RootTools.getShell(true).add(command2);
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                                final ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /cache... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(cacheonly.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        getContext());
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        cacheonly = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /cache... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(cacheonly.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        cacheonly = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /cache... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(cacheonly.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        cacheonly = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    } else {
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                    }

                    ///////////////////////////////////////////////
                    ////// System & Data  FSTRIM option ///////////
                    ///////////////////////////////////////////////
                } else if (system.isChecked() & data.isChecked() & cache.isChecked() == false) {

                    if (RootTools.isAccessGiven()) {
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /data", "mount -o rw,remount /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /data", "mount -o ro,remount /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount data");
                        Command command2 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /data") {
                            @Override
                            public void commandOutput(int id, final String line) {
                                super.commandOutput(id, line);
                                one += line + "\n";
                            }
                        };
                        try {
                            RootTools.getShell(true).add(command1);
                            RootTools.getShell(true).add(command2);
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                                final ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /data & /system... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(one.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        getContext());
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        one = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /data & /system... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(one.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        one = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /data & /system... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(one.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        one = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    } else {
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                    }

                    ///////////////////////////////////////////////
                    ////// System & Cache FSTRIM option ///////////
                    ///////////////////////////////////////////////
                } else if (system.isChecked() & cache.isChecked() & data.isChecked() == false) {

                    if (RootTools.isAccessGiven()) {
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /cache");
                        Command command2 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /system ",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /cache") {
                            @Override
                            public void commandOutput(int id, String line) {
                                super.commandOutput(id, line);
                                two += line + "\n";

                            }
                        };
                        try {
                            RootTools.getShell(true).add(command1);
                            RootTools.getShell(true).add(command2);
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                                final ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /system & /cache... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(two.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        getContext());
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        two = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /system & /cache... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(two.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        two = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /system & /cache... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(two.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        two = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    } else {
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                    }

                    /////////////////////////////////////////////
                    ////// Data & Cache FSTRIM option ///////////
                    /////////////////////////////////////////////
                } else if (data.isChecked() & cache.isChecked() & system.isChecked() == false) {

                    if (RootTools.isAccessGiven()) {
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /data", "mount -o rw,remount /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /data", "mount -o ro,remount /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /cache");
                        Command command2 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /cache") {
                            @Override
                            public void commandOutput(int id, String line) {
                                super.commandOutput(id, line);
                                three += line + "\n";
                            }
                        };
                        try {
                            RootTools.getShell(true).add(command1);
                            RootTools.getShell(true).add(command2);
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                                final ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /data & /cache... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(three.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        getContext());
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        three = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /data & /cache... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(three.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        three = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /data & /cache... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(three.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        three = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    } else {
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                    }

                    //////////////////////////////////////////////////////
                    ////// System & Data & Cache FSTRIM option ///////////
                    /////////////////////////////////////////////////////
                } else if (system.isChecked() & data.isChecked() & cache.isChecked()) {

                    if (RootTools.isAccessGiven()) {
                        Command command1 = new Command(0,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /data", "mount -o rw,remount /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /data", "mount -o ro,remount /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /cache",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /cache");
                        Command command2 = new Command(1,
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /data",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /system",
                                "/data/data/com.nowenui.systemtweakerfree/files/busybox fstrim -v /cache") {
                            @Override
                            public void commandOutput(int id, final String line) {
                                super.commandOutput(id, line);
                                four += line + "\n";
                            }
                        };
                        try {
                            RootTools.getShell(true).add(command1);
                            RootTools.getShell(true).add(command2);
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                                final ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /cache & /system & /data... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(four.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        getContext());
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        four = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /cache & /system & /data... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(four.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogDark));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        four = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                            if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                                final ProgressDialog dialog = new ProgressDialog(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.setTitle("FSTRIM");
                                dialog.setCancelable(false);
                                dialog.setMessage(getContext().getResources().getString(R.string.speedmessage));
                                dialog.setIndeterminate(false);
                                dialog.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        new SnackBar.Builder(getActivity()).withMessage("fstrim /cache & /system & /data... OK!").withBackgroundColorId(R.color.textview1good).show();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                StringBuilder sb = new StringBuilder(four.trim());
                                                sb.delete(0, 4);
                                                final AlertDialog.Builder db = new AlertDialog.Builder(
                                                        new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack));
                                                db.setMessage(sb);
                                                db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface d, int arg1) {

                                                        four = null;
                                                        d.cancel();
                                                    }

                                                });
                                                db.show();
                                            }
                                        }, 0);
                                    }
                                }, 4000);
                            }
                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.errordev)).withBackgroundColorId(R.color.textview1bad).show();
                        }
                    } else {
                        new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.error)).withBackgroundColorId(R.color.textview1bad).show();
                    }

                }
            }
        });

        /////////////////////////////////////////////////////
        ////// FSTRIM after booting device option ///////////
        ////////////////////////////////////////////////////
        CheckBox bootfstrim = view.findViewById(R.id.bootfstrim);
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


                                                          if (RootTools.isAccessGiven()) {
                                                              Command command1 = new Command(0,
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                                                                      "cp /data/data/com.nowenui.systemtweakerfree/files/70fstrim /system/etc/init.d/70fstrim",
                                                                      "chmod 777 /system/etc/init.d/70fstrim",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
                                                              try {
                                                                  RootTools.getShell(true).add(command1);
                                                                  new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.ok)).withBackgroundColorId(R.color.textview1good).show();
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
                                                                      "rm -f /system/etc/init.d/70fstrim",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                                                                      "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system"
                                                              );
                                                              try {
                                                                  RootTools.getShell(true).add(command1);
                                                                  new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.delete)).withBackgroundColorId(R.color.textview1good).show();
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

}