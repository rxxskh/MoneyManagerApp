package com.rxxskh.moneymanagerapp.presentation.screen.income

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxxskh.domain.account.model.Account
import com.rxxskh.domain.account.usecase.GetAccountsUseCase
import com.rxxskh.domain.category.model.Category
import com.rxxskh.domain.category.usecase.GetCategoriesUseCase
import com.rxxskh.domain.transaction.model.Transaction
import com.rxxskh.domain.transaction.model.OperationType
import com.rxxskh.domain.transaction.usecase.AddTransactionUseCase
import com.rxxskh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {

    var value by mutableStateOf<Int?>(null)
        private set

    var accounts by mutableStateOf<List<Account>>(emptyList())
        private set
    var selectedAccount by mutableStateOf<Account?>(null)
        private set

    var categories by mutableStateOf<List<Category>>(emptyList())
        private set
    var selectedCategory by mutableStateOf<Category?>(null)
        private set

    var addLoading by mutableStateOf<Boolean>(false)
        private set

    init {
        loadData()
    }

    fun incValue(input: Int) {
        var newValue = if (value == null) 0 else value
        if (newValue!! < Int.MAX_VALUE / 10 - 10) newValue = newValue * 10 + input
        value = newValue
    }

    fun decValue() {
        var newValue = value
        if (newValue != null) {
            newValue = value!! / 10
            if (newValue == 0) {
                newValue = null
            }
        }
        value = newValue
    }

    fun clearValue() {
        value = null
    }

    fun nextAccount() {
        selectedAccount = accounts[(accounts.indexOf(selectedAccount) + 1) % accounts.size]
        loadCategories()
    }

    fun previousAccount() {
        selectedAccount =
            accounts[(accounts.indexOf(selectedAccount) - 1 + accounts.size) % accounts.size]
        loadCategories()
    }

    fun selectCategory(input: Category) {
        selectedCategory = input
    }

    fun isCorrectInput(): Boolean =
        value != null && selectedAccount != null && selectedCategory != null && !addLoading

    fun addTransaction() {
        addLoading = true
        incAccountBalance(value!!)

        addTransactionUseCase(
            transaction = Transaction(
                account = selectedAccount!!,
                category = selectedCategory!!,
                transactionValue = value!!.toLong(),
                operationType = OperationType.INCOME,
                transactionDate = Date()
            )
        ).onEach {
            addLoading = false
        }.launchIn(viewModelScope)

        clearValue()
    }

    private fun incAccountBalance(input: Int) {
        selectedAccount =
            selectedAccount!!.copy(accountBalance = selectedAccount!!.accountBalance + input)
    }


    private fun loadData() {
        loadAccounts()
    }

    private fun loadAccounts() {
        getAccountsUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {}
                is Resource.Success -> {
                    accounts = result.data!!
                    if (accounts.isNotEmpty()) {
                        selectedAccount = accounts[0]
                        loadCategories()
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadCategories() {
        getCategoriesUseCase(accountId = if (selectedAccount!!.accountMembers.isEmpty()) null else selectedAccount!!.accountId).onEach { result ->
            when (result) {
                is Resource.Error -> {}
                is Resource.Success -> {
                    categories = result.data!!.filter { it.categoryType == OperationType.INCOME }
                    if (categories.isNotEmpty()) {
                        selectedCategory = categories[0]
                    } else {
                        selectedCategory = null
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}