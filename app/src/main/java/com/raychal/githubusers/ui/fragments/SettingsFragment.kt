package com.raychal.githubusers.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.raychal.githubusers.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsFragment private constructor(private val dataStore: DataStore<Preferences>): PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map {
            preferences -> preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = context?.getString(R.string.settings)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingsFragment? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingsFragment {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingsFragment(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}