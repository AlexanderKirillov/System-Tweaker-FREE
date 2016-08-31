package com.nowenui.systemtweakerfree.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HeapSize {
    public HeapSize() {
    }

    public static class HardwareInfo {

        public static String getHeapSize() {
            try {
                Process proc = Runtime.getRuntime().exec("getprop dalvik.vm.heapsize");
                InputStream is = proc.getInputStream();
                String size = getStringFromInputStream(is);
                if (!size.equals("\n"))
                    return size;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "? (unknown)";
        }

        private static String getStringFromInputStream(InputStream is) {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;

            try {
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }

    }

}
