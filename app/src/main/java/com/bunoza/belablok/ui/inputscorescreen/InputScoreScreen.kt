package com.bunoza.belablok.ui.inputscorescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bunoza.belablok.data.database.model.SingleGame
import com.bunoza.belablok.ui.destinations.InputScoreScreenDestination
import com.bunoza.belablok.ui.destinations.ScoreScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun InputScoreScreen(navigator: DestinationsNavigator, dealer: String, singleGame: SingleGame?) {
    val inputScoreViewModel = koinViewModel<InputScoreViewModel>() { parametersOf(singleGame) }
    inputScoreViewModel.setDealer(dealer)
    val collectedSaveButton = inputScoreViewModel.isButtonEnabled.collectAsState()
    val collectedCallTwentyUsState = inputScoreViewModel.callTwentyUsState.collectAsState()
    val collectedCallTwentyThemState = inputScoreViewModel.callTwentyThemState.collectAsState()
    val collectedCallFiftyUsState = inputScoreViewModel.callFiftyUsState.collectAsState()
    val collectedCallFiftyThemState = inputScoreViewModel.callFiftyThemState.collectAsState()
    val collectedCallHundredUsState = inputScoreViewModel.callHundredUsState.collectAsState()
    val collectedCallHundredThemState = inputScoreViewModel.callHundredThemState.collectAsState()
    val collectedCallBelotUsState = inputScoreViewModel.callBelotUsState.collectAsState()
    val collectedCallBelotThemState = inputScoreViewModel.callBelotThemState.collectAsState()
    val collectedTimesCalledUsState = inputScoreViewModel.timesCalledUs.collectAsState()
    val collectedTimesCalledThemState = inputScoreViewModel.timesCalledThem.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            InputScoreComposable(
                pointsValue = inputScoreViewModel.firstPlayerPoints.value,
                onPointsChanged = { inputScoreViewModel.onFirstInputChange(it) }
            )
            InputScoreComposable(
                pointsValue = inputScoreViewModel.secondPlayerPoints.value,
                onPointsChanged = { inputScoreViewModel.onSecondInputChange(it) }
            )
        }
        CallsComposable(collectedTimesCalledUsState = collectedTimesCalledUsState.value, collectedTimesCalledThemState = collectedTimesCalledThemState.value) {
            inputScoreViewModel.onDeleteCallsClick()
        }
        CallRowComposable(
            callValue = " ${collectedCallTwentyUsState.value.callValue} ",
            iconButtonVisibilityFirst = collectedCallTwentyUsState.value.visibility,
            iconButtonVisibilitySecond = collectedCallTwentyThemState.value.visibility,
            timesCalledVisibilityFirst = collectedCallTwentyUsState.value.timesCalledVisibility,
            timesCalledVisibilitySecond = collectedCallTwentyThemState.value.timesCalledVisibility,
            timesCalledValueFirst = collectedCallTwentyUsState.value.timesCalled,
            timesCalledValueSecond = collectedCallTwentyThemState.value.timesCalled,
            onFirstButtonClick = { inputScoreViewModel.onTwentyCallUsClick() },
            onSecondButtonClick = { inputScoreViewModel.onTwentyCallThemClick() },
            onFirstIconButtonClick = { inputScoreViewModel.onTwentyCallMinusUsClick() },
            onSecondIconButtonClick = { inputScoreViewModel.onTwentyCallMinusThemClick() }
        )
        CallRowComposable(
            callValue = " ${collectedCallFiftyThemState.value.callValue} ",
            iconButtonVisibilityFirst = collectedCallFiftyUsState.value.visibility,
            iconButtonVisibilitySecond = collectedCallFiftyThemState.value.visibility,
            timesCalledVisibilityFirst = collectedCallFiftyUsState.value.timesCalledVisibility,
            timesCalledVisibilitySecond = collectedCallFiftyThemState.value.timesCalledVisibility,
            timesCalledValueFirst = collectedCallFiftyUsState.value.timesCalled,
            timesCalledValueSecond = collectedCallFiftyThemState.value.timesCalled,
            onFirstButtonClick = { inputScoreViewModel.onFiftyCallUsClick() },
            onSecondButtonClick = { inputScoreViewModel.onFiftyCallThemClick() },
            onFirstIconButtonClick = { inputScoreViewModel.onFiftyCallMinusUsClick() },
            onSecondIconButtonClick = { inputScoreViewModel.onFiftyCallMinusThemClick() }
        )
        CallRowComposable(
            callValue = "${collectedCallHundredUsState.value.callValue}",
            iconButtonVisibilityFirst = collectedCallHundredUsState.value.visibility,
            iconButtonVisibilitySecond = collectedCallHundredThemState.value.visibility,
            timesCalledVisibilityFirst = collectedCallHundredUsState.value.timesCalledVisibility,
            timesCalledVisibilitySecond = collectedCallHundredThemState.value.timesCalledVisibility,
            timesCalledValueFirst = collectedCallHundredUsState.value.timesCalled,
            timesCalledValueSecond = collectedCallHundredThemState.value.timesCalled,
            onFirstButtonClick = { inputScoreViewModel.onHundredCallUsClick() },
            onSecondButtonClick = { inputScoreViewModel.onHundredCallThemClick() },
            onFirstIconButtonClick = { inputScoreViewModel.onHundredCallMinusUsClick() },
            onSecondIconButtonClick = { inputScoreViewModel.onHundredCallMinusThemClick() }
        )
        CallRowComposable(
            callValue = "${collectedCallBelotUsState.value.callValue}",
            iconButtonVisibilityFirst = collectedCallBelotUsState.value.visibility,
            iconButtonVisibilitySecond = collectedCallBelotThemState.value.visibility,
            timesCalledVisibilityFirst = collectedCallBelotUsState.value.timesCalledVisibility,
            timesCalledVisibilitySecond = collectedCallBelotThemState.value.timesCalledVisibility,
            timesCalledValueFirst = collectedCallBelotUsState.value.timesCalled,
            timesCalledValueSecond = collectedCallBelotThemState.value.timesCalled,
            onFirstButtonClick = { inputScoreViewModel.onBelotCallUsClick() },
            onSecondButtonClick = { inputScoreViewModel.onBelotCallThemClick() },
            onFirstIconButtonClick = { inputScoreViewModel.onBelotCallUsMinusClick() },
            onSecondIconButtonClick = { inputScoreViewModel.onBelotCallThemMinusClick() }
        )

        Text(
            text = "Tko je zvao:",
            color = MaterialTheme.colorScheme.background,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CallRadioButtonComposable(
                isSelected = (inputScoreViewModel.radioOptions[0] == inputScoreViewModel.selectedOption.value),
                onRadioButtonClick = {
                    inputScoreViewModel.onRadioButtonClick(inputScoreViewModel.radioOptions[0])
                },
                radioText = inputScoreViewModel.radioOptions[0]
            )
            CallRadioButtonComposable(
                isSelected = (inputScoreViewModel.radioOptions[1] == inputScoreViewModel.selectedOption.value),
                onRadioButtonClick = {
                    inputScoreViewModel.onRadioButtonClick(inputScoreViewModel.radioOptions[1])
                },
                radioText = inputScoreViewModel.radioOptions[1]
            )
        }
        Spacer(modifier = Modifier.weight(1F))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(
                onClick = {
                    navigator.navigateUp()
                    /*navigator.navigate(ScoreScreenDestination) {
                        popUpTo(InputScoreScreenDestination) {
                            inclusive = true
                        }
                    }*/
                          },
                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.background)
            ) {
                Text(text = "Odustani")
            }
            Button(
                onClick = {
                    inputScoreViewModel.onSaveGameClick()
                    /*navigator.navigate(ScoreScreenDestination) {
                        popUpTo(InputScoreScreenDestination) {
                            inclusive = true
                        }
                    }*/
                    navigator.navigateUp()
                },
                enabled = collectedSaveButton.value,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "Spremi")
            }
        }
    }
}
