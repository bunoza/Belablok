package com.bunoza.belablok.ui.scorescreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bunoza.belablok.ui.theme.BelaBlokTheme

@Composable
fun NewGameAlertDialog(onConfirmClick: () -> Unit, onDismissClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        dismissButton = {
            TextButton(onClick = onDismissClick) {
                Text(text = "Odustani")
            }
        },
        confirmButton = {
            Button(onClick = onConfirmClick) {
                Text(text = "U redu")
            }
        },
        title = { Text(text = "Nova igra?") },
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.primary
    )
}

@Preview
@Composable
private fun PreviewNewGameAlertDialog() {
    BelaBlokTheme {
        NewGameAlertDialog(onConfirmClick = {}, onDismissClick = {})
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewNewGameAlertDialogDarkTheme() {
    BelaBlokTheme {
        NewGameAlertDialog(onConfirmClick = {}, onDismissClick = {})
    }
}
