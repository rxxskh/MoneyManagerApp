package com.rxxskh.data.transaction.repository

import com.rxxskh.data.account.local.AccountLocalDataSource
import com.rxxskh.data.account.remote.model.toAccountRemoteData
import com.rxxskh.data.category.local.CategoryLocalDataSource
import com.rxxskh.data.transaction.local.TransactionLocalDataSource
import com.rxxskh.data.transaction.remote.TransactionRemoteDataSource
import com.rxxskh.data.transaction.remote.model.toTransaction
import com.rxxskh.data.transaction.remote.model.toTransactionRemoteData
import com.rxxskh.data.user.local.UserLocalDataSource
import com.rxxskh.domain.transaction.model.Transaction
import com.rxxskh.domain.transaction.repository.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionLocalDataSource: TransactionLocalDataSource,
    private val transactionRemoteDataSource: TransactionRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val accountLocalDataSource: AccountLocalDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource
) : TransactionRepository {

    override suspend fun loadData() {
        transactionLocalDataSource.save(
            transactionList = transactionRemoteDataSource.getTransactions(
                userId = userLocalDataSource.get().user_id!!
            ).map {
                it.toTransaction(
                    account = accountLocalDataSource.getById(accountId = it.account_id!!),
                    category = categoryLocalDataSource.getById(categoryId = it.category_id!!)
                )
            }
        )
    }

    override suspend fun getTransactions(): List<Transaction> {
        return transactionLocalDataSource.get().asReversed()
    }

    override suspend fun addTransaction(transaction: Transaction) {
        val newTransaction = transaction
            .copy(transactionId = transactionRemoteDataSource.pushTransactionKey())
            .copy(userId = userLocalDataSource.get().user_id!!)

        transactionLocalDataSource.push(transaction = newTransaction)
        accountLocalDataSource.edit(editedAccount = transaction.account)

        transactionRemoteDataSource.pushTransaction(transactionRemoteData = newTransaction.toTransactionRemoteData())
        transactionRemoteDataSource.pushAccount(accountRemoteData = transaction.account.toAccountRemoteData())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionLocalDataSource.delete(transaction = transaction)
        transactionRemoteDataSource.deleteTransaction(transactionRemoteData = transaction.toTransactionRemoteData())
    }
}