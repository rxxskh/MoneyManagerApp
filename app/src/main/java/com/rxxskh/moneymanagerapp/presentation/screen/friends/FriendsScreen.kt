package com.rxxskh.moneymanagerapp.presentation.screen.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.presentation.component.AppLargeButton
import com.rxxskh.moneymanagerapp.presentation.component.AppTextField
import com.rxxskh.moneymanagerapp.presentation.component.AppTopBar
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background
import com.rxxskh.moneymanagerapp.presentation.ui.theme.BlueGradient
import com.rxxskh.moneymanagerapp.presentation.ui.theme.DarkRed
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightBlue
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base2
import com.rxxskh.moneymanagerapp.presentation.ui.theme.title3

@Composable
fun FriendsScreen(
    vm: FriendsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState()),
    ) {
        AppTopBar(
            text = stringResource(id = R.string.friends_title)
        )
        LazyColumn(
            modifier = Modifier
                .padding(if (vm.friendNames.isNotEmpty()) 16.dp else 0.dp)
                .height(if (vm.friendNames.size < 6) (vm.friendNames.size * 40 + (vm.friendNames.size - 1) * 16).dp else 244.dp)
        ) {
            items(items = vm.friendNames) { friend ->
                FriendsItem(
                    modifier = Modifier.padding(bottom = if (vm.friendNames.indexOf(friend) != vm.friendNames.size - 1) 16.dp else 0.dp),
                    friend = friend,
                    onDeleteClick = { vm.deleteFriend(it) }
                )
            }
        }
        FriendsAddPanel(
            modifier = Modifier.padding(all = 16.dp),
            value = vm.newFriendLogin,
            onValueChange = { vm.updateNewFriendLogin(it) },
            onClearClick = { vm.clearNewFriendLogin() },
            onAddClick = { vm.addFriend() },
            addButtonActive = vm.addFriendButtonActive,
            errorMessage = vm.addFriendErrorMessage
        )
    }
}

@Composable
fun FriendsAddPanel(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onAddClick: () -> Unit,
    addButtonActive: Boolean,
    errorMessage: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = stringResource(id = R.string.friends_add_title).uppercase(),
            color = Color.White,
            style = MaterialTheme.typography.title3
        )
        AppTextField(
            modifier = Modifier.padding(top = 16.dp),
            value = value,
            onValueChange = onValueChange,
            onClearClick = onClearClick,
            placeHolder = stringResource(id = R.string.friends_placeholder),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )
        Text(
            text = errorMessage,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            color = if (errorMessage.isEmpty()) Background else DarkRed,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.base2
        )
        AppLargeButton(
            modifier = Modifier.padding(top = 16.dp),
            enabled = value.isNotEmpty() && addButtonActive,
            onClick = { onAddClick() },
            gradient = BlueGradient,
            text = stringResource(id = if (addButtonActive) R.string.add_button else R.string.add_button_loading)
        )
    }
}

@Composable
fun FriendsItem(
    modifier: Modifier = Modifier,
    friend: String,
    onDeleteClick: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color = SecondBackground,
                shape = RoundedCornerShape(8.dp)
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
                tint = LightBlue
            )
            Text(
                text = friend,
                color = Color.White,
                style = MaterialTheme.typography.base2
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(16.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { onDeleteClick(friend) }
                ),
            tint = LightGray
        )
    }
}