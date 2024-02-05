package com.bunoza.belablok.ui.scorescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bunoza.belablok.R
import com.bunoza.belablok.data.database.model.SingleGame
import com.bunoza.belablok.ui.UIState
import com.bunoza.belablok.ui.destinations.HistoryScreenDestination
import com.bunoza.belablok.ui.destinations.InputScoreScreenDestination
import com.bunoza.belablok.ui.errorscreen.ErrorScreen
import com.bunoza.belablok.ui.loadingscreen.LoadingScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun ScoreScreen(navigator: DestinationsNavigator) {
    val scoreScreenViewModel = koinViewModel<ScoreScreenViewModel>()
    val scoreScreenUIState by scoreScreenViewModel.scoreScreenUIState.collectAsState()
    val totalScoreWe = scoreScreenViewModel.totalWeScore.collectAsState()
    val totalThemScore = scoreScreenViewModel.totalThemScore.collectAsState()
    val dealer by scoreScreenViewModel.dealer.collectAsState()
    val historyButtonState by scoreScreenViewModel.historyButtonState.collectAsState()


    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            initialValue = SheetValue.Hidden,
            skipPartiallyExpanded = false
        )
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            TableComposable(
                tableOptions = scoreScreenViewModel.dealerPossibilities,
                selectedOption = dealer,
                onOptionSelected = { scoreScreenViewModel.onDealerCounterClick(it) }
            )
        },
        sheetPeekHeight = 0.dp,
        scaffoldState = bottomSheetScaffoldState
    ) {
        when (scoreScreenUIState) {
            ScoreScreenUIState.Loading -> LoadingScreen()
            ScoreScreenUIState.Error -> ErrorScreen { scoreScreenViewModel.getAllSingleGames() }
            is ScoreScreenUIState.Success -> {
                scoreScreenViewModel.dealerChangeCondition()
                ScoreScreenContent(
                    navigateToInputScoreScreen = {
                        navigator.navigate(
                            InputScoreScreenDestination(
                                dealer,
                                null
                            )
                        )
                    },
                    singleGameList = (scoreScreenUIState as ScoreScreenUIState.Success).data,
                    totalScoreWe = totalScoreWe.value,
                    totalScoreThem = totalThemScore.value,
                    dealer = dealer,
                    onDealerCounterClick = {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    },
                    checkNewGame = { scoreScreenViewModel.checkNewGame() },
                    isAlertDialogOpened = scoreScreenViewModel.isAlertDialogOpened.value,
                    shouldNavigate = scoreScreenViewModel.shouldNavigate,
                    onAlertDialogConfirmClick = {
                        scoreScreenViewModel.deleteAllGames()
                        scoreScreenViewModel.resetNavigation()
                        scoreScreenViewModel.startNewGameDealerChange()
                        scoreScreenViewModel.resetListSizes()
                    },
                    onHistoryButtonClick = { navigator.navigate(HistoryScreenDestination) },
                    onSingleGameClick = {
                        navigator.navigate(
                            InputScoreScreenDestination(
                                scoreScreenViewModel.dealer.value,
                                it
                            )
                        )
                    },
                    isHistoryButtonEnabled = historyButtonState,
                    onDismissClick = {
                        scoreScreenViewModel.isAlertDialogOpened.value = false
                    },
                    onDealerChange = {
                        //scoreScreenViewModel.changeDealerAfterNewGame()
                    }
                )
            }
        }


    }
    if (totalScoreWe.value > 1000 || totalThemScore.value > 1000) {
        WinAnimation()
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ScoreScreenContent(
    navigateToInputScoreScreen: () -> Unit,
    singleGameList: List<SingleGame>,
    totalScoreWe: Int,
    totalScoreThem: Int,
    dealer: String,
    onDealerCounterClick: () -> Unit,
    checkNewGame: () -> Unit,
    isAlertDialogOpened: Boolean,
    shouldNavigate: MutableState<Boolean>,
    onAlertDialogConfirmClick: () -> Unit,
    onHistoryButtonClick: () -> Unit,
    onSingleGameClick: (SingleGame) -> Unit,
    isHistoryButtonEnabled: Boolean,
    onDismissClick: () -> Unit,
    onDealerChange: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    if (isAlertDialogOpened) {
        NewGameAlertDialog(
            onConfirmClick = onAlertDialogConfirmClick,
            onDismissClick = onDismissClick
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Bela Blok",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onHistoryButtonClick, enabled = isHistoryButtonEnabled) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_history_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        bottomBar = {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
                TotalScoreItem(
                    firstPlayerText = totalScoreWe.toString(),
                    secondPlayerText = totalScoreThem.toString()
                )
                Row(
                    modifier = Modifier.clickable {
                        onDealerCounterClick.invoke()
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dijeli: $dealer",
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(12.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.outline_edit_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                checkNewGame.invoke()
                if (shouldNavigate.value) {
                    navigateToInputScoreScreen()
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyListState
        ) {
            stickyHeader {
                LabelHeader(
                    firstPlayerText = "MI",
                    secondPlayerText = "VI",
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                )
            }
            items(singleGameList) { singleGame ->
                SingleScoreItem(
                    singleGame,
                    onSingleGameClick
                )
            }
        }
        LaunchedEffect(singleGameList.size) {
            if (singleGameList.isNotEmpty()) {
                lazyListState.animateScrollToItem(singleGameList.size - 1)
            }

        }
    }
}
