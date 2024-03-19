package com.rxxskh.moneymanagerapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground
import com.rxxskh.moneymanagerapp.presentation.ui.theme.title2

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    text: String,
    leftButtonIconId: Int? = null,
    onLeftButtonClick: () -> Unit = { },
    rightButtonIconId: Int? = null,
    onRightButtonClick: () -> Unit = { },
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(SecondBackground)
            .padding(top = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leftButtonIconId != null) {
            Icon(
                painter = painterResource(id = leftButtonIconId),
                contentDescription = null,
                tint = LightGray,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(16.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onLeftButtonClick() }
                    )
            )
        }
        Text(
            text = text.uppercase(),
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = if (leftButtonIconId == null) 32.dp else 0.dp,
                    end = if (rightButtonIconId == null) 32.dp else 0.dp
                ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.title2
        )
        if (rightButtonIconId != null) {
            Icon(
                painter = painterResource(id = rightButtonIconId),
                contentDescription = null,
                tint = LightGray,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(16.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onRightButtonClick() }
                    )
            )
        }
    }
}