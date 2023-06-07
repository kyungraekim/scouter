package com.example.scouter.ui.scan.placeholder;

import com.example.scouter.scan.ScanResult;

import java.time.Duration;
import java.time.Instant;

public class DeviceScanItem {
    private final String address;
    private String deviceName;
    private int value;
    private int interval;
    private Instant timestamp;

    public DeviceScanItem(ScanResult scanResult) {
        this.deviceName = scanResult.getDeviceName();
        this.address = scanResult.getAddress();
        this.value = scanResult.getRssi();
        this.timestamp = scanResult.getTimestamp();

    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getAddress() {
        return address;
    }

    public int getValue() {
        return value;
    }

    public int getInterval() {
        return interval;
    }

    public void update(ScanResult scanResult) {
        deviceName = scanResult.getDeviceName();
        value = scanResult.getRssi();
        interval = (int) Duration.between(timestamp, scanResult.getTimestamp()).toMillis();
        timestamp = scanResult.getTimestamp();
    }
}
