package com.rxxskh.data.friend.repository

import com.rxxskh.data.friend.local.FriendLocalDataSource
import com.rxxskh.data.user.local.UserLocalDataSource
import com.rxxskh.data.friend.remote.FriendRemoteDataSource
import com.rxxskh.data.user.remote.model.toUser
import com.rxxskh.domain.user.model.User
import com.rxxskh.domain.friend.repository.FriendRepository
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendRemoteDataSource: FriendRemoteDataSource,
    private val friendLocalDataSource: FriendLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : FriendRepository {

    override suspend fun loadData() {
        val userId = userLocalDataSource.get().user_id!!
        friendLocalDataSource.save(friends = friendRemoteDataSource.getFriends(userId = userId))
    }

    override suspend fun getFriends(): List<User> {
        return friendLocalDataSource.get().map { it.toUser() }
    }

    override suspend fun addFriend(friendName: String) {
        val userId = userLocalDataSource.get().user_id!!
        val friends = friendRemoteDataSource.getFriends(userId = userId).toMutableList()
        val newFriend =
            friendRemoteDataSource.addFriend(userId = userId, friendName = friendName)
        if (newFriend != null) friends.add(newFriend)
        friendLocalDataSource.save(friends = friends)
    }

    override suspend fun deleteFriend(friendName: String) {
        val userId = userLocalDataSource.get().user_id!!
        val friends = friendRemoteDataSource.getFriends(userId = userId).toMutableList()
        val newFriend =
            friendRemoteDataSource.deleteFriend(userId = userId, friendName = friendName)
        if (newFriend != null) friends.remove(newFriend)
        friendLocalDataSource.save(friends = friends)
    }
}