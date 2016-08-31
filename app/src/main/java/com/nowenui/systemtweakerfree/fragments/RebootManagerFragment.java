package com.nowenui.systemtweakerfree.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nowenui.systemtweakerfree.R;
import com.stericson.RootTools.RootTools;

public class RebootManagerFragment extends Fragment {

    private boolean isClicked;

    public static RebootManagerFragment newInstance(Bundle bundle) {
        RebootManagerFragment messagesFragment = new RebootManagerFragment();

        if (bundle != null) {
            messagesFragment.setArguments(bundle);
        }

        return messagesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        RootTools.debugMode = false;

        View view = inflater.inflate(R.layout.fragment_rebootmanager, parent, false);

        Button reboot_1 = (Button) view.findViewById(R.id.reboot_1);
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
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.reboot)
                        .setMessage(R.string.reboot1dialog)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot"});
                                    proc.waitFor();
                                } catch (Exception ex) {
                                    Toast.makeText(getActivity(), "ROOT NEEDED! | ROOT НЕОБХОДИМ!", Toast.LENGTH_SHORT).show();
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
        });

        Button shutdown = (Button) view.findViewById(R.id.shutdown);
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
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.shutdown)
                        .setMessage(R.string.shutdownconf)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
                                    proc.waitFor();
                                } catch (Exception ex) {
                                    Toast.makeText(getActivity(), "ROOT NEEDED! | ROOT НЕОБХОДИМ!", Toast.LENGTH_SHORT).show();
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
        });

        Button hotreboot = (Button) view.findViewById(R.id.reboot_hot);
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
                if (RootTools.isBusyboxAvailable()) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.hotreboot)
                            .setMessage(R.string.hotrebootconf)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "busybox killall system_server"});
                                        proc.waitFor();
                                    } catch (Exception ex) {
                                        Toast.makeText(getActivity(), "ROOT NEEDED! | ROOT НЕОБХОДИМ!", Toast.LENGTH_SHORT).show();
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
                } else {
                    Toast.makeText(getActivity(), R.string.errobusybox, Toast.LENGTH_SHORT).show();
                    RootTools.offerBusyBox(getActivity());
                }
            }
        });

        Button rebootrecovery = (Button) view.findViewById(R.id.rebootrecovery);
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
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.rebootrec)
                        .setMessage(R.string.rebootrecovconf)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot recovery"});
                                    proc.waitFor();
                                } catch (Exception ex) {
                                    Toast.makeText(getActivity(), "ROOT NEEDED! | ROOT НЕОБХОДИМ!", Toast.LENGTH_SHORT).show();
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
        });

        Button rebootboodloader = (Button) view.findViewById(R.id.rebootbootloader);
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
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.rebootbottloader)
                        .setMessage(R.string.rebootbootloaderdialog)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot bootloader"});
                                    proc.waitFor();
                                } catch (Exception ex) {
                                    Toast.makeText(getActivity(), "ROOT NEEDED! | ROOT НЕОБХОДИМ!", Toast.LENGTH_SHORT).show();
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
        });


        return view;
    }

}