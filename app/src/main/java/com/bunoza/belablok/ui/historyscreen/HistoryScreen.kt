package com.bunoza.belablok.ui.historyscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunoza.belablok.data.database.model.Game
import com.bunoza.belablok.ui.EmptyGameScreen
import com.bunoza.belablok.ui.UIState
import com.bunoza.belablok.ui.destinations.GameDetailsScreenDestination
import com.bunoza.belablok.ui.errorscreen.ErrorScreen
import com.bunoza.belablok.ui.loadingscreen.LoadingScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun HistoryScreen(navigator: DestinationsNavigator) {
    val historyViewModel = koinViewModel<HistoryViewModel>()
    val uiState by historyViewModel.uiState.collectAsState()
    Scaffold(topBar = {
        HistoryTopBar {
            navigator.navigateUp()
        }
    }) { paddingValues ->
        when (uiState) {
            UIState.Loading -> LoadingScreen()
            UIState.Error -> ErrorScreen {
                historyViewModel.getAllGames()
            }

            UIState.EmptyListState -> {
                navigator.navigateUp()
            }

            is UIState.Success<*> -> HistoryScreenContent(gameList = (uiState as UIState.Success<*>).data as List<Game>, onCardClick = { navigator.navigate(GameDetailsScreenDestination(it.id)) }, paddingValues)
        }
    }
}

@Composable
private fun HistoryScreenContent(gameList: List<Game>, onCardClick: (Game) -> Unit, paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = paddingValues.calculateTopPadding(),
                bottom = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(gameList) { game ->
            GameItem(
                firstPlayerText = game.totalPointsWe.toString(),
                secondPlayerText = game.totalPointsThem.toString(),
                onCardClick = { onCardClick.invoke(game) }
            )
        }
    }
}

/*@Preview
@Composable
private fun PreviewHistoryScreenLightTheme() {
    BelaBlokTheme {
        HistoryScreenContent(gameList = listOf(Game(wePoints = listOf<Int>(12,14,15,16), themPoints = listOf(1,1,1,1,1,1,1))))
    }

}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewHistoryScreenDarkTheme() {
    BelaBlokTheme {
        HistoryScreenContent(gameList = listOf(Game(wePoints = listOf<Int>(12,14,15,16), themPoints = listOf(1,1,1,1,1,1,1))))
    }
}*/
