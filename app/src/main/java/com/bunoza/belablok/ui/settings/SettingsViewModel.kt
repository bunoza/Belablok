package com.bunoza.belablok.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunoza.belablok.data.repositories.DatabaseRepository
import com.bunoza.belablok.data.repositories.PreferenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val preferenceRepository: PreferenceRepository, private val databaseRepository: DatabaseRepository) : ViewModel() {

    val isScreenEnabled = preferenceRepository.isScreenEnabled.filterNotNull().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    val targetScore = preferenceRepository.targetScore.filterNotNull().map {
        when (it) {
            501 -> 0
            701 -> 1
            1001 -> 2
            else -> null
        }
    }.filterNotNull().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 2
    )

    val isGameDiffEnabled = preferenceRepository.isGameDiffEnabled.filterNotNull().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    val isHistoryDiffEnabled = preferenceRepository.isHistoryDiffEnabled.filterNotNull().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    fun updateScreenSetting(newState: Boolean) {
        viewModelScope.launch {
            preferenceRepository.updateScreenSetting(newState)
        }
    }

    fun updateTargetScore(targetScore: String) {
        viewModelScope.launch {
            val score = targetScore.toIntOrNull() ?: return@launch
            preferenceRepository.updateTargetScore(score)
        }
    }

    fun updateGameScoreDiff(isEnabled: Boolean) {
        viewModelScope.launch {
            preferenceRepository.updateGameScoreDiff(isEnabled)
        }
    }

    fun updateHistoryScoreDiff(isEnabled: Boolean) {
        viewModelScope.launch {
            preferenceRepository.updateHistoryScoreDiff(isEnabled)
        }
    }

    fun deleteCurrentGame() {
        viewModelScope.launch {
            databaseRepository.deleteAllSingleGames()
        }
    }

    fun deleteHistory() {
        viewModelScope.launch {
            databaseRepository.deleteAllGames()
        }
    }
}
