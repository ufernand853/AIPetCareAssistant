package com.example.aipetcareassistant.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsStore(private val context: Context) {
    private val showApiHostKey = booleanPreferencesKey("show_api_host")

    val showApiHost: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[showApiHostKey] ?: false }

    suspend fun setShowApiHost(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[showApiHostKey] = enabled
        }
    }
}
