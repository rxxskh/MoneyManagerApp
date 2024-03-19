package com.rxxskh.moneymanagerapp.presentation.screen.categories.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.common.Constants
import com.rxxskh.moneymanagerapp.presentation.component.AppTextField
import com.rxxskh.moneymanagerapp.presentation.component.AppTopBar
import com.rxxskh.moneymanagerapp.presentation.navigation.Screen
import com.rxxskh.moneymanagerapp.presentation.screen.categories.edit.components.ColorEditScreen
import com.rxxskh.moneymanagerapp.presentation.screen.categories.edit.components.IconEditScreen
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background
import com.rxxskh.moneymanagerapp.presentation.ui.theme.DarkGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightRed
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base2

@Composable
fun CategoryEditScreen(
    vm: CategoryEditViewModel = hiltViewModel(),
    navController: NavController,
    accountId: String,
    categoryId: String
) {
    vm.passData(
        accountId = accountId,
        categoryId = categoryId
    )

    if (vm.iconScreenStatus) {
        IconEditScreen(
            selectedIcon = vm.icon,
            colorIndex = vm.colorIndex,
            onIconClick = { vm.updateIcon(it) },
            onBackClick = { vm.updateIconScreenStatus() }
        )
    } else if (vm.colorScreenStatus) {
        ColorEditScreen(
            selectedIcon = vm.icon,
            colorIndex = vm.colorIndex,
            onColorClick = { vm.updateColor(it) },
            onBackClick = { vm.updateColorScreenStatus() }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            AppTopBar(
                text = stringResource(id = R.string.category_edit_title_new),
                leftButtonIconId = R.drawable.ic_arrow_back,
                onLeftButtonClick = {
                    navController.navigate(
                        Screen.CategoriesScreen.passAccountId(
                            accountId = accountId
                        )
                    )
                },
                rightButtonIconId = R.drawable.ic_check_mark,
                onRightButtonClick = {
                    vm.applyCategory(onComplete = {
                        navController.navigate(
                            Screen.CategoriesScreen.passAccountId(
                                accountId = accountId
                            )
                        )
                    })
                }
            )
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppTextField(
                    value = vm.name,
                    onValueChange = { vm.updateCategoryName(it) },
                    onClearClick = { vm.clearCategoryName() },
                    placeHolder = stringResource(id = R.string.name_placeholder)
                )
                EditPanel(
                    text = stringResource(id = R.string.category_edit_icon),
                    icon = vm.icon,
                    color = Constants.COLORS[vm.colorIndex],
                    onEditPanelClick = { vm.updateIconScreenStatus() }
                )
                EditPanel(
                    text = stringResource(id = R.string.category_edit_color),
                    icon = R.drawable.ic_circle,
                    color = Constants.COLORS[vm.colorIndex],
                    onEditPanelClick = { vm.updateColorScreenStatus() }
                )
                if (categoryId != Constants.NEW_TAG) {
                    Text(
                        text = stringResource(id = R.string.delete_category),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = {
                                    vm.deleteCategory(onComplete = {
                                        navController.navigate(
                                            Screen.CategoriesScreen.passAccountId(
                                                accountId = accountId
                                            )
                                        )
                                    })
                                }
                            ),
                        color = LightRed,
                        style = MaterialTheme.typography.base2
                    )
                }
            }
        }
    }
}

@Composable
fun EditPanel(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    color: Color,
    onEditPanelClick: () -> Unit = { }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(DarkGray)
            .padding(bottom = 1.dp)
            .background(SecondBackground)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { onEditPanelClick() }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(start = 16.dp),
            color = Color.White,
            style = MaterialTheme.typography.base2
        )
        Row(
            modifier = Modifier.padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = color
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = LightGray
            )
        }
    }
}