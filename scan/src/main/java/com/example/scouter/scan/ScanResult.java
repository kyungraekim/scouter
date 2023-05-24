package com.example.scouter.scan;

public class ScanResult {
    private final String deviceName;
    private final String address;
    private final int rssi;

    public ScanResult(String deviceName, String address, int rssi) {
        this.deviceName = deviceName;
        this.address = address;
        this.rssi = rssi;
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
}
