package com.rxxskh.data.account.remote.model

import com.rxxskh.data.user.remote.model.UserData
import com.rxxskh.data.user.remote.model.toUser
import com.rxxskh.domain.account.model.Account

data class AccountRemoteData(
    var account_id: String? = null,
    val account_name: String? = null,
    val account_balance: Long? = null,
)

fun Account.toAccountRemoteData(): AccountRemoteData = AccountRemoteData(
    account_id = accountId,
    account_name = accountName,
    account_balance = accountBalance
)

fun Pair<AccountRemoteData, List<UserData>>.toAccount() = Account(
    accountId = this.first.account_id ?: "",
    accountName = this.first.account_name ?: "",
    accountBalance = this.first.account_balance ?: 0,
    accountMembers = this.second.map { it.toUser() }
)