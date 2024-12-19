package com.axpe.timekeeping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.axpe.timekeeping.ui.feature.home.navigation.navigateToHome
import com.axpe.timekeeping.ui.feature.home.navigation.screenHome
import com.axpe.timekeeping.ui.feature.login.navigation.LoginRoute
import com.axpe.timekeeping.ui.feature.login.navigation.screenLogin
import com.axpe.timekeeping.ui.theme.TimeKeepingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeKeepingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = LoginRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        screenLogin {
                            navController.navigateToHome {
                                popUpTo(LoginRoute) {
                                    inclusive = true
                                }
                            }
                        }
                        screenHome()
                    }
                }

            }
        }
    }
}