package com.rxxskh.moneymanagerapp.presentation.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.common.toRussianCurrency
import com.rxxskh.moneymanagerapp.presentation.ui.theme.DarkBlue
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base1
import com.rxxskh.moneymanagerapp.presentation.ui.theme.title2

sealed class NumKey(val onClick: () -> Unit) {
    class NumberNumKey(val text: String, onClick: () -> Unit) : NumKey(onClick = onClick)
    class IconNumKey(val iconId: Int, onClick: () -> Unit) : NumKey(onClick = onClick)
    object EmptyNumKey : NumKey(onClick = { })
}

@Composable
fun NumKeyboard(
    modifier: Modifier = Modifier,
    value: Int? = null,
    valueColor: Color,
    numKeys: List<NumKey> = emptyList(),
    onClearClick: () -> Unit = { }
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        ValueBox(
            modifier = Modifier.padding(bottom = 8.dp),
            value = value,
            valueColor = valueColor,
            onClearClick = { onClearClick() }
        )
        Card(
            shape = RectangleShape,
            border = BorderStroke(width = 2.dp, color = DarkBlue),
        ) {
            Column {
                repeat(numKeys.size / 3) { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        repeat(3) { column ->
                            NumKey(
                                modifier = Modifier.weight(1f),
                                numKey = numKeys[row * 3 + column]
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ValueBox(
    modifier: Modifier = Modifier,
    value: Int? = null,
    valueColor: Color,
    onClearClick: () -> Unit = { }
) {
    Card(
        modifier = modifier
            .height(35.dp),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        colors = CardDefaults.cardColors(containerColor = SecondBackground),
        border = BorderStroke(width = 2.dp, color = DarkBlue),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value?.toRussianCurrency() ?: stringResource(id = R.string.enter_number),
                modifier = Modifier
                    .padding(start = if (value != null) 28.dp else 0.dp)
                    .weight(1f),
                color = valueColor,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.title2
            )
            if (value != null) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_decline),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(12.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = { onClearClick() }
                        ),
                    tint = LightGray
                )
            }
        }
    }
}

@Composable
fun NumKey(
    modifier: Modifier = Modifier,
    numKey: NumKey
) {
    Card(
        modifier = modifier
            .height(35.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = SecondBackground),
        border = BorderStroke(width = 1.dp, color = DarkBlue)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { numKey.onClick() }
                ),
            contentAlignment = Alignment.Center
        ) {
            when (numKey) {
                is NumKey.NumberNumKey -> {
                    Text(
                        text = numKey.text,
                        color = LightGray,
                        style = MaterialTheme.typography.base1
                    )
                }

                is NumKey.IconNumKey -> {
                    Icon(
                        painter = painterResource(id = numKey.iconId),
                        contentDescription = null,
                        tint = LightGray,
                        modifier = Modifier
                            .padding(top = 1.dp)
                            .size(16.dp)
                    )
                }

                NumKey.EmptyNumKey -> {}
            }
        }
    }
}