package com.bunoza.belablok.ui.gamedetailsscreen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteGameAlertDialog(onDismissClick: () -> Unit, onConfirmClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        confirmButton = {
            Button(onClick = onConfirmClick, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error, contentColor = MaterialTheme.colorScheme.onError)) {
                Text(text = "Obriši", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissClick) {
                Text(text = "Odustani")
            }
        },
        title = {
            Text(text = "Obrisati partiju?")
        },
        text = {
            Text(text = "Jeste li sigurni da želite obrisati ovu partiju?")
        },
        containerColor = MaterialTheme.colorScheme.background
    )
}
