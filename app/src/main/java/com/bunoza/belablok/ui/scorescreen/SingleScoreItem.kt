package com.bunoza.belablok.ui.scorescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bunoza.belablok.R
import com.bunoza.belablok.data.database.model.SingleGame

@Composable
fun SingleScoreItem(singleGame: SingleGame, onSingleGameClick: (SingleGame) -> Unit) {
    Column(Modifier.clickable {
        onSingleGameClick.invoke(singleGame)
    }) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = singleGame.scoreWe.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                modifier = Modifier.width(100.dp),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Cursive
            )
            Icon(
                painter = painterResource(id = R.drawable.outline_edit_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )

            Text(
                text = singleGame.scoreThem.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                modifier = Modifier.width(100.dp),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Cursive
            )

        }
        Divider(Modifier.padding(8.dp))
    }

}
