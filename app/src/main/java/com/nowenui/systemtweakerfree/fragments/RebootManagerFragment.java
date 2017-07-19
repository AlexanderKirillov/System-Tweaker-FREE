package com.nowenui.systemtweakerfree.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mrengineer13.snackbar.SnackBar;
import com.nowenui.systemtweakerfree.R;
import com.nowenui.systemtweakerfree.Utility;


public class RebootManagerFragment extends Fragment {

    private boolean isClicked;

    public static RebootManagerFragment newInstance(Bundle bundle) {
        RebootManagerFragment RebootManager = new RebootManagerFragment();

        if (bundle != null) {
            RebootManager.setArguments(bundle);
        }

        return RebootManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rebootmanager, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ////////////////////////////////////////
        ////// Full reboot button /////////////
        ///////////////////////////////////////
        Button reboot_1 = view.findViewById(R.id.reboot_1);
        reboot_1.setBackgroundResource(R.drawable.roundbuttoncal);
        reboot_1.setTextSize(18);
        reboot_1.setTextColor(Color.WHITE);
        reboot_1.setOnClickListener(new View.OnClickListener() {
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
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new android.app.AlertDialog.Builder(getContext())
                            .setTitle(R.string.reboot)
                            .setMessage(R.string.reboot1dialog)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
                            .setMessage(R.string.reboot1dialog)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
                            .setMessage(R.string.reboot1dialog)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
        });

        ////////////////////////////////////////
        ////// Shutdown button ////////////////
        ///////////////////////////////////////
        Button shutdown = view.findViewById(R.id.shutdown);
        shutdown.setBackgroundResource(R.drawable.roundbuttoncal);
        shutdown.setTextSize(18);
        shutdown.setTextColor(Color.WHITE);
        shutdown.setOnClickListener(new View.OnClickListener() {
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
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new android.app.AlertDialog.Builder(getContext())
                            .setTitle(R.string.shutdown)
                            .setMessage(R.string.shutdownconf)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
                            .setTitle(R.string.shutdown)
                            .setMessage(R.string.shutdownconf)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
                            .setTitle(R.string.shutdown)
                            .setMessage(R.string.shutdownconf)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
        });

        ////////////////////////////////////////
        ////// Hot Reboot button //////////////
        ///////////////////////////////////////
        Button hotreboot = view.findViewById(R.id.reboot_hot);
        hotreboot.setBackgroundResource(R.drawable.roundbuttoncal);
        hotreboot.setTextSize(18);
        hotreboot.setTextColor(Color.WHITE);
        hotreboot.setOnClickListener(new View.OnClickListener() {
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
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new android.app.AlertDialog.Builder(getContext())
                            .setTitle(R.string.hotreboot)
                            .setMessage(R.string.hotrebootconf)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "/data/data/com.nowenui.systemtweakerfree/files/busybox killall system_server"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
                            .setTitle(R.string.hotreboot)
                            .setMessage(R.string.hotrebootconf)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "/data/data/com.nowenui.systemtweakerfree/files/busybox killall system_server"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
                            .setTitle(R.string.hotreboot)
                            .setMessage(R.string.hotrebootconf)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "/data/data/com.nowenui.systemtweakerfree/files/busybox killall system_server"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
        });

        ///////////////////////////////////////////////
        ////// Reboot into recovery button ////////////
        //////////////////////////////////////////////
        Button rebootrecovery = view.findViewById(R.id.rebootrecovery);
        rebootrecovery.setBackgroundResource(R.drawable.roundbuttoncal);
        rebootrecovery.setTextSize(18);
        rebootrecovery.setTextColor(Color.WHITE);
        rebootrecovery.setOnClickListener(new View.OnClickListener() {
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
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new android.app.AlertDialog.Builder(getContext())
                            .setTitle(R.string.rebootrec)
                            .setMessage(R.string.rebootrecovconf)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot recovery"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
                            .setTitle(R.string.rebootrec)
                            .setMessage(R.string.rebootrecovconf)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot recovery"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
                            .setTitle(R.string.rebootrec)
                            .setMessage(R.string.rebootrecovconf)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot recovery"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
        });

        /////////////////////////////////////////////////
        ////// Reboot into bootloader button ////////////
        ////////////////////////////////////////////////
        Button rebootboodloader = view.findViewById(R.id.rebootbootloader);
        rebootboodloader.setBackgroundResource(R.drawable.roundbuttoncal);
        rebootboodloader.setTextSize(18);
        rebootboodloader.setTextColor(Color.WHITE);
        rebootboodloader.setOnClickListener(new View.OnClickListener() {
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
                if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
                    new android.app.AlertDialog.Builder(getContext())
                            .setTitle(R.string.rebootbottloader)
                            .setMessage(R.string.rebootbootloaderdialog)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot bootloader"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
                            .setTitle(R.string.rebootbottloader)
                            .setMessage(R.string.rebootbootloaderdialog)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot bootloader"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
                            .setTitle(R.string.rebootbottloader)
                            .setMessage(R.string.rebootbootloaderdialog)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot bootloader"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        new SnackBar.Builder(getActivity()).withMessage("ROOT NEEDED! | ROOT НЕОБХОДИМ!").withBackgroundColorId(R.color.textview1bad).show();
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
        });
    }

}