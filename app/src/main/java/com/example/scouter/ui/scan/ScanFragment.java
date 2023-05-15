package com.example.scouter.ui.scan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scouter.databinding.FragmentScanBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {
    private FragmentScanBinding binding;
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
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}