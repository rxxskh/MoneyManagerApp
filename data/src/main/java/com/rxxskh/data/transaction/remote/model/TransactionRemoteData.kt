package com.rxxskh.data.transaction.remote.model

import com.rxxskh.domain.account.model.Account
import com.rxxskh.domain.category.model.Category
import com.rxxskh.domain.transaction.model.Transaction
import com.rxxskh.domain.transaction.model.TransactionType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

data class TransactionRemoteData(
    val transaction_id: String? = null,
    val user_id: String? = null,
    val account_id: String? = null,
    val category_id: String? = null,
    val value: Long? = null,
    val type: String? = null,
    val date: String? = null
)

fun Transaction.toTransactionRemoteData() = TransactionRemoteData(
    transaction_id = transactionId,
    user_id = userId,
    account_id = account.accountId,
    category_id = category.categoryId,
    value = value,
    type = type.name,
    date = date.toUtc()
)

fun TransactionRemoteData.toTransaction(account: Account, category: Category) = Transaction(
    transactionId = transaction_id,
    userId = user_id ?: "",
    account = account,
    category = category,
    value = value ?: 0,
    type = TransactionType.valueOf(type ?: TransactionType.NONE.name),
    date = date?.toDate() ?: Date()
)

fun String.toDate(): Date {
    val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    utcFormat.timeZone = TimeZone.getTimeZone("UTC")
    return utcFormat.parse(this)!!
}

fun Date.toUtc(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat.format(this)
}
