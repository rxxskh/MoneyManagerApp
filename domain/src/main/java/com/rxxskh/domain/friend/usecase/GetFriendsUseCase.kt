package com.rxxskh.domain.friend.usecase

import com.rxxskh.utils.Resource
import com.rxxskh.domain.user.model.User
import com.rxxskh.domain.friend.repository.FriendRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFriendsUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {

    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try {
            val result = friendRepository.getFriends()
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            emit(Resource.Error("Неизвестная ошибка"))
        }
    }.flowOn(Dispatchers.IO)
}