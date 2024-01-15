package com.bunoza.belablok.ui.scorescreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunoza.belablok.data.database.model.Game
import com.bunoza.belablok.data.database.model.SingleGame
import com.bunoza.belablok.data.repositories.DatabaseRepository
import com.bunoza.belablok.data.repositories.PreferenceRepository
import com.bunoza.belablok.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ScoreScreenViewModel(private val databaseRepository: DatabaseRepository, private val preferenceRepository: PreferenceRepository) : ViewModel() {
    private var _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _totalWeScore = MutableStateFlow(0)
    private val _totalThemScore = MutableStateFlow(0)
    val totalWeScore = _totalWeScore.asStateFlow()
    val totalThemScore = _totalThemScore.asStateFlow()
    private val counter = MutableStateFlow(0)
    val dealerPossibilities = listOf("Ja", "Desni protivnik", "Partner", "Lijevi protivnik")
    var dealer = MutableStateFlow(dealerPossibilities[counter.value])
    var whoWon = mutableStateOf("")
    var isAlertDialogOpened = mutableStateOf(false)
    var shouldNavigate = mutableStateOf(true)
    private var gameList = listOf<SingleGame>()

    init {
        getAllSingleGames()
        getDealer()
    }

    fun getAllSingleGames() {
        viewModelScope.launch {
            try {
                databaseRepository.getAllSingleGames().distinctUntilChanged().collect {
                    if (it.isEmpty()) {
                        _uiState.value = UIState.EmptyListState
                        _totalWeScore.value = 0
                        _totalThemScore.value = 0
                    } else {
                        _uiState.value = UIState.Success(it)
                        gameList = it
                        _totalWeScore.value = getTotalScoreWe(it)
                        _totalThemScore.value = getTotalScoreThem(it)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error
            }
        }
    }

    private fun getTotalScoreWe(list: List<SingleGame>): Int {
        var sum = 0
        list.forEach {
            sum += it.scoreWe
        }
        return sum
    }

    private fun getTotalScoreThem(list: List<SingleGame>): Int {
        var sum = 0
        list.forEach {
            sum += it.scoreThem
        }
        return sum
    }

    fun getDealer() {
        viewModelScope.launch {
            preferenceRepository.dealer.collect {
                if (it != null) {
                    setCounter(it)
                    dealer.value = dealerPossibilities[counter.value]
                }
            }
        }
    }

    fun updateDealer(dealer: String) {
        viewModelScope.launch {
            preferenceRepository.updateDealer(dealerPossibilities[counter.value])
        }
    }

    fun onDealerCounterClick(selectedOption: String) {
        viewModelScope.launch {
            dealer.value = selectedOption
            setCounter(selectedOption)
        }
    }

    fun checkNewGame() {
        viewModelScope.launch {
            if (totalWeScore.value > 1000 && totalThemScore.value > 1000) {
                if (totalWeScore.value > totalThemScore.value) {
                    whoWon.value = "MI"
                    updateCounterWeWin()
                } else {
                    whoWon.value = "VI"
                    updateCounterTheyWin()
                }
                shouldNavigate.value = false
                isAlertDialogOpened.value = true
            } else if (totalWeScore.value > 1000) {
                whoWon.value = "MI"
                shouldNavigate.value = false
                isAlertDialogOpened.value = true
                updateCounterWeWin()
            } else if (totalThemScore.value > 1000) {
                whoWon.value = "VI"
                shouldNavigate.value = false
                isAlertDialogOpened.value = true
                updateCounterTheyWin()
            } else {
                if (counter.value == 3) {
                    counter.value = 0
                } else {
                    counter.value++
                }
                updateDealer(dealer.value)
            }
        }
    }

    fun deleteAllGames() {
        viewModelScope.launch {
            insertWholeGame(gameList)
            databaseRepository.deleteAllSingleGames()
        }
    }
    fun resetNavigation() {
        shouldNavigate.value = true
        isAlertDialogOpened.value = false
    }
    private fun insertWholeGame(gameList: List<SingleGame>) {
        viewModelScope.launch {
            databaseRepository.insertWholeGame(Game(singleGameList = gameList))
        }
    }
    private fun setCounter(selectedOption: String) {
        when (selectedOption) {
            dealerPossibilities[0] -> counter.value = 0
            dealerPossibilities[1] -> counter.value = 1
            dealerPossibilities[2] -> counter.value = 2
            dealerPossibilities[3] -> counter.value = 3
        }
    }
    private fun updateCounterWeWin() {
        when (counter.value) {
            0 -> counter.value = 0
            1 -> counter.value = 2
            2 -> counter.value = 2
            3 -> counter.value = 0
        }
    }
    private fun updateCounterTheyWin() {
        when (counter.value) {
            0 -> counter.value = 1
            1 -> counter.value = 1
            2 -> counter.value = 3
            3 -> counter.value = 3
        }
    }
}
