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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bunoza.belablok.R
import com.bunoza.belablok.ui.theme.BelaBlokTheme

@Composable
fun InputScoreComposable(
    pointsValue: String,
    onPointsChanged: (String) -> Unit,
    isEnabled:Boolean
) {
    TextField(
        value = pointsValue,
        onValueChange = { onPointsChanged.invoke(it) },
        placeholder = {
            Text(
                text = stringResource(R.string.zero_points),
                modifier = Modifier
                    .height(75.dp)
                    .width(125.dp),
                textAlign = TextAlign.Center,
                fontSize = 48.sp
            )
        },
        modifier = Modifier
            .width(125.dp)
            .height(85.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.background,
            unfocusedTextColor = MaterialTheme.colorScheme.background,
            cursorColor = MaterialTheme.colorScheme.background,
            focusedPlaceholderColor = MaterialTheme.colorScheme.surface,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        textStyle = TextStyle(fontSize = 48.sp, textAlign = TextAlign.Center),
        singleLine = true,
        enabled = isEnabled,
    )
}

@Composable
@Preview
private fun PreviewInputScoreComposable() {
    BelaBlokTheme {
        InputScoreComposable(pointsValue = "10", onPointsChanged = {}, isEnabled = true)
    }
}
