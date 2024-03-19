package com.rxxskh.data.account.remote

import com.rxxskh.data.account.remote.model.AccountMemberData
import com.rxxskh.data.account.remote.model.AccountRemoteData
import com.rxxskh.data.user.remote.model.UserRemoteData
import com.rxxskh.utils.FirebaseReferencesProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AccountRemoteDataSource @Inject constructor() {

    suspend fun applyAccount(
        userId: String,
        accountRemoteData: AccountRemoteData,
        newAccountMembers: List<UserRemoteData>,
        oldAccountMembers: List<UserRemoteData>
    ) {
        val accountKey =
            accountRemoteData.account_id!!.ifEmpty { FirebaseReferencesProvider.ACCOUNTS_REF.push().key!! }
        if (accountRemoteData.account_id!!.isEmpty()) {
            accountRemoteData.account_id = accountKey
            saveAccountMember(accountId = accountKey, userId = userId)
        }
        newAccountMembers.forEach {
            saveAccountMember(
                accountId = accountKey,
                userId = it.user_id!!
            )
        }
        oldAccountMembers.forEach {
            deleteAccountMember(
                accountId = accountKey,
                userId = it.user_id!!
            )
        }
        FirebaseReferencesProvider.ACCOUNTS_REF.child(accountKey).setValue(accountRemoteData)
    }

    suspend fun deleteAccount(accountId: String) {
        FirebaseReferencesProvider.ACCOUNTS_REF.child(accountId).removeValue().await()
        suspendCoroutine { continuation ->
            val result = mutableListOf<String>()
            FirebaseReferencesProvider.ACCOUNT_MEMBERS_REF.get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        for (children in snapshot.children) {
                            val data = children.getValue(AccountMemberData::class.java)
                            if (data != null) {
                                if (data.account_id == accountId) {
                                    result.add(children.key!!)
                                }
                            }
                        }
                    }
                    continuation.resume(result)
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }.forEach {
            FirebaseReferencesProvider.ACCOUNT_MEMBERS_REF.child(it).removeValue().await()
        }
    }

    suspend fun getAccounts(userId: String): List<Pair<AccountRemoteData, List<UserRemoteData>>> {
        val accountMembers = suspendCoroutine { continuation ->
            val result = mutableListOf<AccountMemberData>()
            FirebaseReferencesProvider.ACCOUNT_MEMBERS_REF.get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        for (children in snapshot.children) {
                            val data = children.getValue(AccountMemberData::class.java)
                            if (data != null) {
                                result.add(data)
                            }
                        }
                    }
                    continuation.resume(result)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
        val accounts = suspendCoroutine { continuation ->
            val accountIds = accountMembers.filter { it.user_id == userId }.map { it.account_id }
            val result = mutableListOf<AccountRemoteData>()
            FirebaseReferencesProvider.ACCOUNTS_REF.get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        for (children in snapshot.children) {
                            val data = children.getValue(AccountRemoteData::class.java)
                            if (data != null) {
                                if (accountIds.contains(data.account_id)) {
                                    result.add(data)
                                }
                            }
                        }
                    }
                    continuation.resume(result)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
        return accounts.map { accountData ->
            Pair(accountData, suspendCoroutine { continuation ->
                val result = mutableListOf<UserRemoteData>()
                val memberIds =
                    accountMembers
                        .filter { it.account_id == accountData.account_id && it.user_id != userId }
                        .map { it.user_id }
                FirebaseReferencesProvider.USERS_REF.get()
                    .addOnSuccessListener { snapshot ->
                        if (snapshot.exists()) {
                            for (children in snapshot.children) {
                                val data = children.getValue(UserRemoteData::class.java)
                                if (data != null) {
                                    if (memberIds.contains(data.user_id)) {
                                        result.add(data)
                                    }
                                }
                            }
                        }
                        continuation.resume(result)
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            })
        }
    }

    private suspend fun saveAccountMember(accountId: String, userId: String) {
        val key = FirebaseReferencesProvider.ACCOUNT_MEMBERS_REF.push().key!!
        FirebaseReferencesProvider.ACCOUNT_MEMBERS_REF.child(key).setValue(
            AccountMemberData(
                account_id = accountId,
                user_id = userId
            )
        )
    }

    private suspend fun deleteAccountMember(accountId: String, userId: String) {
        val accountMemberKey = suspendCoroutine { continuation ->
            var result: String? = null
            FirebaseReferencesProvider.ACCOUNT_MEMBERS_REF.get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        for (children in snapshot.children) {
                            val data = children.getValue(AccountMemberData::class.java)
                            if (data != null) {
                                if (data.account_id == accountId && data.user_id == userId) {
                                    result = children.key
                                    break
                                }
                            }
                        }
                    }
                    continuation.resume(result)
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
        if (accountMemberKey != null) {
            FirebaseReferencesProvider.ACCOUNT_MEMBERS_REF
                .child(accountMemberKey).removeValue()
                .await()
        }
    }
}