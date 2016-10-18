package com.nowenui.systemtweakerfree;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.nowenui.systemtweakerfree.fragments.AboutFragment;
import com.nowenui.systemtweakerfree.fragments.AboutProgramFragment;
import com.nowenui.systemtweakerfree.fragments.BackupFragment;
import com.nowenui.systemtweakerfree.fragments.BatteryFragment;
import com.nowenui.systemtweakerfree.fragments.BatteryTweaksEnFragment;
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
import com.nowenui.systemtweakerfree.fragments.SystemTweaksFragment;
import com.nowenui.systemtweakerfree.fragments.VariosTweaksFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DrawerLayout androidDrawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        boolean isLang = Locale.getDefault().getLanguage().equals("ru");
        if (isLang) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_main_en);
        }

        if (Build.VERSION.SDK_INT >= 15) {
            overridePendingTransition(R.anim.anim1, R.anim.anim2);
        }

        initInstancesDrawer();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("System Tweaker FREE");
        }


        WebView myWebView = new WebView(MainActivity.this);

        if (isLang == true) {
            myWebView.loadUrl("file:///android_asset/hello_ru.html");
            myWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        } else {
            myWebView.loadUrl("file:///android_asset/hello_en.html");
            myWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        }

        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (mSharedPreference.contains("DIALOGSTART")) {
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.helloatr)
                    .setView(myWebView)
                    .setPositiveButton(R.string.ponatno, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("DIALOGSTART", "535335");
                            editor.commit();
                            dialog.dismiss();
                        }
                    })
                    .setIcon(R.drawable.warning)
                    .show();
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
            }, 2000);
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
                                        .replace(R.id.content, AboutFragment.newInstance(bundle))
                                        .commit();
                                break;
                            case R.id.navigation_drawer_item4:
                                boolean isLang = Locale.getDefault().getLanguage().equals("ru");
                                if (isLang) {
                                    fragmentTitle = res.getString(R.string.batterytweaks);
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                            .replace(R.id.content, BatteryTweaksFragment.newInstance(bundle))
                                            .commit();
                                } else {
                                    fragmentTitle = res.getString(R.string.batterytweaks);
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                                            .replace(R.id.content, BatteryTweaksEnFragment.newInstance(bundle))
                                            .commit();
                                }

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
                                        .replace(R.id.content, BatteryFragment.newInstance(bundle))
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
                                fragmentTitle = "FAQ и решение проблем";
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
                                        Intent vk = new Intent();
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
                                        Intent twitter = new Intent();
                                        Uri addresstwi = Uri.parse("https://twitter.com/intent/user?user_id=4771768877");
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

}