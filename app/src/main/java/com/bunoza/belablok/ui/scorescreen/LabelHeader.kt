package com.bunoza.belablok.ui.scorescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabelHeader(firstPlayerText: String, secondPlayerText: String, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = firstPlayerText,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.width(100.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = secondPlayerText,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.width(100.dp),
            textAlign = TextAlign.Center
        )
    }
}
