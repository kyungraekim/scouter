package com.example.scouter.scan;

import android.Manifest;
import android.os.Build;

public class Permission {
    public static final String[] BLUETOOTH_LOW_ENERGY;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            BLUETOOTH_LOW_ENERGY = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
            };
        } else {
            BLUETOOTH_LOW_ENERGY = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
            };
        }
    }
}
