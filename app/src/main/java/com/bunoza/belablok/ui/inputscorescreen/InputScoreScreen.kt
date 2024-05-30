package com.bunoza.belablok.ui.inputscorescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bunoza.belablok.data.database.model.SingleGame
import com.bunoza.belablok.ui.theme.BelaBlokTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
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
    val collectedCallShtigliaUsState = inputScoreViewModel.callShtigliaUsState.collectAsState()
    val collectedCallShtigliaThemState = inputScoreViewModel.callShtigliaThemState.collectAsState()

    /*Column(
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
                onPointsChanged = { inputScoreViewModel.onFirstInputChange(it) },
                isEnabled = inputScoreViewModel.isInputFieldEnabled.value
            )
            InputScoreComposable(
                pointsValue = inputScoreViewModel.secondPlayerPoints.value,
                onPointsChanged = { inputScoreViewModel.onSecondInputChange(it) },
                isEnabled = inputScoreViewModel.isInputFieldEnabled.value
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
        CallRowComposable(
            callValue = "ŠTIGLJA",
            iconButtonVisibilityFirst = collectedCallShtigliaUsState.value.visibility,
            iconButtonVisibilitySecond = collectedCallShtigliaThemState.value.visibility,
            timesCalledVisibilityFirst = collectedCallShtigliaUsState.value.timesCalledVisibility,
            timesCalledVisibilitySecond = collectedCallShtigliaThemState.value.timesCalledVisibility,
            timesCalledValueFirst = collectedCallShtigliaUsState.value.timesCalled,
            timesCalledValueSecond = collectedCallShtigliaThemState.value.timesCalled,
            onFirstButtonClick = { inputScoreViewModel.onShtigliaCallUsClick() },
            onSecondButtonClick = { inputScoreViewModel.onShtigliaCallThemClick() },
            onFirstIconButtonClick = { inputScoreViewModel.onShtigliaCallUsMinusClick() },
            onSecondIconButtonClick = {inputScoreViewModel.onShtigliaCallThemMinusClick()}
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
                },
                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.background)
            ) {
                Text(text = "Odustani")
            }
            Button(
                onClick = {
                    inputScoreViewModel.onSaveGameClick()
                    navigator.navigateUp()
                },
                enabled = collectedSaveButton.value,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.outline
                )
            ) {
                Text(text = "Spremi")
            }
        }
    }*/
    InputScoreScreenContent(
        callTwentyUs = collectedCallTwentyUsState.value,
        callTwentyThem = collectedCallTwentyThemState.value,
        callFiftyUs = collectedCallFiftyUsState.value,
        callFiftyThem = collectedCallFiftyThemState.value,
        callHundredUs = collectedCallHundredUsState.value,
        callHundredThem = collectedCallHundredThemState.value,
        callBelotUs = collectedCallBelotUsState.value,
        callBelotThem = collectedCallBelotThemState.value,
        callStigljaUs = collectedCallShtigliaUsState.value,
        callStigljaThem = collectedCallShtigliaThemState.value,
        firstPlayerPoints = inputScoreViewModel.firstPlayerPoints.value,
        secondPlayerPoints = inputScoreViewModel.secondPlayerPoints.value,
        onTwentyUsClick = {inputScoreViewModel.onTwentyCallUsClick()},
        onTwentyUsMinusClick = { inputScoreViewModel.onTwentyCallMinusUsClick() },
        onTwentyThemClick = { inputScoreViewModel.onTwentyCallThemClick() },
        onTwentyThemMinusClick = { inputScoreViewModel.onTwentyCallMinusThemClick() },
        onFiftyUsClick = { inputScoreViewModel.onFiftyCallUsClick() },
        onFiftyUsMinusClick = { inputScoreViewModel.onFiftyCallMinusUsClick() },
        onFiftyThemClick = {inputScoreViewModel.onFiftyCallThemClick()},
        onFiftyThemMinusClick = {inputScoreViewModel.onFiftyCallMinusThemClick() },
        onHundredUsClick = { inputScoreViewModel.onHundredCallUsClick() },
        onHundredUsMinusClick = { inputScoreViewModel.onHundredCallMinusUsClick() },
        onHundredThemClick = { inputScoreViewModel.onHundredCallThemClick() },
        onHundredThemMinusClick = { inputScoreViewModel.onHundredCallMinusThemClick() },
        onBelotUsClick = { inputScoreViewModel.onBelotCallUsClick() },
        onBelotUsMinusClick = { inputScoreViewModel.onBelotCallUsMinusClick() },
        onBelotThemClick = { inputScoreViewModel.onBelotCallThemClick() },
        onBelotThemMinusClick = { inputScoreViewModel.onBelotCallThemMinusClick() },
        onStigljaUsClick = { inputScoreViewModel.onShtigliaCallUsClick() },
        onStigljaUsMinusClick = { inputScoreViewModel.onShtigliaCallUsMinusClick() },
        onStigljaThemClick = { inputScoreViewModel.onShtigliaCallThemClick() },
        onStigljaThemMinusClick = { inputScoreViewModel.onShtigliaCallThemMinusClick() },
        onDeleteCallsClick = { inputScoreViewModel.onDeleteCallsClick() },
        isFirstScoreEnabled = inputScoreViewModel.isInputFieldEnabled.value,
        onFirstPointsChanged = {inputScoreViewModel.onFirstInputChange(it)},
        onSecondPointsChanged = {inputScoreViewModel.onSecondInputChange(it)},
        collectedTimesCalledUs = collectedTimesCalledUsState.value,
        collectedTimesCalledThem = collectedTimesCalledThemState.value,
        radioOptions = inputScoreViewModel.radioOptions,
        selectedOption = inputScoreViewModel.selectedOption.value,
        onMIClick = {inputScoreViewModel.onRadioButtonClick(it)},
        onVIClick = {inputScoreViewModel.onRadioButtonClick(it)},
        onCancelClick = { navigator.navigateUp() },
        onSaveGameClick = {
            inputScoreViewModel.onSaveGameClick()
            navigator.navigateUp() },
        isSaveButtonEnabled = collectedSaveButton.value
    )
}

