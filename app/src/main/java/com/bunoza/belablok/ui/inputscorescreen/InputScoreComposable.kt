package com.bunoza.belablok.ui.inputscorescreen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bunoza.belablok.R

@Composable
fun InputScoreComposable(
    pointsValue: String,
    onPointsChanged: (String) -> Unit
) {
    TextField(
        value = pointsValue,
        onValueChange = { onPointsChanged.invoke(it) },
        placeholder = {
            Text(
                text = stringResource(R.string.zero_points),
                modifier = Modifier
                    .height(100.dp)
                    .width(150.dp),
                textAlign = TextAlign.Center,
                fontSize = 64.sp
            )
        },
        modifier = Modifier
            .width(150.dp)
            .height(100.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.background,
            unfocusedTextColor = MaterialTheme.colorScheme.background,
            cursorColor = MaterialTheme.colorScheme.background,
            focusedPlaceholderColor = MaterialTheme.colorScheme.surface,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        textStyle = TextStyle(fontSize = 64.sp, textAlign = TextAlign.Center),
        singleLine = true
    )
}
