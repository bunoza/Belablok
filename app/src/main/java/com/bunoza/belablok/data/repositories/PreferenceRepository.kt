package com.bunoza.belablok.data.repositories

import com.bunoza.belablok.data.database.preferences.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PreferenceRepository(private val preferenceManager: PreferenceManager) {
    val dealer = preferenceManager.counterData

    suspend fun updateDealer(newDealer: String) {
        withContext(Dispatchers.IO) {
            preferenceManager.saveStringData(newDealer)
        }
    }
}
