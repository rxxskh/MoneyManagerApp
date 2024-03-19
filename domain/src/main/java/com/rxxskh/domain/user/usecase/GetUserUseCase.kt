package com.rxxskh.domain.user.usecase

import com.rxxskh.domain.user.model.User
import com.rxxskh.domain.user.repository.UserRepository
import com.rxxskh.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<Resource<User?>> = flow {
        try {
            val result = userRepository.getUser()
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Неизвестная ошибка"))
        }
    }.flowOn(Dispatchers.IO)
}