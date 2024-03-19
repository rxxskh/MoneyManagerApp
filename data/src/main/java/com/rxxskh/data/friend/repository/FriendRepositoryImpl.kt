package com.rxxskh.data.friend.repository

import com.rxxskh.data.friend.local.FriendLocalDataSource
import com.rxxskh.data.friend.remote.FriendRemoteDataSource
import com.rxxskh.data.user.local.UserLocalDataSource
import com.rxxskh.data.user.remote.model.toUser
import com.rxxskh.domain.friend.repository.FriendRepository
import com.rxxskh.domain.user.model.User
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendRemoteDataSource: FriendRemoteDataSource,
    private val friendLocalDataSource: FriendLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : FriendRepository {

    override suspend fun loadData() {
        val userId = userLocalDataSource.getUser()!!.userId!!
        friendLocalDataSource.save(friends = friendRemoteDataSource.getFriends(userId = userId))
    }

    override suspend fun getFriends(): List<User> {
        return friendLocalDataSource.get().map { it.toUser() }
    }

    override suspend fun addFriend(friendName: String) {
        val user = userLocalDataSource.getUser()!!
        if (user.userLogin != friendName) {
            val userId = userLocalDataSource.getUser()!!.userId!!
            val friends = friendRemoteDataSource.getFriends(userId = userId).toMutableList()
            val newFriend =
                friendRemoteDataSource.addFriend(userId = userId, friendName = friendName)
            if (newFriend != null) friends.add(newFriend)
            friendLocalDataSource.save(friends = friends)
        } else {
            throw Exception()
        }
    }

    override suspend fun deleteFriend(friendName: String) {
        val userId = userLocalDataSource.getUser()!!.userId!!
        val friends = friendRemoteDataSource.getFriends(userId = userId).toMutableList()
        val newFriend =
            friendRemoteDataSource.deleteFriend(userId = userId, friendName = friendName)
        if (newFriend != null) friends.remove(newFriend)
        friendLocalDataSource.save(friends = friends)
    }
}