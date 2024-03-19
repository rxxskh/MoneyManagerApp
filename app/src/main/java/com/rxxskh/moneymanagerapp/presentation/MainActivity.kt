package com.rxxskh.moneymanagerapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rxxskh.moneymanagerapp.presentation.navigation.BottomNavigationBar
import com.rxxskh.moneymanagerapp.presentation.navigation.NavigationGraph
import com.rxxskh.moneymanagerapp.presentation.navigation.Screen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.MoneyKeeperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyKeeperTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        val nowScreenRoute =
                            navController.currentBackStackEntryAsState().value?.destination?.route
                        val noMenuRoutes = listOf(
                            Screen.SplashScreen.route,
                            Screen.SignupScreen.route,
                            Screen.LoginScreen.route,
                            null
                        )
                        if (!noMenuRoutes.contains(nowScreenRoute)) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { paddingValue ->
                    Box(modifier = Modifier.padding(paddingValue)) {
                        NavigationGraph(navController = navController)
                    }
                }
            }
        }
    }
}