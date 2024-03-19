package com.rxxskh.data.account.repository

import com.rxxskh.data.account.local.AccountLocalDataSource
import com.rxxskh.data.account.remote.AccountRemoteDataSource
import com.rxxskh.data.account.remote.model.toAccount
import com.rxxskh.data.account.remote.model.toAccountRemoteData
import com.rxxskh.data.user.local.UserLocalDataSource
import com.rxxskh.data.user.remote.model.toUserData
import com.rxxskh.domain.account.model.Account
import com.rxxskh.domain.account.repository.AccountRepository
import com.rxxskh.domain.user.model.User
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource,
    private val accountLocalDataSource: AccountLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : AccountRepository {

    override suspend fun loadData() {
        accountLocalDataSource.save(
            accountList = accountRemoteDataSource.getAccounts(
                userId = userLocalDataSource.get().user_id!!
            ).map { it.toAccount() }
        )
    }

    override suspend fun applyAccount(account: Account, newAccountMembers: List<User>) {
        accountRemoteDataSource.applyAccount(
            userId = userLocalDataSource.get().user_id!!,
            accountRemoteData = account.toAccountRemoteData(),
            newAccountMembers = newAccountMembers.map { it.toUserData() })
        loadData()
    }

    override suspend fun deleteAccount(accountId: String) {
        accountLocalDataSource.save(
            accountList = accountLocalDataSource.get()
                .filter { it.accountId != accountId }
        )
        accountRemoteDataSource.deleteAccount(accountId = accountId)
    }

    override suspend fun getAccounts(): List<Account> {
        return accountLocalDataSource.get()
    }

    override suspend fun getAccountById(accountId: String): Account {
        return accountLocalDataSource.getById(accountId = accountId)
    }
}