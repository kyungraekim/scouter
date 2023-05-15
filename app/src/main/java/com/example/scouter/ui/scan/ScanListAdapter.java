package com.example.scouter.ui.scan;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scouter.databinding.FragmentDeviceScanBinding;
import com.example.scouter.ui.scan.placeholder.DeviceScanItem;

import java.util.ArrayList;
import java.util.List;

public class ScanListAdapter extends RecyclerView.Adapter<ScanListAdapter.ViewHolder> {
    private final List<DeviceScanItem> scanItemList;

    public ScanListAdapter() {
        scanItemList = new ArrayList<>();
        scanItemList.add(new DeviceScanItem("Sample Device", 123));
        scanItemList.add(new DeviceScanItem("Sample Device2", 2345));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(FragmentDeviceScanBinding.inflate(layoutInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setScanItem(scanItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return scanItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView deviceName;
        private final TextView measuredValue;

        public ViewHolder(FragmentDeviceScanBinding binding) {
            super(binding.getRoot());
            deviceName = binding.itemDevice;
            measuredValue = binding.itemValue;
        }

        public void setScanItem(DeviceScanItem scanItem) {
            deviceName.setText(scanItem.getDeviceName());
            measuredValue.setText(String.valueOf(scanItem.getValue()));
        }
    }
}
