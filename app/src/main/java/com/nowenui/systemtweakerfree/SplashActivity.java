package com.nowenui.systemtweakerfree;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    private static final int ALERT_DIALOG2 = 2;
    public static int checksu, checkbusy;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (RootTools.isAccessGiven()) {
            checksu = 1;
            if (RootTools.isBusyboxAvailable()) {
                checkbusy = 1;
                if (isInitdSupport() == 1) {
                    startMainActivity();
                } else {
                    showDialog(ALERT_DIALOG2);
                }
            } else {
                showDialog(ALERT_DIALOG2);
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
