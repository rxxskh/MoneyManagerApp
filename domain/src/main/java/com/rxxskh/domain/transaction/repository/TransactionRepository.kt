package com.rxxskh.domain.transaction.repository

import com.rxxskh.domain.transaction.model.Transaction

interface TransactionRepository {

    suspend fun loadData()
    suspend fun getTransactions(): List<Transaction>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
}