package com.rxxskh.moneymanagerapp.presentation.screen.history.components

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
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.presentation.component.AppTopBar
import com.rxxskh.moneymanagerapp.presentation.ui.theme.Background
import com.rxxskh.moneymanagerapp.presentation.ui.theme.DarkGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base2

@Composable
fun MonthSelectScreen(
    months: List<Month>,
    selectedMonths: List<Month>,
    onMonthClick: (Month) -> Unit,
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
            items(items = months) { month ->
                HistoryMonthItem(
                    isSelected = selectedMonths.contains(month),
                    month = month,
                    onMonthClick = onMonthClick
                )
            }
        }
    }
}

@Composable
fun HistoryMonthItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    month: Month,
    onMonthClick: (Month) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(SecondBackground)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { onMonthClick(month) }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Background),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = month.toString(),
                modifier = Modifier.padding(start = 16.dp),
                color = Color.White,
                style = MaterialTheme.typography.base2
            )
            if (isSelected) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_mark),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(16.dp),
                    tint = DarkGray
                )
            }
        }
    }
}