package com.bunoza.belablok.ui.inputscorescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

@Composable
fun CallRadioButtonComposable(
    isSelected: Boolean,
    onRadioButtonClick: () -> Unit,
    radioText: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onRadioButtonClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.background,
                unselectedColor = MaterialTheme.colorScheme.background
            )
        )
        Text(text = radioText, fontSize = 14.sp, color = MaterialTheme.colorScheme.background)
    }
}
