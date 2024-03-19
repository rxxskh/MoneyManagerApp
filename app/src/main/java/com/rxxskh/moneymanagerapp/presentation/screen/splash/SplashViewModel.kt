package com.rxxskh.moneymanagerapp.presentation.screen.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxxskh.domain.account.usecase.LoadAccountDataUseCase
import com.rxxskh.domain.category.usecase.LoadCategoryDataUseCase
import com.rxxskh.domain.friend.usecase.LoadFriendDataUseCase
import com.rxxskh.domain.transaction.usecase.LoadTransactionDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loadFriendDataUseCase: LoadFriendDataUseCase,
    private val loadAccountDataUseCase: LoadAccountDataUseCase,
    private val loadCategoryDataUseCase: LoadCategoryDataUseCase,
    private val loadTransactionDataUseCase: LoadTransactionDataUseCase
) : ViewModel() {

    var status by mutableStateOf<Boolean>(false)
        private set

    init {
        loadFriendData()
    }

    fun resetStatus() {
        status = false
    }

    private fun loadFriendData() {
        loadFriendDataUseCase().onEach { }.launchIn(viewModelScope)
            .invokeOnCompletion {
                loadAccountDataUseCase().onEach { }.launchIn(viewModelScope)
                    .invokeOnCompletion {
                        loadCategoryDataUseCase().onEach { }.launchIn(viewModelScope)
                            .invokeOnCompletion {
                                loadTransactionDataUseCase().onEach { }.launchIn(viewModelScope)
                                    .invokeOnCompletion { status = true }
                            }
                    }
            }
    }
}