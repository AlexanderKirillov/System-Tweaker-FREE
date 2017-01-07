package com.nowenui.systemtweakerfree;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeoutException;

public class SplashActivity extends AppCompatActivity {
    private static final int ALERT_DIALOG2 = 2;
    private static final int ALERT_DIALOG3 = 3;
    public static int checksu, checkbusy;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);

        copyAssets();

        Command com5 = new Command(0,
                "chmod 777 /data/data/com.nowenui.systemtweakerfree/files/*");
        try {
            RootTools.getShell(true).add(com5);
        } catch (IOException | RootDeniedException | TimeoutException ex) {
        }
        if (RootTools.isRootAvailable()) {
            if (RootTools.isAccessGiven()) {
                Command com6 = new Command(0,
                        "ln -s /data/data/com.nowenui.systemtweakerfree/files/busybox /data/data/com.nowenui.systemtweakerfree/files/ash",
                        "/data/data/com.nowenui.systemtweakerfree/files/busybox dos2unix /system/etc/init.d/*");
                try {
                    RootTools.getShell(true).add(com6);
                } catch (IOException | RootDeniedException | TimeoutException ex) {
                }
                checksu = 1;
                checkbusy = 1;
                if (isInitdSupport() == 1) {
                    startMainActivity();
                } else {
                    showDialog(ALERT_DIALOG3);
                }
            } else {
                showDialog(ALERT_DIALOG2);
            }
        } else {
            showDialog(ALERT_DIALOG2);
        }
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
            case ALERT_DIALOG3:
                builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.error)
                        .setMessage("Увы, но мы не обнаружили поддержку init.d. К сожалению, начиная с версии 2.2.3, мы убрали автоматическое включение поддержки init.d по требованию Google. \n\nПожалуйста, отнеситесь с пониманием, включить поддержку init.d Вы можете с помощью приложения Init.d Toogler, которое Вы можете найти в Google Play! Спасибо!")
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void startMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }

    public int isInitdSupport() {
        File f = new File("/system/etc/init.d");
        if ((f.exists()) && (f.isDirectory())) {
            return 1;
        }
        return 0;
    }

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

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }


}
