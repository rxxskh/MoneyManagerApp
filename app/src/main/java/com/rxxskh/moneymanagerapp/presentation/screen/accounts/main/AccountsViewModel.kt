package com.rxxskh.moneymanagerapp.presentation.screen.accounts.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxxskh.utils.Resource
import com.rxxskh.domain.account.model.Account
import com.rxxskh.domain.account.usecase.DeleteAccountUseCase
import com.rxxskh.domain.account.usecase.GetAccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    var accounts by mutableStateOf<List<Account>>(emptyList())
        private set

    init {
        loadData()
    }

    fun deleteAccount(input: Account) {
        val newValue = accounts.toMutableList()
        newValue.remove(input)
        accounts = newValue
        deleteAccountUseCase(accountId = input.accountId).launchIn(viewModelScope)
    }

    private fun loadData() {
        getAccountsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    accounts = result.data!!
                }

                is Resource.Error -> {

                }
            }

        }.launchIn(viewModelScope)
    }
}