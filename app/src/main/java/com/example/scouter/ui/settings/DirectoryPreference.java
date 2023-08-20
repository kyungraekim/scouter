package com.example.scouter.ui.settings;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;

public class DirectoryPreference extends Preference {
    private ActivityResultLauncher<Uri> directoryPicker;

    public DirectoryPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void registerActivity(Fragment fragment) {
        directoryPicker = fragment.registerForActivityResult(
                new ActivityResultContracts.OpenDocumentTree(),
                uri -> {
                    persistString(uri.getPath());
                    setSummary(uri.getPath());
                });
    }

    @Override
    protected void onClick() {
        directoryPicker.launch(Uri.parse(""));
        super.onClick();
    }
}
