package com.bunoza.belablok.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunoza.belablok.data.repositories.PreferenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainViewModel(preferenceRepository: PreferenceRepository) : ViewModel() {
    val keepScreenOn = preferenceRepository.isScreenEnabled.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )
}
