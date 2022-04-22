package com.svalero.tripalbum.view;

import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.svalero.tripalbum.R;

public class PreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
