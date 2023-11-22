package com.bunoza.belablok.ui

sealed interface UIState {
    object Loading : UIState
    object Error : UIState
    data class Success<T>(val data: T) : UIState
    object EmptyListState : UIState
}
