package com.bunoza.belablok.ui.scorescreen

import com.bunoza.belablok.data.database.model.SingleGame

sealed interface ScoreScreenUIState {
    object Loading : ScoreScreenUIState
    object Error : ScoreScreenUIState
    data class Success(val data: List<SingleGame>) : ScoreScreenUIState
}
