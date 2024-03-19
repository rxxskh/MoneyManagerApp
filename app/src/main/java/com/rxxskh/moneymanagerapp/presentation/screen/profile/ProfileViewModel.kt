package com.rxxskh.moneymanagerapp.presentation.screen.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxxskh.domain.user.usecase.GetUserUseCase
import com.rxxskh.domain.user.usecase.LogoutUserUseCase
import com.rxxskh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUserUseCase: LogoutUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    var name by mutableStateOf<String>("")
        private set

    init {
        loadData()
    }

    fun isActiveButton(): Boolean = name.isNotEmpty()

    fun logout(onComplete: () -> Unit) {
        logoutUserUseCase().onEach { }.launchIn(viewModelScope)
            .invokeOnCompletion { onComplete() }
    }

    private fun loadData() {
        getUserUseCase().onEach {
            when (it) {
                is Resource.Error -> {}
                is Resource.Success -> {
                    name = it.data!!.userLogin
                }
            }
        }.launchIn(viewModelScope)
    }
}