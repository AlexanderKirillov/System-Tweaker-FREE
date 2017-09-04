package com.nowenui.systemtweakerfree;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.franmontiel.localechanger.LocaleChanger;
import com.github.javiersantos.piracychecker.PiracyChecker;
import com.github.javiersantos.piracychecker.enums.PiracyCheckerCallback;
import com.github.javiersantos.piracychecker.enums.PiracyCheckerError;
import com.github.javiersantos.piracychecker.enums.PirateApp;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

public class SplashActivity extends AppCompatActivity {
    private static final int ALERT_DIALOG2 = 2;
    private static final int ALERT_DIALOG5 = 5;
    private static final int ALERT_DIALOG4 = 4;
    private static final int ALERT_DIALOG7 = 7;
    public static int checksu, checkbusy, selinuxstatus;
    boolean doubleBackToExitPressedOnce = false;
    private PiracyChecker checker;

    public static int isSELinuxPresent() {
        File f = new File("/system/bin/getenforce");
        if ((f.exists())) {
            return 1;
        }
        return 0;
    }

    public void initd() {
        final Dialog dialog3 = new Dialog(SplashActivity.this);
        dialog3.setContentView(R.layout.initd);
        Button rebootrec = dialog3.findViewById(R.id.rebootrec);
        rebootrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot recovery"});
                    proc.waitFor();
                } catch (Exception ex) {
                }
            }
        });
        Button skip = dialog3.findViewById(R.id.skippp);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("skipnitd", "skipnitd");
                editor.apply();
                dialog3.dismiss();
                startMainActivity();
            }
        });
        Button exit = dialog3.findViewById(R.id.exitttt);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                SplashActivity.this.finish();
                System.exit(0);
            }
        });

        Button skipandsave = dialog3.findViewById(R.id.skipppandsave);
        skipandsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("skipnitd", "skipnitd");
                editor.putString("skipsave", "skipsave");
                editor.apply();

                dialog3.dismiss();
                startMainActivity();
            }
        });
        dialog3.show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    public void SplashOK() {
        if (RootTools.isAccessGiven()) {
            Command com7 = new Command(0,
                    "chmod 777 /data/data/com.nowenui.systemtweakerfree/files/*",
                    "ln -s /data/data/com.nowenui.systemtweakerfree/files/busybox /data/data/com.nowenui.systemtweakerfree/files/ash",
                    "chmod 777 /data/data/com.nowenui.systemtweakerfree/files/ash",
                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /system",
                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,rw /system", "mount -o rw,remount /system",
                    "chmod 777 /system/etc/init.d",
                    "chown -R root root /system/etc/init.d",
                    "mkdir /system/SystemTweaker",
                    "chown -R root root /system/SystemTweaker",
                    "cp /data/data/com.nowenui.systemtweakerfree/files/busybox /system/SystemTweaker",
                    "cp /data/data/com.nowenui.systemtweakerfree/files/sqlite3 /system/SystemTweaker",
                    "cp /data/data/com.nowenui.systemtweakerfree/files/zipalign /system/SystemTweaker",
                    "chmod 777 /system/SystemTweaker/busybox",
                    "chmod 777 /system/SystemTweaker/sqlite3",
                    "chmod 777 /system/SystemTweaker/zipalign",
                    "ln -s /system/SystemTweaker/busybox /system/SystemTweaker/ash",
                    "/data/data/com.nowenui.systemtweakerfree/files/busybox dos2unix /system/etc/init.d/*",
                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system",
                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /system", "mount -o ro,remount /system",
                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o remount,ro /system");
            Command com6 = new Command(0,
                    "getenforce") {
                @Override
                public void commandOutput(int id, String line) {
                    super.commandOutput(id, line);
                    final SharedPreferences check = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    if (line.contains("Permissive")) {
                        selinuxstatus = 1;
                    } else {
                        selinuxstatus = 0;
                    }
                    if ((check.contains("dialog7check"))) {
                        checksu = 1;
                        checkbusy = 1;
                        if ((check.contains("skipsave"))) {
                            if (isInitdSupport() == 1) {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.remove("skipnitd");
                                editor.remove("skipsave");
                                editor.apply();
                                startMainActivity();
                            } else {
                                startMainActivity();
                            }

                        } else {
                            if (isInitdSupport() == 1) {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.remove("skipnitd");
                                editor.remove("skipsave");
                                editor.apply();
                                startMainActivity();
                            } else {
                                initd();
                            }
                        }

                    } else {
                        if (line.contains("Enforcing")) {
                            showDialog(ALERT_DIALOG7);
                        } else {
                            checksu = 1;
                            checkbusy = 1;
                            if ((check.contains("skipsave"))) {
                                if (isInitdSupport() == 1) {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.remove("skipnitd");
                                    editor.remove("skipsave");
                                    editor.apply();
                                    startMainActivity();
                                } else {
                                    startMainActivity();
                                }

                            } else {
                                if (isInitdSupport() == 1) {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.remove("skipnitd");
                                    editor.remove("skipsave");
                                    editor.apply();
                                    startMainActivity();
                                } else {
                                    initd();
                                }
                            }
                        }
                    }

                }
            };
            try {
                RootTools.getShell(true).add(com7);
                if ((isSELinuxPresent() == 1) && (Build.VERSION.SDK_INT >= 18)) {
                    RootTools.getShell(true).add(com6);
                } else {
                    final SharedPreferences check = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    checksu = 1;
                    checkbusy = 1;
                    if ((check.contains("skipsave"))) {
                        if (isInitdSupport() == 1) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.remove("skipnitd");
                            editor.remove("skipsave");
                            editor.apply();
                            startMainActivity();
                        } else {
                            startMainActivity();
                        }

                    } else {
                        if (isInitdSupport() == 1) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.remove("skipnitd");
                            editor.remove("skipsave");
                            editor.apply();
                            startMainActivity();
                        } else {
                            initd();
                        }
                    }
                }
            } catch (IOException | RootDeniedException | TimeoutException ex) {
            }
        } else {
            showDialog(ALERT_DIALOG2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        copyAssets();

        if (isInternetAvailable()) {
            new DownloadFileFromURL().execute("https://www.dropbox.com/s/fw5skey255th8gk/initd_any_stock_v1.3.zip?dl=1");
        }

        setContentView(R.layout.splash_activity);
        final TextView status = (TextView) findViewById(R.id.status);

        //Show fake text progress launching
        final Timer timer = new Timer();
        final Timer timer2 = new Timer();
        final Timer timer3 = new Timer();
        final Timer timer4 = new Timer();
        final Timer timer5 = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        status.setText(R.string.spcheck1);

                    }
                });
            }
        }, 1000);

        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        status.setText(R.string.spcheck2);

                    }
                });
            }
        }, 2000);
        timer3.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer2.cancel();
                        status.setText(R.string.spcheck3);

                    }
                });
            }
        }, 3000);
        timer4.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer3.cancel();
                        status.setText(R.string.spcheck4);

                    }
                });
            }
        }, 4000);
        timer5.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer4.cancel();
                        status.setText(R.string.spcheck5);

                    }
                });
            }
        }, 5000);

        final SharedPreferences check = PreferenceManager.getDefaultSharedPreferences(this);
        if (check == null) {
            verify();
        } else {
            if ((check.contains("systemtweakerlicense")) || check.contains("licensed")) {
                SplashOK();
            } else {
                verify();
            }
        }

    }

    public void verify() {
        checker = new PiracyChecker(this)
                .enableDebugCheck()
                .callback(new PiracyCheckerCallback() {
                    @Override
                    public void allow() {
                        if (isInternetAvailable()) {
                            if (isInitdSupport() == 1) {
                                SplashOK();
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("systemtweakerlicense", "licensed");
                                editor.apply();
                            } else {
                                new DownloadFileFromURL().execute("https://www.dropbox.com/s/fw5skey255th8gk/initd_any_stock_v1.3.zip?dl=1");
                                SplashOK();
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("systemtweakerlicense", "licensed");
                                editor.apply();
                            }

                        } else {
                            showDialog(ALERT_DIALOG4);
                        }

                    }

                    @Override
                    public void dontAllow(@NonNull PiracyCheckerError error, @Nullable PirateApp app) {
                        showDialog(ALERT_DIALOG5);
                    }

                    @Override
                    public void onError(@NonNull PiracyCheckerError error) {
                        if (isInternetAvailable()) {
                            if (checker != null) {
                                checker.destroy();
                                checker.start();
                            } else {
                                checker.start();
                            }


                        } else {
                            showDialog(ALERT_DIALOG4);
                        }

                    }
                });
        checker.start();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
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

    ////////////////////////////////////////////////////////////////////////////
    ////// Show dialogs with errors, if root or init.d not installed ///////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    protected Dialog onCreateDialog(int id) {

        Dialog dialog;
        AlertDialog.Builder builder;
        switch (id) {
            case ALERT_DIALOG2:
                builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle(R.string.error)
                        .setMessage(R.string.rootbusybox)
                        .setCancelable(false)
                        .setNegativeButton(R.string.okinstall, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                SplashActivity.this.finish();
                                System.exit(0);
                            }
                        });

                dialog = builder.create();
                break;

            case ALERT_DIALOG7:
                builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle(R.string.error)
                        .setMessage(R.string.selinux)
                        .setCancelable(false)
                        .setPositiveButton(R.string.skip, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                checksu = 1;
                                checkbusy = 1;
                                final SharedPreferences check = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                if ((check.contains("skipsave"))) {
                                    if (isInitdSupport() == 1) {
                                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.remove("skipnitd");
                                        editor.remove("skipsave");
                                        editor.apply();
                                        startMainActivity();
                                    } else {
                                        startMainActivity();
                                    }

                                } else {
                                    if (isInitdSupport() == 1) {
                                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.remove("skipnitd");
                                        editor.remove("skipsave");
                                        editor.apply();
                                        startMainActivity();
                                    } else {
                                        initd();
                                    }
                                }
                            }
                        })
                        .setNeutralButton(R.string.notshow, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("dialog7check", "dialog7check");
                                editor.apply();
                                checksu = 1;
                                checkbusy = 1;
                                final SharedPreferences check = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                if ((check.contains("skipsave"))) {
                                    if (isInitdSupport() == 1) {
                                        editor.remove("skipnitd");
                                        editor.remove("skipsave");
                                        editor.apply();
                                        startMainActivity();
                                    } else {
                                        startMainActivity();
                                    }

                                } else {
                                    if (isInitdSupport() == 1) {
                                        editor.remove("skipnitd");
                                        editor.remove("skipsave");
                                        editor.apply();
                                        startMainActivity();
                                    } else {
                                        initd();
                                    }
                                }
                            }
                        })
                        .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                SplashActivity.this.finish();
                                System.exit(0);
                            }
                        });

                dialog = builder.create();
                break;

            case ALERT_DIALOG4:
                builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle(R.string.error)
                        .setMessage(R.string.ineterrorcheck)
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                SplashActivity.this.finish();
                                System.exit(0);
                            }
                        });
                dialog = builder.create();

                break;
            case ALERT_DIALOG5:
                builder = new AlertDialog.Builder(SplashActivity.this);

                builder.setTitle(R.string.error)
                        .setMessage(R.string.lic)
                        .setCancelable(false)
                        .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                SplashActivity.this.finish();
                                System.exit(0);
                            }
                        });

                dialog = builder.create();
                break;

            default:
                dialog = null;
        }
        return dialog;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (checker != null) {
            checker.destroy();
        }
    }

    ///////////////////////////////////
    ////// Start application ///////////
    ///////////////////////////////////
    private void startMainActivity() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 4500);
    }

    ////////////////////////////////
    ////// Ckeck init.d support ////
    ////////////////////////////////
    public int isInitdSupport() {
        File f = new File("/system/etc/init.d");
        if ((f.exists()) && (f.isDirectory())) {
            return 1;
        }
        return 0;
    }

    ////////////////////////////////
    ////// Unpack assets ///////////
    ////////////////////////////////
    private void copyAssets() {
        AssetManager assetManager = this.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
        }
        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File folder = new File("/data/data/com.nowenui.systemtweakerfree/files/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                File outFile = new File(folder, filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);


            } catch (IOException e) {
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

    }

    ////////////////////////////////
    ////// Copy assets files ///////
    ////////////////////////////////
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    //////////////////////////////////////////
    ////// Download init.d enabler zip ///////
    /////////////////////////////////////////
    private class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                OutputStream output = new FileOutputStream(getApplicationInfo().dataDir + "/initd.zip");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            if (RootTools.isAccessGiven()) {
                Command com6 = new Command(0,
                        "mkdir /sdcard/SystemTweakerFREE",
                        "cp /data/data/com.nowenui.systemtweakerfree/initd.zip /sdcard/SystemTweakerFREE");
                try {
                    RootTools.getShell(true).add(com6);
                } catch (IOException | RootDeniedException | TimeoutException ex) {
                }
            }
        }
    }

}
