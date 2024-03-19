package com.rxxskh.domain.user.usecase

import com.rxxskh.utils.NameTakenException
import com.rxxskh.utils.Resource
import com.rxxskh.domain.user.model.User
import com.rxxskh.domain.user.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(user: User): Flow<Resource<Boolean>> = flow {
        try {
            val result = userRepository.registerUser(user)
            emit(Resource.Success(data = result))
        } catch (e: NameTakenException) {
            emit(Resource.Error(message = "Данный логин занят"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Ошибка подключения сети"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Неизвестная ошибка"))
        }
    }.flowOn(Dispatchers.IO)
}