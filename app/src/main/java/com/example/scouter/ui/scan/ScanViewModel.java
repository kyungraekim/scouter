package com.example.scouter.ui.scan;

import android.app.Application;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.scouter.scan.BleScanner;
import com.example.scouter.scan.Permission;
import com.example.scouter.scan.ScanResult;
import com.example.scouter.ui.scan.placeholder.DeviceScanItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ScanViewModel extends AndroidViewModel {
    private final List<DeviceScanItem> scanned = new ArrayList<>();
    private final MutableLiveData<List<DeviceScanItem>> scanList = new MutableLiveData<>();
    private final Map<String, Integer> scanIndexed = new HashMap<>();
    private final BleScanner scanner = new BleScanner();
    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<String[]> permissions = new MutableLiveData<>();

    public ScanViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<DeviceScanItem>> getScanList() {
        return scanList;
    }

    public MutableLiveData<String[]> getPermissions() {
        return permissions;
    }

    public void startScan() {
        if (!isBlePermitted()) {
            permissions.setValue(Permission.BLUETOOTH_LOW_ENERGY);
            return;
        }
        disposable.add(
                scanner.scan()
                        .subscribeOn(Schedulers.io())
                        .map(this::updateScanResult)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(ignoredItem -> scanList.setValue(scanned))
        );
    }

    private DeviceScanItem updateScanResult(ScanResult scanResult) {
        DeviceScanItem item;
        if (!scanIndexed.containsKey(scanResult.getAddress())) {
            scanIndexed.put(scanResult.getAddress(), scanIndexed.size());
            item = new DeviceScanItem(scanResult);
            scanned.add(item);
        } else {
            Integer index = scanIndexed.get(scanResult.getAddress());
            assert index != null;
            item = scanned.get(index);
            item.update(scanResult);
        }

        return item;
    }

    public String[] getRequiredPermissions() {
        return Permission.BLUETOOTH_LOW_ENERGY;
    }

    public boolean isBlePermitted() {
        boolean isGranted = true;
        for (String permission : getRequiredPermissions()) {
            isGranted &= ContextCompat.checkSelfPermission(
                    getApplication().getApplicationContext(), permission
            ) == PackageManager.PERMISSION_GRANTED;
        }
        return isGranted;
    }
}
