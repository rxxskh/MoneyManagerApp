package com.rxxskh.domain.account.model

import com.rxxskh.domain.user.model.User

data class Account(
    val accountId: String = "",
    val accountName: String = "",
    val accountBalance: Long = 0,
    val accountMembers: List<User> = emptyList()
)