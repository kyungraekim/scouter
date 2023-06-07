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

    public void updateList(List<DeviceScanItem> deviceScanItems) {
        scanItemList.clear();
        scanItemList.addAll(deviceScanItems);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView deviceName;
        private final TextView measuredValue;
        private final TextView address;
        private final TextView interval;

        public ViewHolder(FragmentDeviceScanBinding binding) {
            super(binding.getRoot());
            deviceName = binding.itemDevice;
            measuredValue = binding.itemValue;
            address = binding.itemAddress;
            interval = binding.itemInterval;
        }

        public void setScanItem(DeviceScanItem scanItem) {
            deviceName.setText(scanItem.getDeviceName());
            measuredValue.setText(String.valueOf(scanItem.getValue()));
            address.setText(scanItem.getAddress());
            interval.setText(String.valueOf(scanItem.getInterval()));
        }
    }
}
