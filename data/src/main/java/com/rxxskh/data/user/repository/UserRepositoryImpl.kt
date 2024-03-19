package com.rxxskh.data.user.repository

import com.rxxskh.data.user.local.UserLocalDataSource
import com.rxxskh.data.user.remote.UserRemoteDataSource
import com.rxxskh.data.user.remote.model.toUser
import com.rxxskh.data.user.remote.model.toUserData
import com.rxxskh.domain.user.model.User
import com.rxxskh.domain.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun registerUser(user: User): Boolean {
        return userRemoteDataSource.saveUser(user.toUserData())
    }

    override suspend fun loginUser(user: User): Boolean {
        val userData = user.toUserData()
        val result = userRemoteDataSource.checkUser(userData)
        val isNotEmpty = result.user_id.isNullOrEmpty().not()
        if (isNotEmpty) {
            userLocalDataSource.saveUser(userData)
        }
        return isNotEmpty
    }

    override suspend fun getUser(): User {
        return userLocalDataSource.get().toUser()
    }

    override suspend fun checkUserLoggedIn(): Boolean {
        return userLocalDataSource.get().user_id.isNullOrEmpty()
    }
}