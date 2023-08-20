package com.example.scouter.ui.settings;

import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.scouter.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    private SwitchPreferenceCompat bleSwitch;
    private SwitchPreferenceCompat bluetoothSwitch;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        DirectoryPreference logPreference = findPreference(getString(R.string.setting_log_path));
        bleSwitch = findPreference(getString(R.string.setting_scan_ble));
        bluetoothSwitch = findPreference(getString(R.string.setting_scan_bl));
        if (logPreference == null || bleSwitch == null || bluetoothSwitch == null) {
            Toast.makeText(getContext(), "Failed to create preference page", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        logPreference.registerActivity(this);
        bleSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean isChecked = (boolean) newValue;
            bluetoothSwitch.setChecked(!isChecked);
            return true;
        });
        bluetoothSwitch.setOnPreferenceChangeListener(((preference, newValue) -> {
            boolean isChecked = (boolean) newValue;
            bleSwitch.setChecked(!isChecked);
            return true;
        }));
    }
}
