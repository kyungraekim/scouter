package com.example.scouter.ui.scan;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.scouter.scan.BleScanner;
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

    public ScanViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<DeviceScanItem>> getScanList() {
        return scanList;
    }

    public void startScan() {
        disposable.add(
                scanner.scan()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(scanResult -> {
                            if (!scanIndexed.containsKey(scanResult.getAddress())) {
                                scanIndexed.put(scanResult.getAddress(), scanIndexed.size());
                                DeviceScanItem item = new DeviceScanItem(scanResult);
                                scanned.add(item);
                            } else {
                                Integer index = scanIndexed.get(scanResult.getAddress());
                                assert index != null;
                                DeviceScanItem item = scanned.get(index);
                                item.update(scanResult);
                            }

                            scanList.setValue(scanned);
                        })
        );
    }
}
