package com.example.scouter.ui.scan;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scouter.databinding.FragmentScanBinding;
import com.example.scouter.scan.Permission;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {
    private FragmentScanBinding binding;
    private ScanViewModel viewModel;
    private ScanListAdapter scanListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScanBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = binding.listDeviceScan;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        scanListAdapter = new ScanListAdapter();
        recyclerView.setAdapter(scanListAdapter);

        viewModel = new ViewModelProvider(this).get(ScanViewModel.class);
        checkBluetoothPermission();
        viewModel.getScanList().observe(getViewLifecycleOwner(), deviceScanItems -> {
            binding.listDeviceScan.setVisibility(View.VISIBLE);
            binding.textErrorScanPermission.setVisibility(View.GONE);
            scanListAdapter.updateList(deviceScanItems);
        });
        return binding.getRoot();
    }

    private void checkBluetoothPermission() {
        ActivityResultLauncher<String[]> requestPermissionLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.RequestMultiplePermissions(),
                        booleanMap -> {
                            boolean isGranted = isBlePermitted();
                            if (isGranted) {
                                Toast.makeText(getContext(), "Granted", Toast.LENGTH_SHORT).show();
                                enableScan();
                            } else {
                                Toast.makeText(getContext(), "Not granted", Toast.LENGTH_SHORT).show();
                                disableScan();
                            }
                        });

        if (!isBlePermitted()) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Bluetooth Scan Permission")
                    .setMessage("Requires access to scan BLE devices")
                    .setPositiveButton("Allow", ((dialogInterface, i) ->
                            requestPermissionLauncher.launch(Permission.BLUETOOTH_LOW_ENERGY)))
                    .setNegativeButton("No", ((dialogInterface, i) -> disableScan()))
                    .show();
        } else {
            enableScan();
        }
    }

    private boolean isBlePermitted() {
        boolean isGranted = true;
        for (String permission : Permission.BLUETOOTH_LOW_ENERGY) {
            isGranted &= ContextCompat.checkSelfPermission(requireContext(), permission) ==
                    PackageManager.PERMISSION_GRANTED;
        }
        return isGranted;
    }

    private void disableScan() {
        binding.listDeviceScan.setVisibility(View.GONE);
        binding.textErrorScanPermission.setVisibility(View.VISIBLE);
    }

    private void enableScan() {
        binding.textErrorScanPermission.setVisibility(View.GONE);
        viewModel.startScan();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}