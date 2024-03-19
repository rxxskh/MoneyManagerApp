package com.rxxskh.domain.friend.repository

import com.rxxskh.domain.user.model.User

interface FriendRepository {

    suspend fun loadData()
    suspend fun getFriends(): List<User>
    suspend fun addFriend(friendName: String)
    suspend fun deleteFriend(friendName: String)
}