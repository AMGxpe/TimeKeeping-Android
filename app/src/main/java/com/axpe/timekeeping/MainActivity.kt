package com.axpe.timekeeping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.axpe.timekeeping.ui.TimeKeepingApp
import com.axpe.timekeeping.ui.rememberTimeKeepingAppState
import com.axpe.timekeeping.ui.theme.TimeKeepingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberTimeKeepingAppState()
            TimeKeepingTheme {
                TimeKeepingApp(appState)
            }
        }
    }
}