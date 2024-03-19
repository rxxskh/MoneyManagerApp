package com.rxxskh.moneymanagerapp.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

val Typography.title2: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        )
    }

val Typography.title3: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        )
    }

val Typography.base1: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    }

val Typography.base2: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    }

val Typography.link: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline
        )
    }