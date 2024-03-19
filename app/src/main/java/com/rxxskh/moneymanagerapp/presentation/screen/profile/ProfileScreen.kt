package com.rxxskh.moneymanagerapp.presentation.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.presentation.component.AppLargeButton
import com.rxxskh.moneymanagerapp.presentation.component.AppTopBar
import com.rxxskh.moneymanagerapp.presentation.navigation.Screen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background
import com.rxxskh.moneymanagerapp.presentation.ui.theme.BlueGradient
import com.rxxskh.moneymanagerapp.presentation.ui.theme.title2

@Composable
fun ProfileScreen(
    vm: ProfileViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        AppTopBar(text = stringResource(id = R.string.profile_title))
        Text(
            text = stringResource(id = R.string.current_user) + " " + vm.name,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            color = Color.White,
            style = MaterialTheme.typography.title2
        )
        AppLargeButton(
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            enabled = vm.isActiveButton(),
            onClick = { vm.logout { navController.navigate(Screen.LoginScreen.route) } },
            gradient = BlueGradient,
            text = stringResource(id = R.string.exit)
        )
    }
}