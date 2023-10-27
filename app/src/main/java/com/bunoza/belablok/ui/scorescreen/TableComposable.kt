package com.bunoza.belablok.ui.scorescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bunoza.belablok.R

@Composable
fun TableComposable(tableOptions:List<String>,selectedOption:String,onOptionSelected:(String)->Unit) {
    Text(text = "Dijeli: $selectedOption",
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(12.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
         Icon(painter = painterResource(id = showPersonIconResource(isClicked = (tableOptions[2]==selectedOption))), contentDescription = null,Modifier.size(120.dp).clickable { onOptionSelected(tableOptions[2])}.padding(vertical = 12.dp),tint = MaterialTheme.colorScheme.primary)
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Icon(painter = painterResource(id = showPersonIconResource(isClicked = (tableOptions[3]==selectedOption))), contentDescription = null,Modifier.size(120.dp).clickable { onOptionSelected(tableOptions[3])}.padding(vertical = 12.dp), tint = MaterialTheme.colorScheme.primary)
        Icon(painter = painterResource(id = R.drawable.outline_table_bar_24), contentDescription = null, Modifier.size(120.dp).padding(vertical = 12.dp),tint = MaterialTheme.colorScheme.primary)
        Icon(painter = painterResource(id = showPersonIconResource(isClicked = (tableOptions[1]==selectedOption))), contentDescription = null,Modifier.size(120.dp).clickable { onOptionSelected(tableOptions[1])}.padding(vertical = 12.dp),tint = MaterialTheme.colorScheme.primary)
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Icon(painter = painterResource(id = showPersonIconResource(isClicked = (tableOptions[0]==selectedOption))), contentDescription = null,Modifier.size(120.dp).clickable { onOptionSelected(tableOptions[0])}.padding(vertical = 12.dp),tint = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun showPersonIconResource(isClicked:Boolean):Int{
    return if (isClicked){
        R.drawable.baseline_person_24
    }else{
        R.drawable.baseline_person_outline_24
    }
}