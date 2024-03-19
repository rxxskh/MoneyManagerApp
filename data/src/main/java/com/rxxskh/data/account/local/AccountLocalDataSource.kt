package com.rxxskh.data.account.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.rxxskh.data.account.local.model.AccountList
import com.rxxskh.domain.account.model.Account
import com.rxxskh.utils.SharedPreferenceProvider
import javax.inject.Inject

class AccountLocalDataSource @Inject constructor(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {

    suspend fun save(accountList: List<Account>) {
        val listFriends = AccountList(list = accountList)
        with(sharedPreferences.edit()) {
            val json: String = gson.toJson(listFriends)
            putString(SharedPreferenceProvider.ACCOUNTS_DATA_PREFS_KEY, json)
            apply()
        }
    }

    suspend fun get(): List<Account> {
        val json: String? =
            sharedPreferences.getString(SharedPreferenceProvider.ACCOUNTS_DATA_PREFS_KEY, null)
        return if (json != null) {
            gson.fromJson(json, AccountList::class.java).list
        } else {
            emptyList()
        }
    }

    suspend fun getById(accountId: String): Account {
        return get().first { it.accountId == accountId }
    }

    suspend fun edit(editedAccount: Account) {
        val list = get().toMutableList()
        val old = list.first { it.accountId == editedAccount.accountId }
        list[list.indexOf(old)] = editedAccount
        save(accountList = list)
    }
}