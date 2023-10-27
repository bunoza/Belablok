package com.bunoza.belablok.ui.gamedetailsscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.bunoza.belablok.ui.theme.BelaBlokTheme

@Composable
fun SinglePointStatLineComposable(description:String,firstvalue:Int,secondValue:Int) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(text = "$description:\n $firstvalue", textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onPrimary, fontSize = 16.sp)
        Text(text = "$description:\n $secondValue", textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onPrimary, fontSize = 16.sp)


    }

}
@Preview
@Composable
fun PreviewSinglePointStatLine() {
    BelaBlokTheme {
        SinglePointStatLineComposable(description = "Ukupno bodova", firstvalue = 20, secondValue = 100)
    }

}