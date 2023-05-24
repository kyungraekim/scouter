package com.example.scouter.scan;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;

import io.reactivex.rxjava3.core.Observable;

public class BleScanner {
    private final BluetoothAdapter adapter;
    private final BluetoothLeScanner scanner;

    public BleScanner() {
        this.adapter = BluetoothAdapter.getDefaultAdapter();
        scanner = adapter.getBluetoothLeScanner();
    }

    @SuppressLint("MissingPermission")
    public Observable<ScanResult> scan() {
        return Observable.create(emitter -> {
            ScanCallback callback = new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, android.bluetooth.le.ScanResult result) {
                    ScanResult scanResult = new ScanResult(
                            result.getDevice().getName(),
                            result.getDevice().getAddress(),
                            result.getRssi()
                    );
                    emitter.onNext(scanResult);
                }

                @Override
                public void onScanFailed(int errorCode) {
                    emitter.onError(new RuntimeException("Scan failed with error code: " + errorCode));
                }
            };
            scanner.startScan(callback);
            emitter.setCancellable(() -> scanner.stopScan(callback));
        });
    }
}
