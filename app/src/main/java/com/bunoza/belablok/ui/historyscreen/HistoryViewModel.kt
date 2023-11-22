package com.bunoza.belablok.ui.historyscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunoza.belablok.data.repositories.DatabaseRepository
import com.bunoza.belablok.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val databaseRepository: DatabaseRepository) : ViewModel() {
    private var _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAllGames()
    }

    fun getAllGames() {
        viewModelScope.launch {
            try {
                databaseRepository.getAllGames().collect {
                    if (it.isEmpty()) {
                        _uiState.value = UIState.EmptyListState
                    } else {
                        _uiState.value = UIState.Success(it)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error
            }
        }
    }
}
