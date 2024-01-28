package com.bunoza.belablok.ui.inputscorescreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bunoza.belablok.R
import com.bunoza.belablok.ui.theme.BelaBlokTheme

@Composable
fun CallComposable(
    callValue: String,
    iconButtonVisibility: Boolean,
    timesCalledVisibility: Boolean,
    timesCalledValue: Int,
    onButtonClick: () -> Unit,
    onIconButtonClick: () -> Unit
) {
    val alphaIconVisibility: Float by animateFloatAsState(
        targetValue = if (iconButtonVisibility) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        ),
        label = ""
    )
    val alphaTextVisibility: Float by animateFloatAsState(
        targetValue = if (timesCalledVisibility) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        ),
        label = ""
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onIconButtonClick,
            modifier = Modifier
                .size(24.dp)
                .alpha(alphaIconVisibility)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_outline_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
        }

        Button(
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.width(100.dp).border(1.dp, color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = callValue, fontSize = 20.sp)
        }

        Text(
            text = "x$timesCalledValue",
            color = MaterialTheme.colorScheme.background,
            fontSize = 14.sp,
            modifier = Modifier.alpha(alphaTextVisibility)
        )
    }
}

@Composable
fun CallRowComposable(
    callValue: String,
    iconButtonVisibilityFirst: Boolean,
    iconButtonVisibilitySecond: Boolean,
    timesCalledVisibilityFirst: Boolean,
    timesCalledVisibilitySecond: Boolean,
    timesCalledValueFirst: Int,
    timesCalledValueSecond: Int,
    onFirstButtonClick: () -> Unit,
    onSecondButtonClick: () -> Unit,
    onFirstIconButtonClick: () -> Unit,
    onSecondIconButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CallComposable(
            callValue = callValue,
            iconButtonVisibility = iconButtonVisibilityFirst,
            timesCalledVisibility = timesCalledVisibilityFirst,
            timesCalledValue = timesCalledValueFirst,
            onButtonClick = onFirstButtonClick,
            onIconButtonClick = onFirstIconButtonClick
        )
        CallComposable(
            callValue = callValue,
            iconButtonVisibility = iconButtonVisibilitySecond,
            timesCalledVisibility = timesCalledVisibilitySecond,
            timesCalledValue = timesCalledValueSecond,
            onButtonClick = onSecondButtonClick,
            onIconButtonClick = onSecondIconButtonClick
        )
    }
}

@Preview
@Composable
private fun PreviewCallComposable() {
    BelaBlokTheme {
        CallComposable("20", true, true, 4, {}, {})
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewCallComposableDarkTheme() {
    BelaBlokTheme {
        CallComposable("20", true, true, 4, {}, {})
    }
}
