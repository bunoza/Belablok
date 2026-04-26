package com.bunoza.belablok

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunoza.belablok.ui.MainViewModel
import com.bunoza.belablok.ui.NavGraphs
import com.bunoza.belablok.ui.theme.BelaBlokTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.DestinationsNavHost
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel = koinViewModel<MainViewModel>()
            val keepScreenOn by mainViewModel.keepScreenOn.collectAsStateWithLifecycle()
            DisposableEffect(keepScreenOn) {
                if (keepScreenOn == true) {
                    window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                } else {
                    window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }

                onDispose {
                    window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
            }
            BelaBlokTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
        firebaseAnalytics = Firebase.analytics
    }
}
