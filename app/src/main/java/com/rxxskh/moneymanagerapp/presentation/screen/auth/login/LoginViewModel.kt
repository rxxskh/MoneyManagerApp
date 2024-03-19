package com.rxxskh.moneymanagerapp.presentation.screen.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxxskh.domain.user.model.User
import com.rxxskh.domain.user.usecase.GetUserUseCase
import com.rxxskh.domain.user.usecase.LoginUserUseCase
import com.rxxskh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    var login by mutableStateOf<String>("")
        private set
    var password by mutableStateOf<String>("")
        private set
    var status by mutableStateOf<Boolean?>(null)
        private set
    var error by mutableStateOf<String>("")
        private set

    var logged by mutableStateOf<Boolean?>(null)

    init {
        checkLogged()
    }

    private fun checkLogged() {
        getUserUseCase().onEach {
            when (it) {
                is Resource.Error -> {
                    logged = false
                }
                is Resource.Success -> {
                    Log.e("AAA", it.data.toString())
                    if (it.data != null) {
                        logged = true
                    } else {
                        logged = false
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateLogin(input: String) {
        login = input.trim()
    }

    fun updatePassword(input: String) {
        password = input.trim()
    }

    fun clearLogin() {
        login = ""
    }

    fun clearPassword() {
        password = ""
    }

    fun isButtonEnabled(): Boolean =
        login.isNotEmpty() && password.isNotEmpty() && !isLoginWaiting()

    fun haveError(): Boolean = error.isNotEmpty()

    fun isLoginWaiting(): Boolean = status == false

    fun loginUser(onComplete: () -> Unit) {
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
            .invokeOnCompletion {
                if (status == true) {
                    onComplete()
                }
            }
    }
}