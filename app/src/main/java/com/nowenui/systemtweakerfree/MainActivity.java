package com.nowenui.systemtweakerfree;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.franmontiel.localechanger.LocaleChanger;
import com.nowenui.systemtweakerfree.fragments.AboutDeviceFragment;
import com.nowenui.systemtweakerfree.fragments.AboutProgramFragment;
import com.nowenui.systemtweakerfree.fragments.BackupFragment;
import com.nowenui.systemtweakerfree.fragments.BatteryCalibrationFragment;
import com.nowenui.systemtweakerfree.fragments.BatteryTweaksFragment;
import com.nowenui.systemtweakerfree.fragments.CautonFragment;
import com.nowenui.systemtweakerfree.fragments.ConnectWithDeveloperFragment;
import com.nowenui.systemtweakerfree.fragments.FAQFragment;
import com.nowenui.systemtweakerfree.fragments.FstrimFragment;
import com.nowenui.systemtweakerfree.fragments.HomeFragment;
import com.nowenui.systemtweakerfree.fragments.InternetTweaksFragment;
import com.nowenui.systemtweakerfree.fragments.MediaServerFragment;
import com.nowenui.systemtweakerfree.fragments.MediaTweaksFragment;
import com.nowenui.systemtweakerfree.fragments.RebootManagerFragment;
import com.nowenui.systemtweakerfree.fragments.SettingsFragment;
import com.nowenui.systemtweakerfree.fragments.SystemTweaksFragment;
import com.nowenui.systemtweakerfree.fragments.VariosTweaksFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final static int THEME_LIGHT = 1;
    private final static int THEME_DARK = 2;
    private final static int THEME_AMOLED = 3;
    public int themestyle;
    DrawerLayout androidDrawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;
    private MenuItem prevMenuItem;

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }

    /////////////////////////////////////////////////
    ////// Set Applications Theme //////////////////
    /////////////////////////////////////////////////
    public void updateTheme() {
        if (Utility.getTheme(getApplicationContext()) <= THEME_LIGHT) {
            setTheme(R.style.AppTheme_Light);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        } else if (Utility.getTheme(getApplicationContext()) == THEME_DARK) {
            setTheme(R.style.AppTheme_Dark);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        } else if (Utility.getTheme(getApplicationContext()) == THEME_AMOLED) {
            setTheme(R.style.AppTheme_Amoled);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        }

        themestyle = Utility.getTheme(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ////////////////////////////////////////
        ////// Check theme and apply ///////////
        ////////////////////////////////////////
        super.onCreate(savedInstanceState);


        final SharedPreferences check = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (check.contains("THEME_BASE")) {
            updateTheme();
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            prefs.edit().putInt(getApplicationContext().getString(R.string.prefs_theme_key), 1).apply();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("THEME_BASE", "152458875688545666565989865659885");
            editor.apply();

        }

        setContentView(R.layout.activity_main);

        /////////////////////////////////////////////////
        ////// Enable animations... /////////////////////
        /////////////////////////////////////////////////
        if (Build.VERSION.SDK_INT >= 15) {
            overridePendingTransition(R.anim.anim1, R.anim.anim2);
        }

        initInstancesDrawer();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("System Tweaker FREE");
        }


        /////////////////////////////////////////////////
        ////// Show starting dialog /////////////////////
        /////////////////////////////////////////////////
        boolean isLangRU = Locale.getDefault().getLanguage().equals("ru");
        boolean isLangBE = Locale.getDefault().getLanguage().equals("be");
        boolean isLangUK = Locale.getDefault().getLanguage().equals("uk");
        boolean isLangKK = Locale.getDefault().getLanguage().equals("kk");
        boolean isLangKZ = Locale.getDefault().getLanguage().equals("kz");
        if (isLangRU || isLangBE || isLangUK || isLangKK || isLangKZ) {
            LayoutInflater inflater = getLayoutInflater();
            View dialogru = inflater.inflate(R.layout.hello_ru, null);
            final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            if (!mSharedPreference.contains("DIALOGSTART")) {
                new android.app.AlertDialog.Builder(this)
                        .setView(dialogru)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ponatno, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("DIALOGSTART", "46543");
                                editor.apply();
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        } else {
            LayoutInflater inflater = getLayoutInflater();
            View dialogen = inflater.inflate(R.layout.hello_en, null);
            final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            if (!mSharedPreference.contains("DIALOGSTART")) {
                new android.app.AlertDialog.Builder(this)
                        .setView(dialogen)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ponatno, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("DIALOGSTART", "46543");
                                editor.apply();
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }


    }

    private void initInstancesDrawer() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        androidDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_design_support_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, androidDrawerLayout, R.string.app_name, R.string.app_name);
        androidDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        assert navigationView != null;
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.getMenu().performIdentifierAction(R.id.navigation_drawer_item1, 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public void onBackPressed() {
        if (this.androidDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.androidDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            androidDrawerLayout.openDrawer(GravityCompat.START);
            if (doubleBackToExitPressedOnce || getSupportFragmentManager().getBackStackEntryCount() != 0) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.close, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2500);
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                androidDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        final Resources res = getResources();
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if (prevMenuItem != null)
                            prevMenuItem.setChecked(false);

                        menuItem.setChecked(true);
                        Bundle bundle = new Bundle();
                        String fragmentTitle = null;
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_drawer_item1:
                                fragmentTitle = "System Tweaker FREE";
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.content, HomeFragment.newInstance(bundle))
                                        .commit();


                                break;
                            case R.id.navigation_drawer_item2:
                                fragmentTitle = res.getString(R.string.infoabout);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, AboutDeviceFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item4:
                                fragmentTitle = res.getString(R.string.batterytweaks);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, BatteryTweaksFragment.newInstance(bundle))
                                        .commit();

                                break;
                            case R.id.navigation_drawer_item6:
                                fragmentTitle = res.getString(R.string.internettweaks);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, InternetTweaksFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item7:
                                fragmentTitle = res.getString(R.string.systemtweaks);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, SystemTweaksFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item5:
                                fragmentTitle = res.getString(R.string.calbattery);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, BatteryCalibrationFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item13:
                                fragmentTitle = "FSTRIM";
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, FstrimFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.settings:
                                fragmentTitle = res.getString(R.string.settingstile);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, SettingsFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item8:
                                fragmentTitle = res.getString(R.string.mediatweaks);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, MediaTweaksFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item9:
                                fragmentTitle = res.getString(R.string.backupandrestore);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, BackupFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item10:
                                fragmentTitle = res.getString(R.string.soglasenie);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, CautonFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item11:
                                fragmentTitle = res.getString(R.string.connect);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, ConnectWithDeveloperFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item12:
                                fragmentTitle = res.getString(R.string.aboutapp);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, AboutProgramFragment.newInstance(bundle))
                                        .commit();
                                break;

                            case R.id.navigation_drawer_item15:
                                fragmentTitle = res.getString(R.string.varioustweaks);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, VariosTweaksFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item22:
                                fragmentTitle = res.getString(R.string.rebootmanager);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, RebootManagerFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item30:
                                fragmentTitle = res.getString(R.string.faq);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, FAQFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item20:
                                fragmentTitle = "MediaServer | MediaScanner";
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                        .replace(R.id.content, MediaServerFragment.newInstance(bundle))
                                        .commit();
                                break;

                            case R.id.navigation_drawer_item24:
                                fragmentTitle = "System Tweaker FREE";
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        Uri address = Uri.parse("http://vk.com/nowenui_official_group");
                                        Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                                        startActivity(openlink);
                                    }
                                }, 300);

                                menuItem.setChecked(false);

                                break;

                            case R.id.navigation_drawer_item25:
                                fragmentTitle = "System Tweaker FREE";
                                Handler handler2 = new Handler();
                                handler2.postDelayed(new Runnable() {
                                    public void run() {
                                        Uri addresstwi = Uri.parse("https://twitter.com/intent/user?user_id=4771768877");
                                        Intent openlinktwi = new Intent(Intent.ACTION_VIEW, addresstwi);
                                        startActivity(openlinktwi);
                                    }
                                }, 300);

                                menuItem.setChecked(false);

                                break;

                            case R.id.proversion:
                                fragmentTitle = "System Tweaker FREE";
                                Handler handler3 = new Handler();
                                handler3.postDelayed(new Runnable() {
                                    public void run() {
                                        Uri addresstwi = Uri.parse("https://play.google.com/store/apps/details?id=com.nowenui.systemtweaker");
                                        Intent openlinktwi = new Intent(Intent.ACTION_VIEW, addresstwi);
                                        startActivity(openlinktwi);
                                    }
                                }, 300);

                                menuItem.setChecked(false);

                                break;

                            case R.id.navigation_drawer_item18:
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                int pid = android.os.Process.myPid();
                                android.os.Process.killProcess(pid);
                                break;
                        }

                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(fragmentTitle);
                        }

                        androidDrawerLayout.closeDrawers();
                        prevMenuItem = menuItem;
                        return true;
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}