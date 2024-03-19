package com.rxxskh.moneymanagerapp.common

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toRussianCurrency(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    numberFormat.maximumFractionDigits = 0
    return numberFormat.format(this) + " ₽"
}

fun Int.toRussianCurrency(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    numberFormat.maximumFractionDigits = 0
    return numberFormat.format(this) + " ₽"
}

fun Date.toRussianDate(): String {
    val localFormat = SimpleDateFormat("dd MMMM HH:mm", Locale("ru"))
    return localFormat.format(this)
}