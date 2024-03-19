package com.rxxskh.data.user.remote.model

import com.rxxskh.domain.user.model.User

data class UserRemoteData(
    val user_id: String? = null,
    val user_login: String? = null,
    val user_password: String? = null
)

fun User.toUserRemoteData(): UserRemoteData = UserRemoteData(
    user_id = userId,
    user_login = userLogin,
    user_password = userPassword
)

fun UserRemoteData.toUser(): User = User(
    userId = user_id ?: "",
    userLogin = user_login ?: "",
    userPassword = user_password ?: ""
)