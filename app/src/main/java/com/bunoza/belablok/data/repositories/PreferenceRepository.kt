package com.bunoza.belablok.data.repositories

import com.bunoza.belablok.data.database.preferences.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PreferenceRepository(private val preferenceManager: PreferenceManager) {
    // val dealer = preferenceManager.counterData

    val targetScore = preferenceManager.targetScore
    val isScreenEnabled = preferenceManager.isScreenEnabled
    val isGameDiffEnabled = preferenceManager.gameDiff
    val isHistoryDiffEnabled = preferenceManager.historyDiff

    fun getDealer(): Flow<String?> {
        return preferenceManager.counterData
    }

    suspend fun updateDealer(newDealer: String) {
        withContext(Dispatchers.IO) {
            preferenceManager.saveStringData(newDealer)
        }
    }

    suspend fun updateTargetScore(targetScore: Int) {
        withContext(Dispatchers.IO) {
            preferenceManager.saveTargetScore(targetScore)
        }
    }

    suspend fun updateScreenSetting(isEnabled: Boolean) {
        withContext(Dispatchers.IO) {
            preferenceManager.saveEnabledScreen(isEnabled)
        }
    }

    suspend fun updateGameScoreDiff(isEnabled: Boolean) {
        withContext(Dispatchers.IO) {
            preferenceManager.saveGameDiff(isEnabled)
        }
    }

    suspend fun updateHistoryScoreDiff(isEnabled: Boolean) {
        withContext(Dispatchers.IO) {
            preferenceManager.saveHistoryDiff(isEnabled)
        }
    }
}
