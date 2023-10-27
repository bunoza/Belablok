package com.bunoza.belablok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import com.bunoza.belablok.ui.NavGraphs
import com.bunoza.belablok.ui.inputscorescreen.InputScoreScreen
import com.bunoza.belablok.ui.theme.BelaBlokTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BelaBlokTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
