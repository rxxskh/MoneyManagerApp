package com.rxxskh.moneymanagerapp.presentation.screen.accounts.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rxxskh.domain.user.model.User
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.common.toRussianCurrency
import com.rxxskh.moneymanagerapp.presentation.component.AppLargeButton
import com.rxxskh.moneymanagerapp.presentation.component.AppTextField
import com.rxxskh.moneymanagerapp.presentation.component.AppTopBar
import com.rxxskh.moneymanagerapp.presentation.navigation.Screen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background
import com.rxxskh.moneymanagerapp.presentation.ui.theme.BlueGradient
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightBlue
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base2
import com.rxxskh.moneymanagerapp.presentation.ui.theme.title3

@Composable
fun AccountEditScreen(
    vm: AccountEditViewModel = hiltViewModel(),
    navController: NavController,
    accountId: String
) {
    vm.passAccountId(accountId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppTopBar(
                text = stringResource(id = if (vm.passedAccount == null) R.string.account_edit_title_new else R.string.account_edit_title),
                leftButtonIconId = R.drawable.ic_arrow_back,
                onLeftButtonClick = { navController.navigate(Screen.AccountsScreen.route) }
            )
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppTextField(
                    value = vm.name,
                    onValueChange = { vm.updateName(it) },
                    onClearClick = { vm.clearName() },
                    placeHolder = stringResource(id = R.string.name_placeholder),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                AppTextField(
                    value = if (vm.balance.isEmpty()) vm.balance else vm.balance.toLong().toRussianCurrency(),
                    onValueChange = { vm.updateBalance(it) },
                    onClearClick = { vm.clearBalance() },
                    placeHolder = stringResource(id = R.string.account_balance_placeholder),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
            Text(
                text = stringResource(id = R.string.account_members_title).uppercase(),
                modifier = Modifier.padding(start = 16.dp),
                color = Color.White,
                style = MaterialTheme.typography.title3
            )
            LazyColumn(
                modifier = Modifier.height(if (vm.friends.size < 6) (vm.friends.size * 40 + (vm.friends.size - 1) * 16).dp else 244.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(items = vm.friends) { friend ->
                    MemberItem(
                        modifier = Modifier.padding(bottom = if (vm.friends.indexOf(friend) != vm.friends.size - 1) 16.dp else 0.dp),
                        friend = friend,
                        onMemberClick = { vm.selectFriend(it) }
                    )
                }
            }
        }
        AppLargeButton(
            modifier = Modifier.padding(all = 16.dp),
            enabled = vm.addFuncActive(),
            onClick = {
                vm.applyAccount(onComplete = { navController.navigate(Screen.AccountsScreen.route) })
            },
            gradient = BlueGradient,
            text = stringResource(id = if (vm.passedAccount == null) (if (vm.addingStatus) R.string.add_button_loading else R.string.add_button) else (if (vm.addingStatus) R.string.edit_button_loading else R.string.edit_button))
        )
    }
}

@Composable
fun MemberItem(
    modifier: Modifier = Modifier,
    friend: Pair<User, Boolean>,
    onMemberClick: (Pair<User, Boolean>) -> Unit = { }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color = if (friend.second) LightBlue else SecondBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { onMemberClick(friend) }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (friend.second) SecondBackground else LightBlue
            )
            Text(
                text = friend.first.userLogin,
                color = Color.White,
                style = MaterialTheme.typography.base2
            )
        }
        Icon(
            painter = painterResource(id = if (friend.second) R.drawable.ic_decline else R.drawable.ic_check_mark),
            contentDescription = null,
            modifier = Modifier
                .padding(end = if (friend.second) 18.dp else 16.dp)
                .size(if (friend.second) 12.dp else 16.dp),
            tint = LightGray
        )
    }
}