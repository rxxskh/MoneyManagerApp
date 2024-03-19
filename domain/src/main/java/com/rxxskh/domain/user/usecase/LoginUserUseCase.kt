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

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(user: User): Flow<Resource<Boolean>> = flow {
        try {
            val result = userRepository.loginUser(user)
            emit(Resource.Success(data = result))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Ошибка подключения сети"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Неизвестная ошибка"))
        }
    }.flowOn(Dispatchers.IO)
}