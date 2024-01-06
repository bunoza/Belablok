package com.bunoza.belablok.ui.gamedetailsscreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.bunoza.belablok.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailsTopBar(onDeleteClick:()->Unit, onShareClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Statistika", color = MaterialTheme.colorScheme.onPrimary) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        actions = {
            IconButton(onClick = onDeleteClick) {
                Icon(painter = painterResource(id = R.drawable.baseline_delete_outline_24), contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
            }
            IconButton(onClick = onShareClick) {
                Icon(painter = painterResource(id = R.drawable.baseline_share_24), contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    )
}
