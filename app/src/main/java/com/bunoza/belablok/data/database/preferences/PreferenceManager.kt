package com.bunoza.belablok.data.database.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceManager(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Dealer")
    private val dataStore = context.dataStore

    val counterData: Flow<String?> = dataStore.data.map { preferences ->
        preferences[DEALER_KEY]
    }

    val isScreenEnabled: Flow<Boolean?> = dataStore.data.map { preferences ->
        preferences[ENABLED_SCREEN_KEY]
    }

    val targetScore: Flow<Int?> = dataStore.data.map { preferences ->
        preferences[TARGET_SCORE_KEY]
    }

    val gameDiff: Flow<Boolean?> = dataStore.data.map { preferences ->
        preferences[GAME_SCORE_DIFF]
    }

    val historyDiff: Flow<Boolean?> = dataStore.data.map { preferences ->
        preferences[HISTORY_SCORE_DIFF]
    }

    suspend fun saveStringData(value: String) {
        dataStore.edit { preferences ->
            preferences[DEALER_KEY] = value
        }
    }
    suspend fun saveEnabledScreen(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[ENABLED_SCREEN_KEY] = isEnabled
        }
    }

    suspend fun saveTargetScore(score: Int) {
        dataStore.edit { preferences ->
            preferences[TARGET_SCORE_KEY] = score
        }
    }

    suspend fun saveGameDiff(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[GAME_SCORE_DIFF] = isEnabled
        }
    }

    suspend fun saveHistoryDiff(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[HISTORY_SCORE_DIFF] = isEnabled
        }
    }

    companion object {
        private val DEALER_KEY = stringPreferencesKey("dealer_key")
        private val ENABLED_SCREEN_KEY = booleanPreferencesKey("enabled_screen_key")
        private val TARGET_SCORE_KEY = intPreferencesKey("target_score_key")
        private val GAME_SCORE_DIFF = booleanPreferencesKey("game_score_diff")
        private val HISTORY_SCORE_DIFF = booleanPreferencesKey("history_score_diff")
    }
}
