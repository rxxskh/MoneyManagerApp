package com.rxxskh.data.user.remote.model

import com.rxxskh.domain.user.model.User

data class UserData(
    val user_id: String? = null,
    val user_login: String? = null,
    val user_password: String? = null
)

fun User.toUserData(): UserData = UserData(
    user_id = userId,
    user_login = userLogin,
    user_password = userPassword
)

fun UserData.toUser(): User = User(
    userId = user_id ?: "",
    userLogin = user_login ?: "",
    userPassword = user_password ?: ""
)