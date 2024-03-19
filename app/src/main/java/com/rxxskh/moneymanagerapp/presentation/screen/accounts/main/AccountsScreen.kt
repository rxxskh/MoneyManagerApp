package com.rxxskh.moneymanagerapp.presentation.screen.accounts.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rxxskh.domain.account.model.Account
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.common.toRussianCurrency
import com.rxxskh.moneymanagerapp.presentation.component.AppTopBar
import com.rxxskh.moneymanagerapp.presentation.navigation.Screen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightBlue
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base1
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base2
import com.rxxskh.moneymanagerapp.presentation.ui.theme.title2

@Composable
fun AccountsScreen(
    vm: AccountsViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        AppTopBar(
            text = stringResource(id = R.string.accounts_title),
            rightButtonIconId = R.drawable.ic_plus,
            onRightButtonClick = { navController.navigate(Screen.AccountEditScreen.passId(id = "new")) }
        )
        AccountList(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 32.dp),
            accounts = vm.accounts,
            onEditClicked = { navController.navigate(Screen.AccountEditScreen.passId(id = it)) },
            onDeleteClicked = { vm.deleteAccount(it) }
        )
    }
}

@Composable
fun AccountList(
    modifier: Modifier = Modifier,
    accounts: List<Account>,
    onEditClicked: (String) -> Unit = { },
    onDeleteClicked: (Account) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(items = accounts) { account ->
            AccountItem(
                modifier = Modifier.padding(bottom = if (accounts.indexOf(account) != accounts.size - 1) 12.dp else 0.dp),
                account = account,
                onEditClicked = onEditClicked,
                onDeleteClicked = onDeleteClicked
            )
        }
    }
}

@Composable
fun AccountItem(
    modifier: Modifier = Modifier,
    account: Account,
    onEditClicked: (String) -> Unit,
    onDeleteClicked: (Account) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SecondBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(top = 12.dp, bottom = 12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = null,
                    tint = LightGray,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = { onEditClicked(account.accountId) }
                        )
                )
                Text(
                    text = account.accountName,
                    color = Color.White,
                    style = MaterialTheme.typography.base1
                )
            }
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
            ) {
                Text(
                    text = account.accountBalance.toRussianCurrency(),
                    color = Color.White,
                    style = MaterialTheme.typography.title2
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    tint = LightGray,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = { onDeleteClicked(account) }
                        )
                )
            }
        }
        if (account.accountMembers.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.padding(start = 16.dp, top = 20.dp, end = 16.dp)
            ) {
                items(items = account.accountMembers) {
                    Box(
                        modifier = Modifier
                            .padding(
                                start = if (account.accountMembers.indexOf(it) != 0) 8.dp else 0.dp,
                            )
                            .background(
                                color = LightBlue,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it.userLogin,
                            modifier = Modifier.padding(
                                start = 6.dp,
                                top = 2.dp,
                                end = 6.dp,
                                bottom = 2.dp
                            ),
                            color = Color.White,
                            style = MaterialTheme.typography.base2
                        )
                    }
                }
            }
        }
    }
}