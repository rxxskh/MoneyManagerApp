package com.rxxskh.moneymanagerapp.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rxxskh.moneymanagerapp.R
import com.rxxskh.moneymanagerapp.presentation.ui.theme.DarkGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.LightGray
import com.rxxskh.moneymanagerapp.presentation.ui.theme.SecondBackground
import com.rxxskh.moneymanagerapp.presentation.ui.theme.base2

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClearClick: () -> Unit = { },
    placeHolder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(
                width = 1.dp,
                color = DarkGray,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.CenterEnd
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxSize(),
            textStyle = MaterialTheme.typography.base2,
            placeholder = {
                Text(
                    text = placeHolder,
                    style = MaterialTheme.typography.base2
                )
            },
            keyboardOptions = keyboardOptions,
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = LightGray,
                unfocusedTextColor = LightGray,
                focusedPlaceholderColor = DarkGray,
                unfocusedPlaceholderColor = DarkGray,
                focusedContainerColor = SecondBackground,
                unfocusedContainerColor = SecondBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        if (value.isNotEmpty()) {
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