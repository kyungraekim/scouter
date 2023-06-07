package com.example.scouter.scan;

import java.time.Instant;

public class ScanResult {
    private final String deviceName;
    private final String address;
    private final int rssi;
    private final Instant timestamp;

    public ScanResult(String deviceName, String address, int rssi) {
        this.deviceName = deviceName;
        this.address = address;
        this.rssi = rssi;
        this.timestamp = Instant.now();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getAddress() {
        return address;
    }

    public int getRssi() {
        return rssi;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
