package com.bunoza.belablok.ui.inputscorescreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import com.bunoza.belablok.data.database.model.Game
import com.bunoza.belablok.data.database.model.SingleGame
import com.bunoza.belablok.data.repositories.DatabaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class InputScoreViewModel(private val databaseRepository: DatabaseRepository,private var game: SingleGame?) : ViewModel() {
    val radioOptions = listOf("MI", "VI")
    val selectedOption = mutableStateOf(radioOptions[0])
    private val dealer= mutableStateOf("Ja")
    val firstPlayerPoints = mutableStateOf("")
    val secondPlayerPoints = mutableStateOf("")
    val isButtonEnabled = MutableStateFlow(false)
    val timesCalledUs = MutableStateFlow(0)
    val timesCalledThem = MutableStateFlow(0)
    private val afterBasePointsWe= mutableStateOf(0)
    private val afterBasePointsThem= mutableStateOf(0)
    val callTwentyUsState =
        MutableStateFlow(
            CallState(
                callValue = 20,
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
        )
    val callTwentyThemState =
        MutableStateFlow(
            CallState(
                callValue = 20,
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
        )
    val callFiftyUsState =
        MutableStateFlow(
            CallState(
                callValue = 50,
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
        )
    val callFiftyThemState =
        MutableStateFlow(
            CallState(
                callValue = 50,
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
        )
    val callHundredUsState =
        MutableStateFlow(
            CallState(
                callValue = 100,
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
        )
    val callHundredThemState =
        MutableStateFlow(
            CallState(
                callValue = 100,
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
        )
    val callBelotUsState = MutableStateFlow(
        CallState(
            callValue = 1000,
            timesCalled = 0,
            visibility = false,
            timesCalledVisibility = false
        )
    )
    val callBelotThemState = MutableStateFlow(
        CallState(
            callValue = 1000,
            timesCalled = 0,
            visibility = false,
            timesCalledVisibility = false
        )
    )
    init {
        if(game.isNotNull()){
            firstPlayerPoints.value=game?.baseGamePointsWe.toString()
            secondPlayerPoints.value=game?.baseGamePointsThem.toString()
            isButtonEnabled.value=true
            timesCalledUs.value= game?.accumulatedCallsWe!!
            timesCalledThem.value = game!!.accumulatedCallsThem
            callTwentyUsState.value=callTwentyUsState.value.copy(
                timesCalled = game!!.callTwentyWe,
                visibility = checkIfCallVisible(game!!.callTwentyWe),
                timesCalledVisibility = checkIfCallVisible(game!!.callTwentyWe)
            )
            callTwentyThemState.value=callTwentyThemState.value.copy(
                timesCalled = game!!.callTwentyThem,
                visibility = checkIfCallVisible(game!!.callTwentyThem),
                timesCalledVisibility = checkIfCallVisible(game!!.callTwentyThem)
            )
            callFiftyUsState.value=callFiftyUsState.value.copy(
                timesCalled = game!!.callFiftyWe,
                visibility = checkIfCallVisible(game!!.callFiftyWe),
                timesCalledVisibility = checkIfCallVisible(game!!.callFiftyWe)
            )
            callFiftyThemState.value = callFiftyThemState.value.copy(
                timesCalled = game!!.callFiftyThem,
                visibility = checkIfCallVisible(game!!.callFiftyThem),
                timesCalledVisibility = checkIfCallVisible(game!!.callFiftyThem)
            )
            callHundredUsState.value = callHundredUsState.value.copy(
                timesCalled = game!!.callHundredWe,
                visibility = checkIfCallVisible(game!!.callHundredWe),
                timesCalledVisibility = checkIfCallVisible(game!!.callHundredWe)
            )
            callHundredThemState.value=callHundredThemState.value.copy(
                timesCalled = game!!.callHundredThem,
                visibility = checkIfCallVisible(game!!.callHundredThem),
                timesCalledVisibility = checkIfCallVisible(game!!.callHundredThem)
            )
            callBelotUsState.value = callBelotUsState.value.copy(
                timesCalled = game!!.callBelotWe,
                visibility = checkIfCallVisible(game!!.callBelotWe),
                timesCalledVisibility = checkIfCallVisible(game!!.callBelotWe)
            )
            callBelotThemState.value = callBelotThemState.value.copy(
                timesCalled = game!!.callBelotThem,
                visibility = checkIfCallVisible(game!!.callBelotThem),
                timesCalledVisibility = checkIfCallVisible(game!!.callBelotThem)
            )
        }
    }


    fun onTwentyCallUsClick() {
        viewModelScope.launch {
            callTwentyUsState.value = callTwentyUsState.value.copy(
                timesCalled = callTwentyUsState.value.timesCalled + 1,
                visibility = true,
                timesCalledVisibility = true
            )
            timesCalledUs.value = timesCalledUs.value + 20
        }
    }

    fun onTwentyCallMinusUsClick() {
        viewModelScope.launch {
            if (callTwentyUsState.value.timesCalled > 1) {
                callTwentyUsState.value = callTwentyUsState.value.copy(
                    timesCalled = callTwentyUsState.value.timesCalled - 1
                )
                timesCalledUs.value = timesCalledUs.value - 20
            } else if (callTwentyUsState.value.timesCalled == 1) {
                callTwentyUsState.value = callTwentyUsState.value.copy(
                    timesCalled = callTwentyUsState.value.timesCalled - 1,
                    visibility = false,
                    timesCalledVisibility = false
                )
                timesCalledUs.value = timesCalledUs.value - 20
            }
        }
    }

    fun onTwentyCallThemClick() {
        viewModelScope.launch {
            callTwentyThemState.value = callTwentyThemState.value.copy(
                timesCalled = callTwentyThemState.value.timesCalled + 1,
                visibility = true,
                timesCalledVisibility = true
            )
            timesCalledThem.value = timesCalledThem.value + 20
        }
    }

    fun onTwentyCallMinusThemClick() {
        viewModelScope.launch {
            if (callTwentyThemState.value.timesCalled > 1) {
                callTwentyThemState.value = callTwentyThemState.value.copy(
                    timesCalled = callTwentyThemState.value.timesCalled - 1
                )
                timesCalledThem.value = timesCalledThem.value - 20
            } else if (callTwentyUsState.value.timesCalled == 1) {
                callTwentyThemState.value = callTwentyThemState.value.copy(
                    timesCalled = callTwentyThemState.value.timesCalled - 1,
                    visibility = false,
                    timesCalledVisibility = false
                )
                timesCalledThem.value = timesCalledThem.value - 20
            }
        }
    }

    fun onFiftyCallUsClick() {
        viewModelScope.launch {
            callFiftyUsState.value = callFiftyUsState.value.copy(
                timesCalled = callFiftyUsState.value.timesCalled + 1,
                visibility = true,
                timesCalledVisibility = true
            )
            timesCalledUs.value = timesCalledUs.value + 50
        }
    }

    fun onFiftyCallMinusUsClick() {
        viewModelScope.launch {
            if (callFiftyUsState.value.timesCalled > 1) {
                callFiftyUsState.value = callFiftyUsState.value.copy(
                    timesCalled = callFiftyUsState.value.timesCalled - 1
                )
                timesCalledUs.value = timesCalledUs.value - 50
            } else if (callFiftyUsState.value.timesCalled == 1) {
                callFiftyUsState.value = callFiftyUsState.value.copy(
                    timesCalled = callFiftyUsState.value.timesCalled - 1,
                    visibility = false,
                    timesCalledVisibility = false
                )
                timesCalledUs.value = timesCalledUs.value - 50
            }
        }
    }

    fun onFiftyCallThemClick() {
        viewModelScope.launch {
            callFiftyThemState.value = callFiftyThemState.value.copy(
                timesCalled = callFiftyThemState.value.timesCalled + 1,
                visibility = true,
                timesCalledVisibility = true
            )
            timesCalledThem.value = timesCalledThem.value + 50
        }
    }

    fun onFiftyCallMinusThemClick() {
        viewModelScope.launch {
            if (callFiftyThemState.value.timesCalled > 1) {
                callFiftyThemState.value = callFiftyThemState.value.copy(
                    timesCalled = callFiftyThemState.value.timesCalled - 1
                )
                timesCalledThem.value = timesCalledThem.value - 50
            } else if (callFiftyThemState.value.timesCalled == 1) {
                callFiftyThemState.value = callFiftyThemState.value.copy(
                    timesCalled = callFiftyThemState.value.timesCalled - 1,
                    visibility = false,
                    timesCalledVisibility = false
                )
                timesCalledThem.value = timesCalledThem.value - 50
            }
        }
    }

    fun onHundredCallUsClick() {
        viewModelScope.launch {
            callHundredUsState.value = callHundredUsState.value.copy(
                timesCalled = callHundredUsState.value.timesCalled + 1,
                visibility = true,
                timesCalledVisibility = true
            )
            timesCalledUs.value = timesCalledUs.value + 100
        }
    }

    fun onHundredCallMinusUsClick() {
        viewModelScope.launch {
            if (callHundredUsState.value.timesCalled > 1) {
                callHundredUsState.value = callHundredUsState.value.copy(
                    timesCalled = callHundredUsState.value.timesCalled - 1
                )
                timesCalledUs.value = timesCalledUs.value - 100
            } else if (callHundredUsState.value.timesCalled == 1) {
                callHundredUsState.value = callHundredUsState.value.copy(
                    timesCalled = callHundredUsState.value.timesCalled - 1,
                    visibility = false,
                    timesCalledVisibility = false
                )
                timesCalledUs.value = timesCalledUs.value - 100
            }
        }
    }

    fun onHundredCallThemClick() {
        viewModelScope.launch {
            callHundredThemState.value = callHundredThemState.value.copy(
                timesCalled = callHundredThemState.value.timesCalled + 1,
                visibility = true,
                timesCalledVisibility = true
            )
            timesCalledThem.value = timesCalledThem.value + 100
        }
    }

    fun onHundredCallMinusThemClick() {
        viewModelScope.launch {
            if (callHundredThemState.value.timesCalled > 1) {
                callHundredThemState.value = callHundredThemState.value.copy(
                    timesCalled = callHundredThemState.value.timesCalled - 1
                )
                timesCalledThem.value = timesCalledThem.value - 100
            } else if (callHundredThemState.value.timesCalled == 1) {
                callHundredThemState.value = callHundredThemState.value.copy(
                    timesCalled = callHundredThemState.value.timesCalled - 1,
                    visibility = false,
                    timesCalledVisibility = false
                )
                timesCalledThem.value = timesCalledThem.value - 100
            }
        }
    }

    fun onBelotCallUsMinusClick() {
        viewModelScope.launch {
            if (callBelotUsState.value.timesCalled > 1) {
                callBelotUsState.value = callBelotUsState.value.copy(
                    timesCalled = callBelotUsState.value.timesCalled - 1
                )
                timesCalledUs.value = timesCalledUs.value - 1000
            } else if (callBelotUsState.value.timesCalled == 1) {
                callBelotUsState.value = callBelotUsState.value.copy(
                    timesCalled = callBelotUsState.value.timesCalled - 1,
                    visibility = false,
                    timesCalledVisibility = false
                )
                timesCalledUs.value = timesCalledUs.value - 1000
            }
        }
    }

    fun onBelotCallThemMinusClick() {
        viewModelScope.launch {
            if (callBelotThemState.value.timesCalled > 1) {
                callBelotThemState.value = callBelotThemState.value.copy(
                    timesCalled = callBelotThemState.value.timesCalled - 1
                )
                timesCalledThem.value = timesCalledThem.value - 1000
            } else if (callBelotThemState.value.timesCalled == 1) {
                callBelotThemState.value = callBelotThemState.value.copy(
                    timesCalled = callBelotThemState.value.timesCalled - 1,
                    visibility = false,
                    timesCalledVisibility = false
                )
                timesCalledThem.value = timesCalledThem.value - 1000
            }
        }
    }

    fun onBelotCallUsClick() {
        viewModelScope.launch {
            if (callBelotUsState.value.timesCalled < 2) {
                callBelotUsState.value = callBelotUsState.value.copy(
                    timesCalled = callBelotUsState.value.timesCalled + 1,
                    visibility = true,
                    timesCalledVisibility = true
                )
                timesCalledUs.value = timesCalledUs.value + 1000
            }
        }
    }

    fun onBelotCallThemClick() {
        viewModelScope.launch {
            if (callBelotThemState.value.timesCalled < 2) {
                callBelotThemState.value = callBelotThemState.value.copy(
                    timesCalled = callBelotThemState.value.timesCalled + 1,
                    visibility = true,
                    timesCalledVisibility = true
                )
                timesCalledThem.value = timesCalledThem.value + 1000
            }
        }
    }

    fun onDeleteCallsClick() {
        viewModelScope.launch {
            callTwentyUsState.value = callTwentyUsState.value.copy(
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
            callTwentyThemState.value = callTwentyThemState.value.copy(
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
            callFiftyUsState.value = callFiftyUsState.value.copy(
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
            callFiftyThemState.value = callFiftyThemState.value.copy(
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
            callHundredUsState.value = callHundredUsState.value.copy(
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
            callHundredThemState.value = callHundredThemState.value.copy(
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
            callBelotUsState.value = callBelotUsState.value.copy(
                timesCalledVisibility = false,
                visibility = false,
                timesCalled = 0
            )
            callBelotThemState.value = callBelotThemState.value.copy(
                timesCalled = 0,
                visibility = false,
                timesCalledVisibility = false
            )
            timesCalledUs.value = 0
            timesCalledThem.value = 0
        }
    }

    fun onFirstInputChange(input: String) {
        viewModelScope.launch {
            isButtonEnabled.value = true
            if (input.isBlank() || input.matches(Regex("^(0|[1-9][0-9]*)$"))) {
                firstPlayerPoints.value = ""
                secondPlayerPoints.value = "162"
            }
            if (input.isNotBlank() && input.matches(Regex("^(0|[1-9][0-9]*)$"))) {
                if (input.toInt() < 0 || input.toInt() > 162) {
                    firstPlayerPoints.value = input.dropLast(1)
                } else {
                    secondPlayerPoints.value = (162 - input.toInt()).toString()
                    firstPlayerPoints.value = input
                }
            }
        }
    }

    fun onSecondInputChange(input: String) {
        viewModelScope.launch {
            isButtonEnabled.value = true
            if (input.isBlank() || input.matches(Regex("^(0|[1-9][0-9]*)$"))) {
                secondPlayerPoints.value = ""
                firstPlayerPoints.value = "162"
            }
            if (input.isNotBlank()&& input.matches(Regex("^(0|[1-9][0-9]*)$"))) {
                if (input.toInt() < 0 || input.toInt() > 162) {
                    secondPlayerPoints.value = input.dropLast(1)
                } else {
                    firstPlayerPoints.value = (162 - input.toInt()).toString()
                    secondPlayerPoints.value = input
                }
            }
        }
    }

    private fun insertSingleGame(
        basePointsWe: Int,
        basePointsThem: Int,
        callTwentyWe: Int,
        callTwentyThem: Int,
        callFiftyWe: Int,
        callFiftyThem: Int,
        callHundredWe: Int,
        callHundredThem: Int,
        callBelotWe: Int,
        callBelotThem: Int,
        totalScoreWe: Int,
        totalScoreThem: Int,
        afterBasePointsWe:Int,
        afterBasePointsThem:Int,
        dealer:String
    ) {
        viewModelScope.launch {
            if(game.isNotNull()){
                game= game?.copy(
                    baseGamePointsWe = basePointsWe,
                    baseGamePointsThem = basePointsThem,
                    callTwentyWe = callTwentyWe,
                    callTwentyThem = callTwentyThem,
                    callFiftyWe = callFiftyWe,
                    callFiftyThem = callFiftyThem,
                    callHundredWe = callHundredWe,
                    callHundredThem = callHundredThem,
                    callBelotWe = callBelotWe,
                    callBelotThem = callBelotThem,
                    scoreWe = totalScoreWe,
                    scoreThem = totalScoreThem,
                    afterBasePointsWe = afterBasePointsWe,
                    afterBasePointsThem = afterBasePointsThem,
                    dealer = dealer

                )
                databaseRepository.updateSingleGame(
                    game!!
                )

            }else{
                val singleGame=SingleGame(
                    baseGamePointsWe = basePointsWe,
                    baseGamePointsThem = basePointsThem,
                    callTwentyWe = callTwentyWe,
                    callTwentyThem = callTwentyThem,
                    callFiftyWe = callFiftyWe,
                    callFiftyThem = callFiftyThem,
                    callHundredWe = callHundredWe,
                    callHundredThem = callHundredThem,
                    callBelotWe = callBelotWe,
                    callBelotThem = callBelotThem,
                    scoreWe = totalScoreWe,
                    scoreThem = totalScoreThem,
                    afterBasePointsWe = afterBasePointsWe,
                    afterBasePointsThem = afterBasePointsThem,
                    dealer = dealer
                )
                databaseRepository.insertSingleGame(
                    singleGame
                )
            }

        }
    }

    fun onSaveGameClick() {
        viewModelScope.launch {
            var firstPlayerTotalPoints =
                firstPlayerPoints.value.toInt() + callTwentyUsState.value.callValue * callTwentyUsState.value.timesCalled + callFiftyUsState.value.callValue * callFiftyUsState.value.timesCalled + callHundredUsState.value.callValue * callHundredUsState.value.timesCalled + callBelotUsState.value.callValue * callBelotUsState.value.timesCalled
            var secondPlayerTotalPoints =
                secondPlayerPoints.value.toInt() + callTwentyThemState.value.callValue * callTwentyThemState.value.timesCalled + callFiftyThemState.value.timesCalled * callFiftyThemState.value.callValue + callHundredThemState.value.callValue * callHundredThemState.value.timesCalled + callBelotThemState.value.timesCalled * callBelotThemState.value.callValue
            if (selectedOption.value == radioOptions[0]) {
                if (secondPlayerTotalPoints > firstPlayerTotalPoints) {
                    firstPlayerTotalPoints = 0
                    secondPlayerTotalPoints =
                        162 + callTwentyUsState.value.callValue * callTwentyUsState.value.timesCalled + callFiftyUsState.value.callValue * callFiftyUsState.value.timesCalled + callHundredUsState.value.callValue * callHundredUsState.value.timesCalled + callBelotUsState.value.callValue * callBelotUsState.value.timesCalled + callTwentyThemState.value.callValue * callTwentyThemState.value.timesCalled + callFiftyThemState.value.timesCalled * callFiftyThemState.value.callValue + callHundredThemState.value.callValue * callHundredThemState.value.timesCalled + callBelotThemState.value.timesCalled * callBelotThemState.value.callValue
                    afterBasePointsWe.value=0
                    afterBasePointsThem.value=162
                }else{
                    afterBasePointsWe.value=firstPlayerPoints.value.toInt()
                    afterBasePointsThem.value=secondPlayerPoints.value.toInt()
                }
            } else {
                if (firstPlayerTotalPoints > secondPlayerTotalPoints) {
                    secondPlayerTotalPoints = 0
                    firstPlayerTotalPoints =
                        162 + callTwentyThemState.value.callValue * callTwentyThemState.value.timesCalled + callFiftyThemState.value.timesCalled * callFiftyThemState.value.callValue + callHundredThemState.value.callValue * callHundredThemState.value.timesCalled + callBelotThemState.value.timesCalled * callBelotThemState.value.callValue + callTwentyUsState.value.callValue * callTwentyUsState.value.timesCalled + callFiftyUsState.value.callValue * callFiftyUsState.value.timesCalled + callHundredUsState.value.callValue * callHundredUsState.value.timesCalled + callBelotUsState.value.callValue * callBelotUsState.value.timesCalled
                    afterBasePointsThem.value=0
                    afterBasePointsWe.value=162
                }else{
                    afterBasePointsWe.value=firstPlayerPoints.value.toInt()
                    afterBasePointsThem.value=secondPlayerPoints.value.toInt()
                }
            }
            insertSingleGame(
                basePointsWe = firstPlayerPoints.value.toInt(),
                basePointsThem = secondPlayerPoints.value.toInt(),
                callTwentyWe = callTwentyUsState.value.timesCalled,
                callTwentyThem = callTwentyThemState.value.timesCalled,
                callFiftyWe = callFiftyUsState.value.timesCalled,
                callFiftyThem = callFiftyThemState.value.timesCalled,
                callHundredWe = callHundredUsState.value.timesCalled,
                callHundredThem = callHundredThemState.value.timesCalled,
                callBelotWe = callBelotUsState.value.timesCalled,
                callBelotThem = callBelotThemState.value.timesCalled,
                totalScoreWe = firstPlayerTotalPoints,
                totalScoreThem = secondPlayerTotalPoints,
                afterBasePointsWe = afterBasePointsWe.value,
                afterBasePointsThem = afterBasePointsThem.value,
                dealer=dealer.value
            )
        }
    }

    fun onRadioButtonClick(clickedOption: String) {
        viewModelScope.launch {
            selectedOption.value = clickedOption
        }
    }
    fun setDealer(input:String){
        dealer.value=input
    }
    fun checkIfCallVisible(timesCalled:Int):Boolean{
        return timesCalled>0
    }
}
