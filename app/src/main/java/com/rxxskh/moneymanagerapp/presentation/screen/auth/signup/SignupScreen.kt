package com.rxxskh.moneymanagerapp.presentation.screen.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.presentation.component.AppLargeButton
import com.rxxskh.moneymanagerapp.presentation.component.AppTextField
import com.rxxskh.moneymanagerapp.presentation.navigation.Screen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background
import com.rxxskh.moneymanagerapp.presentation.ui.theme.BlueGradient
import com.rxxskh.moneymanagerapp.presentation.ui.theme.DarkRed
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightBlue
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base2
import com.rxxskh.moneymanagerapp.presentation.ui.theme.link
import com.rxxskh.moneymanagerapp.presentation.ui.theme.title2

@Composable
fun SignupScreen(
    vm: SignupViewModel = hiltViewModel(),
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.signup_title).uppercase(),
                modifier = Modifier.padding(start = 16.dp),
                color = Color.White,
                style = MaterialTheme.typography.title2
            )
            AppTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = vm.login,
                onValueChange = { vm.updateLogin(it) },
                onClearClick = { vm.clearLogin() },
                placeHolder = stringResource(id = R.string.login_placeholder),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            AppTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = vm.password,
                onValueChange = { vm.updatePassword(it) },
                onClearClick = { vm.clearPassword() },
                placeHolder = stringResource(id = R.string.password_placeholder),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            if (vm.haveError()) {
                Text(
                    text = vm.error,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    color = DarkRed,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.base2
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AppLargeButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    enabled = vm.isButtonEnabled(),
                    onClick = { vm.registerUser(onComplete = { navController.navigate(Screen.LoginScreen.route) }) },
                    gradient = BlueGradient,
                    text = stringResource(id = if (vm.isSignupWaiting()) R.string.signup_waiting else R.string.signup_button)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.signup_have_account),
                        color = LightGray,
                        style = MaterialTheme.typography.base2
                    )
                    Text(
                        text = stringResource(id = R.string.login_title),
                        modifier = Modifier.clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = {
                                navController.navigate(Screen.LoginScreen.route)
                            }
                        ),
                        color = LightBlue,
                        style = MaterialTheme.typography.link
                    )
                }
            }
        }
    }
}