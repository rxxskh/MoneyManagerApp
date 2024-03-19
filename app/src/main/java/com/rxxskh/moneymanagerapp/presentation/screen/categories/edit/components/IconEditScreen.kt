package com.rxxskh.moneymanagerapp.presentation.screen.categories.edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.common.Constants
import com.rxxskh.moneymanagerapp.presentation.component.AppTopBar
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background
import com.rxxskh.moneymanagerapp.presentation.ui.theme.DarkBlue
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightBlue
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground

@Composable
fun IconEditScreen(
    selectedIcon: Int,
    colorIndex: Int,
    onIconClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        AppTopBar(
            text = stringResource(id = R.string.icon_edit_title),
            leftButtonIconId = R.drawable.ic_arrow_back,
            onLeftButtonClick = { onBackClick() }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = Constants.CATEGORY_ICONS) { icon ->
                IconItem(
                    isSelected = icon == selectedIcon,
                    icon = icon,
                    color = Constants.COLORS[colorIndex],
                    onIconClick = onIconClick
                )
            }
        }
    }
}

@Composable
fun IconItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    icon: Int,
    color: Color,
    onIconClick: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(54.dp)
                .background(
                    color = if (isSelected) LightBlue else DarkBlue,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(4.dp)
                .background(
                    color = if (isSelected) DarkBlue else SecondBackground,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { onIconClick(icon) }
                ),
            tint = color
        )
    }
}