package com.rxxskh.moneymanagerapp.presentation.screen.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxxskh.utils.Resource
import com.rxxskh.domain.user.model.User
import com.rxxskh.domain.user.usecase.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    var login by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var status by mutableStateOf<Boolean?>(null)
        private set
    var error by mutableStateOf("")
        private set

    fun updateLogin(input: String) {
        login = input.trim()
    }

    fun updatePassword(input: String) {
        password = input.trim()
    }

    fun isButtonEnabled(): Boolean = login.isNotEmpty() && password.isNotEmpty() && status != false

    fun haveError(): Boolean = error.isNotEmpty()

    fun isLoginWaiting(): Boolean = status == false

    fun loginUser() {
        val user = User(userLogin = login, userPassword = password)
        status = false
        loginUserUseCase(user = user).onEach {
            when (it) {
                is Resource.Success -> {
                    status = it.data
                    error = ""
                }
                is Resource.Error -> {
                    status = null
                    error = it.message ?: "Неизвестная ошибка"
                }
            }
        }.launchIn(viewModelScope)
    }
}