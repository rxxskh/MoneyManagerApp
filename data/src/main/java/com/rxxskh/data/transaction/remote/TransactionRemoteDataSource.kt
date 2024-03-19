package com.rxxskh.data.transaction.remote

import com.rxxskh.data.account.remote.model.AccountRemoteData
import com.rxxskh.data.transaction.remote.model.TransactionRemoteData
import com.rxxskh.utils.FirebaseReferencesProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TransactionRemoteDataSource @Inject constructor() {

    suspend fun pushTransactionKey(): String =
        FirebaseReferencesProvider.TRANSACTION_REF.push().key!!

    suspend fun pushTransaction(transactionRemoteData: TransactionRemoteData) {
        FirebaseReferencesProvider.TRANSACTION_REF
            .child(transactionRemoteData.transaction_id!!)
            .setValue(transactionRemoteData)
    }

    suspend fun pushAccount(accountRemoteData: AccountRemoteData) {
        FirebaseReferencesProvider.ACCOUNTS_REF
            .child(accountRemoteData.account_id!!)
            .setValue(accountRemoteData)
    }

    suspend fun getTransactions(userId: String): List<TransactionRemoteData> {
        return suspendCoroutine { continuation ->
            val result = mutableListOf<TransactionRemoteData>()
            FirebaseReferencesProvider.TRANSACTION_REF.get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        for (children in snapshot.children) {
                            val data = children.getValue(TransactionRemoteData::class.java)
                            if (data != null) {
                                if (data.user_id == userId) {
                                    result.add(data)
                                }
                            }
                        }
                    }
                    continuation.resume(result)
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    suspend fun deleteTransaction(transactionRemoteData: TransactionRemoteData) {
        FirebaseReferencesProvider.TRANSACTION_REF
            .child(transactionRemoteData.transaction_id!!)
            .removeValue().await()
    }
}