package com.rxxskh.data.transaction.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.rxxskh.data.transaction.local.model.TransactionList
import com.rxxskh.domain.transaction.model.Transaction
import com.rxxskh.utils.SharedPreferenceProvider
import javax.inject.Inject

class TransactionLocalDataSource @Inject constructor(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {

    suspend fun save(transactionList: List<Transaction>) {
        with(sharedPreferences.edit()) {
            val json: String =
                gson.toJson(TransactionList(list = transactionList))
            putString(SharedPreferenceProvider.TRANSACTIONS, json)
            apply()
        }
    }

    suspend fun get(): List<Transaction> {
        val json: String? = sharedPreferences.getString(SharedPreferenceProvider.TRANSACTIONS, null)
        return if (json != null) {
            gson.fromJson(json, TransactionList::class.java).list
        } else {
            emptyList()
        }
    }

    suspend fun push(transaction: Transaction) {
        val list = get().toMutableList()
        list.add(transaction)
        save(transactionList = list)
    }

    suspend fun delete(transaction: Transaction) {
        val list = get().toMutableList()
        list.remove(transaction)
        save(transactionList = list)
    }
}