@Composable
private fun InputScoreScreenContent(
    callTwentyUs:CallState,
    callTwentyThem:CallState,
    callFiftyUs:CallState,
    callFiftyThem:CallState,
    callHundredUs:CallState,
    callHundredThem:CallState,
    callBelotUs:CallState,
    callBelotThem:CallState,
    callStigljaUs:CallState,
    callStigljaThem:CallState,
    firstPlayerPoints:String,
    secondPlayerPoints:String,
    onTwentyUsClick:()->Unit,
    onTwentyUsMinusClick:()->Unit,
    onTwentyThemClick:()->Unit,
    onTwentyThemMinusClick:()->Unit,
    onFiftyUsClick:()->Unit,
    onFiftyUsMinusClick:()->Unit,
    onFiftyThemClick:()->Unit,
    onFiftyThemMinusClick:()->Unit,
    onHundredUsClick:()->Unit,
    onHundredUsMinusClick:()->Unit,
    onHundredThemClick:()->Unit,
    onHundredThemMinusClick:()->Unit,
    onBelotUsClick:()->Unit,
    onBelotUsMinusClick:()->Unit,
    onBelotThemClick:()->Unit,
    onBelotThemMinusClick:()->Unit,
    onStigljaUsClick:()->Unit,
    onStigljaUsMinusClick:()->Unit,
    onStigljaThemClick:()->Unit,
    onStigljaThemMinusClick:()->Unit,
    onDeleteCallsClick:()->Unit,
    isFirstScoreEnabled:Boolean,
    onFirstPointsChanged:(String)->Unit,
    onSecondPointsChanged:(String)->Unit,
    collectedTimesCalledUs:Int,
    collectedTimesCalledThem:Int,
    radioOptions:List<String>,
    selectedOption:String,
    onMIClick:(String)->Unit,
    onVIClick:(String)->Unit,
    onCancelClick:()->Unit,
    onSaveGameClick:()->Unit,
    isSaveButtonEnabled:Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            InputScoreComposable(
                pointsValue = firstPlayerPoints,
                onPointsChanged = onFirstPointsChanged,
                isEnabled = isFirstScoreEnabled
            )
            InputScoreComposable(
                pointsValue = secondPlayerPoints,
                onPointsChanged = onSecondPointsChanged,
                isEnabled = isFirstScoreEnabled
            )
        }
        CallsComposable(collectedTimesCalledUsState = collectedTimesCalledUs, collectedTimesCalledThemState = collectedTimesCalledThem, onDeleteClick = onDeleteCallsClick)
        CallRowComposable(
            callValue = " ${callTwentyUs.callValue} ",
            iconButtonVisibilityFirst = callTwentyUs.visibility,
            iconButtonVisibilitySecond = callTwentyThem.visibility,
            timesCalledVisibilityFirst = callTwentyUs.timesCalledVisibility,
            timesCalledVisibilitySecond = callTwentyThem.timesCalledVisibility,
            timesCalledValueFirst = callTwentyUs.timesCalled,
            timesCalledValueSecond = callTwentyThem.timesCalled,
            onFirstButtonClick = onTwentyUsClick,
            onSecondButtonClick = onTwentyThemClick,
            onFirstIconButtonClick = onTwentyUsMinusClick,
            onSecondIconButtonClick = onTwentyThemMinusClick
        )
        CallRowComposable(
            callValue = " ${callFiftyUs.callValue} ",
            iconButtonVisibilityFirst = callFiftyUs.visibility,
            iconButtonVisibilitySecond = callFiftyThem.visibility,
            timesCalledVisibilityFirst = callFiftyUs.timesCalledVisibility,
            timesCalledVisibilitySecond = callFiftyThem.timesCalledVisibility,
            timesCalledValueFirst = callFiftyUs.timesCalled,
            timesCalledValueSecond = callFiftyThem.timesCalled,
            onFirstButtonClick = onFiftyUsClick,
            onSecondButtonClick = onFiftyThemClick,
            onFirstIconButtonClick = onFiftyUsMinusClick,
            onSecondIconButtonClick = onFiftyThemMinusClick
        )
        CallRowComposable(
            callValue = "${callHundredUs.callValue}",
            iconButtonVisibilityFirst = callHundredUs.visibility,
            iconButtonVisibilitySecond = callHundredThem.visibility,
            timesCalledVisibilityFirst = callHundredUs.timesCalledVisibility,
            timesCalledVisibilitySecond = callHundredThem.timesCalledVisibility,
            timesCalledValueFirst = callHundredUs.timesCalled,
            timesCalledValueSecond = callHundredThem.timesCalled,
            onFirstButtonClick = onHundredUsClick,
            onSecondButtonClick = onHundredThemClick,
            onFirstIconButtonClick = onHundredUsMinusClick,
            onSecondIconButtonClick = onHundredThemMinusClick
        )
        CallRowComposable(
            callValue = "BELOT",
            iconButtonVisibilityFirst = callBelotUs.visibility,
            iconButtonVisibilitySecond = callBelotThem.visibility,
            timesCalledVisibilityFirst = callBelotUs.timesCalledVisibility,
            timesCalledVisibilitySecond = callBelotThem.timesCalledVisibility,
            timesCalledValueFirst = callBelotUs.timesCalled,
            timesCalledValueSecond = callBelotThem.timesCalled,
            onFirstButtonClick = onBelotUsClick,
            onSecondButtonClick = onBelotThemClick,
            onFirstIconButtonClick = onBelotUsMinusClick,
            onSecondIconButtonClick = onBelotThemMinusClick
        )
        CallRowComposable(
            callValue = "ŠTIGLJA",
            iconButtonVisibilityFirst = callStigljaUs.visibility,
            iconButtonVisibilitySecond = callStigljaThem.visibility,
            timesCalledVisibilityFirst = callStigljaUs.timesCalledVisibility,
            timesCalledVisibilitySecond = callStigljaThem.timesCalledVisibility,
            timesCalledValueFirst = callStigljaUs.timesCalled,
            timesCalledValueSecond = callStigljaThem.timesCalled,
            onFirstButtonClick = onStigljaUsClick,
            onSecondButtonClick = onStigljaThemClick,
            onFirstIconButtonClick = onStigljaUsMinusClick,
            onSecondIconButtonClick = onStigljaThemMinusClick
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
                isSelected = (radioOptions[0] == selectedOption),
                onRadioButtonClick = {
                    onMIClick.invoke(radioOptions[0])
                },
                radioText = radioOptions[0]
            )
            CallRadioButtonComposable(
                isSelected = (radioOptions[1] == selectedOption),
                onRadioButtonClick = {
                    onVIClick.invoke(radioOptions[1])
                },
                radioText = radioOptions[1]
            )
        }
        Spacer(modifier = Modifier.weight(1F))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(
                onClick = onCancelClick,
                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.background)
            ) {
                Text(text = "Odustani")
            }
            Button(
                onClick = onSaveGameClick,
                enabled = isSaveButtonEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.outline
                )
            ) {
                Text(text = "Spremi")
            }
        }
    }
}

