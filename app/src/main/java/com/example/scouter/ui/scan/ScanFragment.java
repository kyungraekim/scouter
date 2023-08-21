package com.example.scouter.ui.scan;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scouter.databinding.FragmentScanBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {
    private FragmentScanBinding binding;
    private ScanViewModel viewModel;
    private ScanListAdapter scanListAdapter;
    private ActivityResultLauncher<String[]> requestLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestLauncher = getRequestLauncher();
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
        viewModel.getPermissions().observe(getViewLifecycleOwner(), permissions ->
                requestPermissions(requestLauncher, permissions));
        viewModel.getScanList().observe(getViewLifecycleOwner(), deviceScanItems -> {
            binding.listDeviceScan.setVisibility(View.VISIBLE);
            binding.textErrorScanPermission.setVisibility(View.GONE);
            scanListAdapter.updateList(deviceScanItems);
        });
        viewModel.startScan();
        return binding.getRoot();
    }

    private ActivityResultLauncher<String[]> getRequestLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                booleanMap -> {
                    boolean isGranted = true;
                    for (boolean eachGranted : booleanMap.values()) {
                        isGranted &= eachGranted;
                    }
                    if (isGranted) {
                        Toast.makeText(getContext(),
                                "Granted", Toast.LENGTH_SHORT).show();
                        enableScan();
                        viewModel.startScan();
                    } else {
                        Toast.makeText(getContext(),
                                "Not granted", Toast.LENGTH_SHORT).show();
                        disableScan();
                    }
                }
        );
    }

    private <T> void requestPermissions(ActivityResultLauncher<T> requestLauncher,
                                        T permissions) {
        Log.i("permission", "request permissions");
        new AlertDialog.Builder(getContext())
                .setTitle("Permission requests for scanning")
                .setMessage("Requires permissions to scan devices")
                .setPositiveButton("Allow", (dialogInterface, i) ->
                        requestLauncher.launch(permissions))
                .setNegativeButton("No", ((dialogInterface, i) -> disableScan()))
                .show();
    }

    private void disableScan() {
        binding.listDeviceScan.setVisibility(View.GONE);
        binding.textErrorScanPermission.setVisibility(View.VISIBLE);
    }

    private void enableScan() {
        binding.textErrorScanPermission.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}