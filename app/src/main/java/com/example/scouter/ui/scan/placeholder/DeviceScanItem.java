package com.example.scouter.ui.scan.placeholder;

public class DeviceScanItem {
    private final String deviceName;
    private final int value;

    public DeviceScanItem(String deviceName, int value) {
        this.deviceName = deviceName;
        this.value = value;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getValue() {
        return value;
    }
}
