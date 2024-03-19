package com.rxxskh.data.user.repository

import com.rxxskh.data.user.local.UserLocalDataSource
import com.rxxskh.data.user.remote.UserRemoteDataSource
import com.rxxskh.data.user.remote.model.toUserRemoteData
import com.rxxskh.domain.user.model.User
import com.rxxskh.domain.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun registerUser(user: User): Boolean {
        return userRemoteDataSource.saveUser(userRemoteData = user.toUserRemoteData())
    }

    override suspend fun loginUser(user: User): Boolean {
        val userId = userRemoteDataSource.checkUser(userRemoteData = user.toUserRemoteData())
        val found = userId != null
        if (found) {
            userLocalDataSource.saveUser(user = user.copy(userId = userId))
        }
        return found
    }

    override suspend fun getUser(): User? {
        return userLocalDataSource.getUser()
    }

    override suspend fun checkUserLoggedIn(): Boolean {
        return userLocalDataSource.getUser() != null
    }

    override suspend fun logoutUser() {
        userLocalDataSource.clearUser()
    }
}