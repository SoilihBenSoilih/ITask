package com.ss.itask

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.ss.itask.R


class SettingsActivityFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }
}