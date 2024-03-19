package com.rxxskh.moneymanagerapp.presentation.screen.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rxxskh.domain.transaction.model.Transaction
import com.rxxskh.domain.transaction.model.OperationType
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.common.Constants
import com.rxxskh.moneymanagerapp.common.toRussianCurrency
import com.rxxskh.moneymanagerapp.common.toRussianDate
import com.rxxskh.moneymanagerapp.presentation.component.AppTopBar
import com.rxxskh.moneymanagerapp.presentation.navigation.Screen
import com.rxxskh.moneymanagerapp.presentation.screen.history.components.AccountSelectScreen
import com.rxxskh.moneymanagerapp.presentation.screen.history.components.MonthSelectScreen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background
import com.rxxskh.moneymanagerapp.presentation.ui.theme.DarkGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGreen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightRed
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base2
import com.rxxskh.moneymanagerapp.presentation.ui.theme.title2

@Composable
fun HistoryScreen(
    vm: HistoryViewModel = hiltViewModel(),
    navController: NavController,
    historyType: String
) {
    vm.passHistoryType(historyType)

    if (vm.accountSelectScreenStatus) {
        AccountSelectScreen(
            accounts = vm.accounts,
            selectedAccounts = vm.selectedAccounts,
            onAccountClick = { vm.selectAccount(it) },
            onSelectAllClick = { vm.selectAllAccounts() },
            onRemoveAllClick = { vm.removeAllAccounts() },
            onBackClick = { vm.changeAccountSelectScreenStatus() }
        )
    } else if (vm.monthSelectScreenStatus) {
        MonthSelectScreen(
            months = vm.months,
            selectedMonths = vm.selectedMonths,
            onMonthClick = { vm.selectMonth(it) },
            onSelectAllClick = { vm.selectAllMonths() },
            onRemoveAllClick = { vm.removeAllMonths() },
            onBackClick = { vm.changeMonthSelectScreenStatus() }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            AppTopBar(
                text = stringResource(id = R.string.history),
                leftButtonIconId = R.drawable.ic_arrow_back,
                onLeftButtonClick = {
                    when (vm.passedHistoryType) {
                        OperationType.INCOME -> navController.navigate(Screen.IncomeScreen.route)
                        OperationType.EXPENSE -> navController.navigate(Screen.ExpenseScreen.route)
                        else -> {}
                    }
                }
            )
            HistoryOptions(
                modifier = Modifier.padding(all = 16.dp),
                text = if (vm.selectedMonths.size == 1) vm.selectedMonths[0].toString() else "Выбрано месяцев: ${vm.selectedMonths.size}",
                onOptionClick = { vm.changeMonthSelectScreenStatus() }
            )
            HistoryOptions(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Выбрано счетов: ${vm.selectedAccounts.size}",
                onOptionClick = { vm.changeAccountSelectScreenStatus() }
            )
            LazyColumn(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                items(items = vm.showTransactions) { transaction ->
                    TransactionItem(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = if (vm.showTransactions.indexOf(transaction) == vm.showTransactions.lastIndex) 0.dp else 16.dp),
                        transaction = transaction,
                        operationType = vm.passedHistoryType,
                        onDeleteClick = { vm.deleteTransaction(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryOptions(
    modifier: Modifier = Modifier,
    text: String,
    onOptionClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { onOptionClick() }
            ),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = LightGray),
        border = BorderStroke(width = 1.dp, color = DarkGray),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = Background,
                    style = MaterialTheme.typography.title2
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(16.dp),
                tint = DarkGray
            )
        }
    }
}

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    operationType: OperationType,
    onDeleteClick: (Transaction) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = SecondBackground)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = transaction.category.categoryIcon),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Constants.COLORS[transaction.category.categoryColor]
                    )
                    Text(
                        text = transaction.category.categoryName,
                        color = Color.White,
                        style = MaterialTheme.typography.title2
                    )
                }
                Text(
                    text = transaction.account.accountName,
                    color = LightGray,
                    style = MaterialTheme.typography.base2
                )
            }
            Row(
                modifier = Modifier.padding(end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = when (operationType) {
                            OperationType.INCOME -> transaction.transactionValue
                            OperationType.EXPENSE -> -transaction.transactionValue
                            else -> 0
                        }.toRussianCurrency(),
                        color = when (operationType) {
                            OperationType.INCOME -> LightGreen
                            OperationType.EXPENSE -> LightRed
                            else -> Color.White
                        },
                        style = MaterialTheme.typography.title2
                    )
                    Text(
                        text = transaction.transactionDate.toRussianDate(),
                        color = LightGray,
                        style = MaterialTheme.typography.base2
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = { onDeleteClick(transaction) }
                        ),
                    tint = LightGray
                )
            }
        }
    }
}