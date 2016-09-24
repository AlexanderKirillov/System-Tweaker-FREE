package com.nowenui.systemtweakerfree;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SplashActivity extends AppCompatActivity {

    private static final int ALERT_DIALOG2 = 2;
    public static int checksu, checkbusy;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (RootTools.isAccessGiven()) {
            checksu = 1;
            String check = "/data/com.nowenui.systemtweaker/files/busybox";
            String check1 = "/data/data/com.nowenui.systemtweakerfree/files/busybox";
            if ((new File(Environment.getRootDirectory() + check).exists() || new File(check1).exists() || new File(Environment.getRootDirectory() + check1).exists())) {
                checkbusy = 1;
                if (isInitdSupport() == 1) {
                    startMainActivity();
                } else {
                    if (RootTools.isRootAvailable()) {
                        if (RootTools.isAccessGiven()) {
                            Command command1 = new Command(0,
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /rootfs /",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox cp /sdcard/SystemTweaker/install-recovery.sh /system/etc/install-recovery.sh",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox chmod 755 /system/etc/install-recovery.sh",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox rm -f /system/bin/sysinit",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox cp /sdcard/SystemTweaker/sysinit /system/bin/sysinit",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox chmod 755 /system/bin/sysinit",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mkdir /system/etc/init.d",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox chmod 777 /system/etc/init.d",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system");
                            try {
                                RootTools.getShell(true).add(command1);
                                startMainActivity();
                            } catch (IOException | RootDeniedException | TimeoutException ex) {
                                ex.printStackTrace();
                                Toast.makeText(getApplicationContext(), R.string.errordev, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                if (RootTools.isRootAvailable()) {
                    if (RootTools.isAccessGiven()) {
                        Command command1 = new Command(0,
                                "cp /sdcard/SystemTweaker/busybox /data/data/com.nowenui.systemtweakerfree/files/",
                                "chmod 777 /data/data/com.nowenui.systemtweakerfree/files/busybox");
                        try {
                            RootTools.getShell(true).add(command1);
                            checkbusy = 1;
                        } catch (IOException | RootDeniedException | TimeoutException ex) {
                            ex.printStackTrace();
                            Toast.makeText(getApplicationContext(), R.string.errordev, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                }
                if (isInitdSupport() == 1) {
                    startMainActivity();
                } else {
                    if (RootTools.isRootAvailable()) {
                        if (RootTools.isAccessGiven()) {
                            Command command1 = new Command(0,
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /proc /system",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o rw,remount /rootfs /",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox cp /sdcard/SystemTweaker/install-recovery.sh /system/etc/install-recovery.sh",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox chmod 755 /system/etc/install-recovery.sh",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox rm -f /system/bin/sysinit",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox cp /sdcard/SystemTweaker/sysinit /system/bin/sysinit",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox chmod 755 /system/bin/sysinit",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mkdir /system/etc/init.d",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox chmod 777 /system/etc/init.d",
                                    "/data/data/com.nowenui.systemtweakerfree/files/busybox mount -o ro,remount /proc /system");
                            try {
                                RootTools.getShell(true).add(command1);
                                startMainActivity();
                            } catch (IOException | RootDeniedException | TimeoutException ex) {
                                ex.printStackTrace();
                                Toast.makeText(getApplicationContext(), R.string.errordev, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            showDialog(ALERT_DIALOG2);
        }
        setContentView(R.layout.splash_activity);
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
        }, 2000);
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        Dialog dialog;
        AlertDialog.Builder builder;
        switch (id) {
            case ALERT_DIALOG2:
                builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.error)
                        .setMessage(R.string.rootbusybox)
                        .setCancelable(false)
                        .setNegativeButton(R.string.okinstall, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
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

    private void startMainActivity() {
        int SPLASH_TIME_OUT = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    public int isInitdSupport() {
        File f = new File("/system/etc/init.d");
        File f1 = new File("/system/su.d");
        File f2 = new File("/su/su.d");
        if ((f.exists()) && (f.isDirectory()) || (f1.exists()) && (f1.isDirectory()) || (f2.exists()) && (f2.isDirectory())) {
            return 1;
        }
        return 0;
    }

}
