package com.nowenui.systemtweakerfree.fragments;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MemInfo {
    private final Context context;

    public MemInfo(Context context) {
        this.context = context;
    }

    public long getTotalRAM() {
        long total_memory_in_Mbs = 0;
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            MemoryInfo mi = new MemoryInfo();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
            total_memory_in_Mbs = mi.totalMem / 1048576L;
        } else {
            RandomAccessFile reader;
            String load;
            try {
                reader = new RandomAccessFile("/proc/meminfo", "r");
                load = reader.readLine().replaceAll("\\D+", "");
                total_memory_in_Mbs = Integer.parseInt(load) / 1024;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return total_memory_in_Mbs;
    }

    public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        long totalBlocks;
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
        }
        return (totalBlocks * blockSize) / (1024 * 1024);
    }

}
