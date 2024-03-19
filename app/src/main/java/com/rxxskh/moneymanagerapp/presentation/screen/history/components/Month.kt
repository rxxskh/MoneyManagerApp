package com.rxxskh.moneymanagerapp.presentation.screen.history.components

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Month(
    val month: Int,
    val year: Int
) {
    override fun toString(): String {
        val date = Date()
        date.month = month
        date.year = year
        val format = SimpleDateFormat("LLLL yyyy", Locale("ru"))
        val result = format.format(date)
        return result.substring(0, 1).uppercase() + result.substring(1)
    }
}
