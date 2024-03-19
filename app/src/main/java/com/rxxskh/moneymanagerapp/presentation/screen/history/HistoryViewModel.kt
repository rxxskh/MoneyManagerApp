package com.rxxskh.moneymanagerapp.presentation.screen.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxxskh.domain.account.model.Account
import com.rxxskh.domain.account.usecase.GetAccountsUseCase
import com.rxxskh.domain.transaction.model.Transaction
import com.rxxskh.domain.transaction.model.OperationType
import com.rxxskh.domain.transaction.usecase.DeleteTransactionUseCase
import com.rxxskh.domain.transaction.usecase.GetTransactionsUseCase
import com.rxxskh.moneymanagerapp.presentation.screen.history.components.Month
import com.rxxskh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getAccountsUseCase: GetAccountsUseCase
) : ViewModel() {

    var passedHistoryType by mutableStateOf<OperationType>(OperationType.NONE)
    private var historyTypePassedStatus by mutableStateOf<Boolean>(false)

    var showTransactions by mutableStateOf<List<Transaction>>(emptyList())
        private set
    private var transactions by mutableStateOf<List<Transaction>>(emptyList())
        private set

    var accounts by mutableStateOf<List<Account>>(emptyList())
        private set
    var selectedAccounts by mutableStateOf<List<Account>>(emptyList())
        private set

    var months by mutableStateOf<List<Month>>(emptyList())
        private set
    var selectedMonths by mutableStateOf<List<Month>>(emptyList())
        private set

    var accountSelectScreenStatus by mutableStateOf<Boolean>(false)
        private set
    var monthSelectScreenStatus by mutableStateOf<Boolean>(false)
        private set

    init {
        loadData()
    }

    fun passHistoryType(input: String) {
        if (!historyTypePassedStatus) {
            passedHistoryType = when (input) {
                OperationType.INCOME.name -> OperationType.INCOME
                OperationType.EXPENSE.name -> OperationType.EXPENSE
                else -> OperationType.NONE
            }
            historyTypePassedStatus = true
        }
    }

    fun changeAccountSelectScreenStatus() {
        accountSelectScreenStatus = !accountSelectScreenStatus
        if (!accountSelectScreenStatus) {
            setShowTransactions()
        }
    }

    fun changeMonthSelectScreenStatus() {
        monthSelectScreenStatus = !monthSelectScreenStatus
        if (!monthSelectScreenStatus) {
            setShowTransactions()
        }
    }

    fun selectAccount(input: Account) {
        val newValue = selectedAccounts.toMutableList()
        if (selectedAccounts.contains(input)) {
            newValue.remove(input)
        } else {
            newValue.add(input)
        }
        selectedAccounts = newValue
    }

    fun selectAllAccounts() {
        selectedAccounts = accounts
    }

    fun removeAllAccounts() {
        selectedAccounts = emptyList()
    }

    fun selectMonth(input: Month) {
        val newValue = selectedMonths.toMutableList()
        if (selectedMonths.contains(input)) {
            newValue.remove(input)
        } else {
            newValue.add(input)
        }
        selectedMonths = newValue
    }

    fun selectAllMonths() {
        selectedMonths = months
    }

    fun removeAllMonths() {
        selectedMonths = emptyList()
    }

    fun deleteTransaction(input: Transaction) {
        vmDeleteTransaction(input)
        deleteTransactionUseCase(transaction = input).onEach { }.launchIn(viewModelScope)
    }

    private fun vmDeleteTransaction(input: Transaction) {
        val list = transactions.toMutableList()
        list.remove(input)
        transactions = list
        setShowTransactions()
    }

    private fun setShowTransactions() {
        showTransactions = transactions
            .filter {
                selectedAccounts
                    .map { selectedAccount -> selectedAccount.accountId }
                    .contains(it.account.accountId)
            }
            .filter {
                selectedMonths.contains(Month(it.transactionDate.month, it.transactionDate.year))
            }
            .filter {
                it.operationType == passedHistoryType
            }
    }

    private fun loadData() {
        getTransactionsUseCase().onEach {
            when (it) {
                is Resource.Error -> {}
                is Resource.Success -> {
                    transactions = it.data!!
                }
            }
        }.launchIn(viewModelScope)
            .invokeOnCompletion {
                getAccountsUseCase().onEach {
                    when (it) {
                        is Resource.Error -> {}

                        is Resource.Success -> {
                            accounts = it.data!!
                            selectedAccounts = accounts
                        }
                    }
                }.launchIn(viewModelScope)
                    .invokeOnCompletion {
                        months =
                            transactions.map { Month(month = it.transactionDate.month, year = it.transactionDate.year) }
                                .distinct()
                        if (months.isNotEmpty()) {
                            selectedMonths = listOf(months[0])
                        }
                        setShowTransactions()
                    }
            }
    }
}