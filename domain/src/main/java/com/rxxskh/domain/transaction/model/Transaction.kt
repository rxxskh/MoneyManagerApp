package com.rxxskh.domain.transaction.model

import com.rxxskh.domain.account.model.Account
import com.rxxskh.domain.category.model.Category
import java.util.Date

data class Transaction(
    val transactionId: String? = null,
    val userId: String? = null,
    val account: Account,
    val category: Category,
    val transactionValue: Long,
    val operationType: OperationType,
    val transactionDate: Date,
)