@Composable
@PreviewScreenSizes
private fun PreviewInputScoreScreen() {
    val testCallState = CallState(20,2,true,true)
    BelaBlokTheme {
        InputScoreScreenContent(
            callTwentyUs = testCallState,
            callTwentyThem = testCallState,
            callFiftyUs = testCallState,
            callFiftyThem = testCallState,
            callHundredUs = testCallState,
            callHundredThem = testCallState,
            callBelotUs = testCallState,
            callBelotThem = testCallState,
            callStigljaUs = testCallState,
            callStigljaThem = testCallState,
            firstPlayerPoints = "122",
            secondPlayerPoints = "40",
            onTwentyUsClick = { /*TODO*/ },
            onTwentyUsMinusClick = { /*TODO*/ },
            onTwentyThemClick = { /*TODO*/ },
            onTwentyThemMinusClick = { /*TODO*/ },
            onFiftyUsClick = { /*TODO*/ },
            onFiftyUsMinusClick = { /*TODO*/ },
            onFiftyThemClick = { /*TODO*/ },
            onFiftyThemMinusClick = { /*TODO*/ },
            onHundredUsClick = { /*TODO*/ },
            onHundredUsMinusClick = { /*TODO*/ },
            onHundredThemClick = { /*TODO*/ },
            onHundredThemMinusClick = { /*TODO*/ },
            onBelotUsClick = { /*TODO*/ },
            onBelotUsMinusClick = { /*TODO*/ },
            onBelotThemClick = { /*TODO*/ },
            onBelotThemMinusClick = { /*TODO*/ },
            onStigljaUsClick = { /*TODO*/ },
            onStigljaUsMinusClick = { /*TODO*/ },
            onStigljaThemClick = { /*TODO*/ },
            onStigljaThemMinusClick = { /*TODO*/ },
            onDeleteCallsClick = { /*TODO*/ },
            isFirstScoreEnabled = true,
            onFirstPointsChanged = {},
            onSecondPointsChanged = {},
            collectedTimesCalledUs = 3,
            collectedTimesCalledThem =4 ,
            radioOptions = listOf("MI","VI"),
            selectedOption = "MI",
            onMIClick = {},
            onVIClick = {},
            onCancelClick = { /*TODO*/ },
            onSaveGameClick = { /*TODO*/ },
            isSaveButtonEnabled = true
        )
    }
}
