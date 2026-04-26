package com.bunoza.belablok.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunoza.belablok.ui.gamedetailsscreen.DeleteGameAlertDialog
import com.bunoza.belablok.ui.gamedetailsscreen.DeleteHistoryAlertDialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun SettingsScreen(navigator: DestinationsNavigator) {
    val settingsViewModel = koinViewModel<SettingsViewModel>()
    val options = listOf("501", "701", "1001")
    val isScreenEnabled by settingsViewModel.isScreenEnabled.collectAsStateWithLifecycle()
    val targetScore by settingsViewModel.targetScore.collectAsStateWithLifecycle()
    val isGameDiffEnabled by settingsViewModel.isGameDiffEnabled.collectAsStateWithLifecycle()
    val isHistoryDiffEnabled by settingsViewModel.isHistoryDiffEnabled.collectAsStateWithLifecycle()
    var isDeleteCurrentGameDialogOpened by rememberSaveable {
        mutableStateOf(false)
    }
    var isDeleteHistoryDialogOpened by rememberSaveable {
        mutableStateOf(false)
    }

    if (isDeleteCurrentGameDialogOpened) {
        DeleteGameAlertDialog(
            onDismissClick = {
                isDeleteCurrentGameDialogOpened = false
            },
            onConfirmClick = {
                settingsViewModel.deleteCurrentGame()
                isDeleteCurrentGameDialogOpened = false
            }
        )
    }

    if (isDeleteHistoryDialogOpened) {
        DeleteHistoryAlertDialog(
            onDismissClick = {
                isDeleteHistoryDialogOpened = false
            },
            onConfirmClick = {
                settingsViewModel.deleteHistory()
                isDeleteHistoryDialogOpened = false
            }
        )
    }

    SettingsScreenContent(
        options = options,
        selectedIndex = targetScore,
        onNavigateBack = {
            navigator.navigateUp()
        },
        isScreenSwitchEnabled = isScreenEnabled,
        onScreenSwitchChange = {
            settingsViewModel.updateScreenSetting(it)
        },
        isGameDiffEnabled = isGameDiffEnabled,
        onGameDiffSwitchChange = {
            settingsViewModel.updateGameScoreDiff(it)
        },
        isHistoryDiffEnabled = isHistoryDiffEnabled,
        onHistoryDiffSwitchChange = {
            settingsViewModel.updateHistoryScoreDiff(it)
        },
        onDeleteCurrentGame = {
            isDeleteCurrentGameDialogOpened = true
        },
        onDeleteHistory = {
            isDeleteHistoryDialogOpened = true
        },
        onTargetScoreChange = {
            settingsViewModel.updateTargetScore(options[it])
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreenContent(
    options: List<String>,
    selectedIndex: Int,
    onNavigateBack: () -> Unit,
    isScreenSwitchEnabled: Boolean,
    onScreenSwitchChange: (Boolean) -> Unit,
    isGameDiffEnabled: Boolean,
    onGameDiffSwitchChange: (Boolean) -> Unit,
    isHistoryDiffEnabled: Boolean,
    onHistoryDiffSwitchChange: (Boolean) -> Unit,
    onDeleteCurrentGame: () -> Unit,
    onDeleteHistory: () -> Unit,
    onTargetScoreChange: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Postavke", color = MaterialTheme.colorScheme.onPrimary)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack

                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) { pv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = pv.calculateTopPadding() + 16.dp,
                    bottom = pv.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            SwitchRow(
                label = "Ne gasi zaslon",
                isChecked = isScreenSwitchEnabled,
                onCheckedChange = onScreenSwitchChange,
                supportingContent = {
                    Text("Zaslon ostaje upaljen dok je aplikacija u fokusu", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.surfaceVariant)
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimary)
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Prikaži razliku između bodova",
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            SwitchRow(
                label = "Na glavnom ekranu",
                isChecked = isGameDiffEnabled,
                onCheckedChange = onGameDiffSwitchChange
            )
            SwitchRow(
                label = "U povijesti",
                isChecked = isHistoryDiffEnabled,
                onCheckedChange = onHistoryDiffSwitchChange
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimary)

            Text(
                text = "Igra se do:",
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        selected = selectedIndex == index,
                        onClick = { onTargetScoreChange(index) },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        colors = SegmentedButtonDefaults.colors(
                            inactiveContainerColor = MaterialTheme.colorScheme.primary,
                            inactiveBorderColor = MaterialTheme.colorScheme.onPrimary,
                            activeBorderColor = MaterialTheme.colorScheme.onPrimary,
                            inactiveContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(label)
                    }
                }
            }
            Spacer(Modifier.weight(1F))
            Button(
                onClick = onDeleteCurrentGame,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Obriši trenutnu partiju")
            }
            Button(
                onClick = onDeleteHistory,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Obriši povijest igre")
            }
        }
    }
}

@Composable
private fun SwitchRow(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    supportingContent: @Composable () -> Unit = {}

) {
    ListItem(
        headlineContent = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        supportingContent = supportingContent,
        trailingContent = {
            Switch(
                onCheckedChange = onCheckedChange,
                checked = isChecked,
                colors = SwitchDefaults.colors(
                    checkedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedBorderColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primary)
    )
}
