package com.rxxskh.domain.user.usecase

import com.rxxskh.domain.user.model.User
import com.rxxskh.domain.user.repository.UserRepository
import com.rxxskh.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<Resource<Unit>> = flow {
        try {
            val result = userRepository.logoutUser()
            emit(Resource.Success(data = result))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Ошибка подключения сети"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Неизвестная ошибка"))
        }
    }.flowOn(Dispatchers.IO)
}