package com.rxxskh.moneymanagerapp.presentation.screen.accounts.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxxskh.utils.Resource
import com.rxxskh.domain.account.model.Account
import com.rxxskh.domain.account.usecase.ApplyAccountUseCase
import com.rxxskh.domain.account.usecase.GetAccountByIdUseCase
import com.rxxskh.domain.friend.usecase.GetFriendsUseCase
import com.rxxskh.domain.user.model.User
import com.rxxskh.domain.user.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AccountEditViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getFriendsUseCase: GetFriendsUseCase,
    private val applyAccountUseCase: ApplyAccountUseCase,
    private val getAccountByIdUseCase: GetAccountByIdUseCase
) : ViewModel() {

    var name by mutableStateOf<String>("")
        private set
    var balance by mutableStateOf<String>("")
        private set

    var passedAccount by mutableStateOf<Account?>(null)
        private set
    private var passedAccountId by mutableStateOf<String?>(null)
        private set

    var authorizedUser by mutableStateOf<User?>(null)
        private set
    var friends by mutableStateOf<List<Pair<User, Boolean>>>(emptyList())
        private set


    var addingStatus by mutableStateOf<Boolean>(false)
        private set

    fun updateName(input: String) {
        name = input.replace("  ", " ")
    }

    fun updateBalance(input: String) {
        val newValue = input.trim().filter {
            it.isDigit()
        }
        balance = if (balance.isEmpty() && newValue == "0") balance else newValue
    }

    fun passAccountId(input: String) {
        if (passedAccountId == null) {
            passedAccountId = input
            if (passedAccountId == "new") loadData() else loadAccount()
        }
    }

    fun clearName() {
        name = ""
    }

    fun clearBalance() {
        balance = ""
    }

    fun addFuncActive(): Boolean = name.isNotEmpty() && balance.isNotEmpty() && !addingStatus

    fun selectFriend(input: Pair<User, Boolean>) {
        val newValue = friends.toMutableList()
        newValue.add(newValue.indexOf(input), Pair(input.first, !input.second))
        newValue.remove(input)
        friends = newValue
    }

    fun applyAccount(onComplete: () -> Unit) {
        addingStatus = true
        applyAccountUseCase(
            account = makeAccount(),
            newAccountMembers = getNewAccountMembers()
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                }

                is Resource.Error -> {
                }
            }

        }.launchIn(viewModelScope).invokeOnCompletion {
            onComplete()
        }
    }

    private fun makeAccount(): Account =
        if (passedAccount != null) {
            Account(
                accountId = passedAccount!!.accountId,
                accountName = name,
                accountBalance = balance.toLong(),
                accountMembers = passedAccount!!.accountMembers
            )
        } else Account(
            accountName = name,
            accountBalance = balance.toLong(),
        )

    private fun getNewAccountMembers(): List<User> =
        friends.filter { it.second }.filter {
            if (passedAccount != null) passedAccount!!.accountMembers.contains(it.first)
                .not() else true
        }.map { it.first }

    private fun loadAccount() {
        getAccountByIdUseCase(accountId = passedAccountId!!).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    passedAccount = result.data
                }

                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope).invokeOnCompletion {
            loadData()
        }
    }

    private fun loadData() {
        getUserUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    authorizedUser = result.data
                }

                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope).invokeOnCompletion {
            getFriendsUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        friends = result.data!!.map {
                            Pair(
                                it,
                                if (passedAccount != null) passedAccount!!.accountMembers.contains(
                                    it
                                ) else false
                            )
                        }
                    }

                    is Resource.Error -> {

                    }
                }
            }.launchIn(viewModelScope).invokeOnCompletion {
                if (passedAccount != null) {
                    updateName(passedAccount!!.accountName)
                    updateBalance(passedAccount!!.accountBalance.toString())
                }
            }
        }
    }
}