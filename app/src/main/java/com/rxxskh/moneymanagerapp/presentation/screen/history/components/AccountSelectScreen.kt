package com.rxxskh.moneymanagerapp.presentation.screen.history.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.rxxskh.domain.account.model.Account
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.common.toRussianCurrency
import com.rxxskh.moneymanagerapp.presentation.component.AppTopBar
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background
import com.rxxskh.moneymanagerapp.presentation.ui.theme.DarkGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base2
import com.rxxskh.moneymanagerapp.presentation.ui.theme.title2
import com.rxxskh.moneymanagerapp.presentation.ui.theme.title3

@Composable
fun AccountSelectScreen(
    accounts: List<Account>,
    selectedAccounts: List<Account>,
    onAccountClick: (Account) -> Unit,
    onSelectAllClick: () -> Unit,
    onRemoveAllClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        AppTopBar(
            text = stringResource(id = R.string.month),
            leftButtonIconId = R.drawable.ic_arrow_back,
            onLeftButtonClick = { onBackClick() }
        )
        Row {
            HistoryMarkButton(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = stringResource(id = R.string.select_all),
                onClick = { onSelectAllClick() }
            )
            HistoryMarkButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.remove_all),
                onClick = { onRemoveAllClick() }
            )
        }
        LazyColumn {
            items(items = accounts) { account ->
                HistoryAccountItem(
                    isSelected = selectedAccounts.contains(account),
                    account = account,
                    onAccountClick = onAccountClick
                )
            }
        }
    }
}

@Composable
fun HistoryAccountItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    account: Account,
    onAccountClick: (Account) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(SecondBackground)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { onAccountClick(account) }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Background)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = account.accountName,
                    color = Color.White,
                    style = MaterialTheme.typography.base2
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = account.accountBalance.toRussianCurrency(),
                        color = Color.White,
                        style = MaterialTheme.typography.title2
                    )
                    if (isSelected) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check_mark),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = DarkGray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryMarkButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(50.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        border = BorderStroke(width = 1.dp, color = DarkGray),
        contentPadding = PaddingValues(),
        onClick = { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Background,
                style = MaterialTheme.typography.title3
            )
        }
    }
}