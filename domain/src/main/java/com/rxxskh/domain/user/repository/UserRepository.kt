package com.rxxskh.domain.user.repository

import com.rxxskh.domain.user.model.User

interface UserRepository {

    suspend fun registerUser(user: User): Boolean
    suspend fun loginUser(user: User): Boolean
    suspend fun getUser(): User
    suspend fun checkUserLoggedIn(): Boolean
}