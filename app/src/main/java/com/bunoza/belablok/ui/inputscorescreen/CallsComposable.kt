package com.bunoza.belablok.ui.inputscorescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CallsComposable(collectedTimesCalledUsState:Int,collectedTimesCalledThemState:Int, onDeleteClick:()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Zvanja:${collectedTimesCalledUsState}",
            color = MaterialTheme.colorScheme.background,
            fontSize = 14.sp
        )
        Button(
            onClick = onDeleteClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Obriši zvanja")
        }
        Text(
            text = "Zvanja:${collectedTimesCalledThemState}",
            color = MaterialTheme.colorScheme.background,
            fontSize = 14.sp
        )
    }


}