package com.rxxskh.moneymanagerapp.presentation.screen.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rxxskh.moneymanagerapp.presentation.animation.LoadingAnimation
import com.rxxskh.moneymanagerapp.presentation.navigation.Screen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightBlue

@Composable
fun SplashScreen(
    vm: SplashViewModel = hiltViewModel(),
    navController: NavController
) {
    if (vm.status) {
        navController.navigate(Screen.IncomeScreen.route)
        vm.resetStatus()
    } else {
        LoadingAnimation(color = LightBlue)
    }
}