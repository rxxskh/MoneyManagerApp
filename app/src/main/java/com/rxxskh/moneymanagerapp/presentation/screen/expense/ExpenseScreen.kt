package com.rxxskh.moneymanagerapp.presentation.screen.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rxxskh.domain.transaction.model.TransactionType
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.presentation.component.AppLargeButton
import com.rxxskh.moneymanagerapp.presentation.component.AppTopBar
import com.rxxskh.moneymanagerapp.presentation.component.NumKey
import com.rxxskh.moneymanagerapp.presentation.component.NumKeyboard
import com.rxxskh.moneymanagerapp.presentation.component.SelectingPanel
import com.rxxskh.moneymanagerapp.presentation.navigation.Screen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightRed
import com.rxxskh.moneymanagerapp.presentation.ui.theme.RedGradient
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base1

@Composable
fun ExpenseScreen(
    vm: ExpenseViewModel = hiltViewModel(),
    navController: NavController,
) {
    val numKeys = listOf(
        NumKey.NumberNumKey(text = "1", onClick = { vm.incValue(1) }),
        NumKey.NumberNumKey(text = "2", onClick = { vm.incValue(2) }),
        NumKey.NumberNumKey(text = "3", onClick = { vm.incValue(3) }),
        NumKey.NumberNumKey(text = "4", onClick = { vm.incValue(4) }),
        NumKey.NumberNumKey(text = "5", onClick = { vm.incValue(5) }),
        NumKey.NumberNumKey(text = "6", onClick = { vm.incValue(6) }),
        NumKey.NumberNumKey(text = "7", onClick = { vm.incValue(7) }),
        NumKey.NumberNumKey(text = "8", onClick = { vm.incValue(8) }),
        NumKey.NumberNumKey(text = "9", onClick = { vm.incValue(9) }),
        NumKey.EmptyNumKey,
        NumKey.NumberNumKey(text = "0", onClick = { vm.incValue(0) }),
        NumKey.IconNumKey(iconId = R.drawable.ic_erase, onClick = { vm.decValue() }),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            AppTopBar(
                text = stringResource(id = R.string.expense_title)
            )
            SelectingPanel(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                selectedCategory = vm.selectedCategory,
                categories = vm.categories,
                selectedAccount = vm.selectedAccount,
                onNextAccountClick = { vm.nextAccount() },
                onPreviousAccountClick = { vm.previousAccount() },
                onCategoryClick = { vm.selectCategory(it) },
                onEditCategoryClick = {
                    navController.navigate(
                        Screen.CategoriesScreen.passAccountId(
                            accountId = it
                        )
                    )
                }
            )
            NumKeyboard(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = vm.value,
                valueColor = LightRed,
                numKeys = numKeys,
                onClearClick = { vm.clearValue() }
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLargeButton(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                ),
                enabled = vm.isCorrectInput(),
                onClick = { vm.addTransaction() },
                gradient = RedGradient,
                text = stringResource(id = R.string.add_button)
            )
            Text(
                text = stringResource(id = R.string.history),
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = {
                            navController.navigate(
                                Screen.HistoryScreen.passHistoryType(
                                    historyType = TransactionType.EXPENSE.name
                                )
                            )
                        }
                    ),
                color = LightGray,
                style = MaterialTheme.typography.base1
            )
        }
    }
}