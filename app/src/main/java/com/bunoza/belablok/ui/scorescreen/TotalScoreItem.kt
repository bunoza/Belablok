package com.bunoza.belablok.ui.scorescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TotalScoreItem(
    firstPlayerText: String,
    secondPlayerText: String,
    modifier: Modifier = Modifier
) {
    Column {
        Divider(thickness = 5.dp, color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(horizontal = 8.dp))
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
                textAlign = TextAlign.Center,
            )
            Text(
                text = "-",
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
            )


            Text(
                text = secondPlayerText,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                modifier = Modifier.width(100.dp),
                textAlign = TextAlign.Center,
            )

        }
    }

}
