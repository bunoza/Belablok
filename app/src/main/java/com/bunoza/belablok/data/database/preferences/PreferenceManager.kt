package com.bunoza.belablok.data.database.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceManager(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Dealer")
    private val dataStore = context.dataStore

    val counterData: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_STRING_DATA]
        }

    suspend fun saveStringData(value: String) {
        dataStore.edit { preferences ->
            preferences[KEY_STRING_DATA] = value
        }
    }

    companion object {
        private val KEY_STRING_DATA = stringPreferencesKey("key_string_data")
    }
}
