package com.example.scouter.ui.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;

public class DirectoryPreference extends Preference {
    public DirectoryPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onClick() {
        Toast.makeText(getContext(), "Directory Preference", Toast.LENGTH_SHORT).show();
        super.onClick();
    }
}
