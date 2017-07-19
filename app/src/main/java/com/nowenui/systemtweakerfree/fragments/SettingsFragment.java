package com.nowenui.systemtweakerfree.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.franmontiel.localechanger.LocaleChanger;
import com.franmontiel.localechanger.utils.ActivityRecreationHelper;
import com.github.mrengineer13.snackbar.SnackBar;
import com.nowenui.systemtweakerfree.R;
import com.nowenui.systemtweakerfree.Utility;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    public static SettingsFragment newInstance(Bundle bundle) {
        SettingsFragment Settings = new SettingsFragment();

        if (bundle != null) {
            Settings.setArguments(bundle);
        }

        return Settings;
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

    public void recreateActivity() {
        Intent intent = getActivity().getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
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
        return inflater.inflate(R.layout.fragment_settings, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button light_theme_btn = view.findViewById(R.id.light_theme_btn);
        Button dark_theme_btn = view.findViewById(R.id.dark_theme_btn);
        Button amoled_theme_btn = view.findViewById(R.id.amoled_theme_btn);
        CheckBox alerttweaks = view.findViewById(R.id.alerttweaks);

        Button localen = view.findViewById(R.id.localeEN);
        localen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocaleChanger.setLocale(new Locale("en", "US"));
                ActivityRecreationHelper.recreate(getActivity(), false);
            }
        });

        Button localru = view.findViewById(R.id.localeRU);
        localru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocaleChanger.setLocale(new Locale("ru", "RU"));
                ActivityRecreationHelper.recreate(getActivity(), false);
            }
        });


        ////////////////////////////////////////////////////
        ////// Dangerous tweak enabling option  ////////////
        ///////////////////////////////////////////////////
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (mSharedPreference.contains("ALERTCHECK")) {
            alerttweaks.setChecked(true);
        } else {
            alerttweaks.setChecked(false);
        }

        alerttweaks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView,
                                                                                boolean isChecked) {
                                                       if (isChecked) {
                                                           SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                                                           SharedPreferences.Editor editor = prefs.edit();
                                                           editor.putString("ALERTCHECK", "789487947878477");
                                                           editor.commit();
                                                       } else {
                                                           SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                                                           SharedPreferences.Editor editor = prefs.edit();
                                                           editor.remove("ALERTCHECK");
                                                           editor.commit();
                                                       }

                                                   }
                                               }

        );

        ////////////////////////////////////////
        ////// Buttons appearance  ////////////
        ///////////////////////////////////////
        light_theme_btn.setBackgroundResource(R.drawable.roundbuttoncal);
        light_theme_btn.setTextColor(Color.WHITE);
        dark_theme_btn.setBackgroundResource(R.drawable.roundbuttoncal);
        dark_theme_btn.setTextColor(Color.WHITE);
        amoled_theme_btn.setBackgroundResource(R.drawable.roundbuttoncal);
        amoled_theme_btn.setTextColor(Color.WHITE);

        /////////////////////////////////////////////////////////
        ////// Show selected theme symbol on buttons ////////////
        /////////////////////////////////////////////////////////
        if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
            light_theme_btn.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.oksmall), null, null, null);
        }
        if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
            dark_theme_btn.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.oksmall), null, null, null);
        }
        if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
            amoled_theme_btn.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.oksmall), null, null, null);
        }


        //////////////////////////////////////////
        ////// Set application theme  ////////////
        /////////////////////////////////////////
        light_theme_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.setTheme(getContext(), 1);
                recreateActivity();
            }
        });


        dark_theme_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.setTheme(getContext(), 2);
                recreateActivity();
            }
        });


        amoled_theme_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.setTheme(getContext(), 3);
                recreateActivity();
            }
        });

    }

}
