package com.bunoza.belablok.ui.scorescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bunoza.belablok.R
import com.bunoza.belablok.data.database.model.SingleGame

@Composable
fun SingleScoreItem(singleGame: SingleGame, onSingleGameClick: (SingleGame) -> Unit) {
    Column(
        Modifier.clickable {
            onSingleGameClick.invoke(singleGame)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            // horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = singleGame.scoreWe.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 32.sp,
                modifier = Modifier
                    .width(100.dp)
                    .weight(2F),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1F))
            Icon(
                painter = painterResource(id = R.drawable.outline_edit_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.weight(1F)
            )
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = singleGame.scoreThem.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 32.sp,
                modifier = Modifier
                    .width(100.dp)
                    .weight(2F),
                textAlign = TextAlign.Center
            )
            if (singleGame.shtigliaCalledWe || singleGame.shtigliaCalledThem) {
                Icon(painter = painterResource(id = R.drawable.outline_theater_comedy_24), contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.weight(1F).size(32.dp))
            } else if (checkForTakedown(singleGame)) {
                Icon(painter = painterResource(id = R.drawable.fall), contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.weight(1F).size(32.dp))
            } else {
                Spacer(modifier = Modifier.weight(1F))
            }
        }
        HorizontalDivider(Modifier.padding(8.dp))
    }
}

fun checkForTakedown(singleGame: SingleGame): Boolean {
    if (singleGame.whoCalled == "MI") {
        if (singleGame.scoreWe <= singleGame.scoreThem) {
            // MI pali
            return true
        }
    } else {
        if (singleGame.scoreWe >= singleGame.scoreThem) {
            // VI pali
            return true
        }
    }
    return false
}
