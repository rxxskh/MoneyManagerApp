package com.rxxskh.domain.account.repository

import com.rxxskh.domain.account.model.Account
import com.rxxskh.domain.user.model.User

interface AccountRepository {

    suspend fun loadData()
    suspend fun applyAccount(account: Account, newAccountMembers: List<User>, oldAccountMembers: List<User>)
    suspend fun deleteAccount(accountId: String)
    suspend fun getAccounts(): List<Account>
    suspend fun getAccountById(accountId: String): Account
}