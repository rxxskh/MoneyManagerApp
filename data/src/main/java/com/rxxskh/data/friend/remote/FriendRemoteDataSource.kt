package com.rxxskh.data.friend.remote

import com.rxxskh.utils.FirebaseReferencesProvider
import com.rxxskh.utils.FriendNotFoundException
import com.rxxskh.data.friend.remote.model.UserFriendData
import com.rxxskh.data.user.remote.model.UserData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FriendRemoteDataSource @Inject constructor() {

    suspend fun getFriends(userId: String): List<UserData> {
        val friendIds = suspendCoroutine { continuation ->
            val result = mutableListOf<String>()
            FirebaseReferencesProvider.USER_FRIENDS_REF.get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        for (children in snapshot.children) {
                            val data = children.getValue(UserFriendData::class.java)
                            if (data != null) {
                                if (data.user_id == userId) {
                                    if (data.friend_id != null) {
                                        result.add(data.friend_id)
                                    }
                                }
                            }
                        }
                    }
                    continuation.resume(result)
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
        return friendIds.mapNotNull { getUserById(it) }
    }

    suspend fun addFriend(userId: String, friendName: String): UserData? {
        val friendId = getFriendIdByName(friendName = friendName)
        val friendData = getUserById(userId = friendId)
        if (friendId.isNotEmpty()) {
            FirebaseReferencesProvider.USER_FRIENDS_REF.child(FirebaseReferencesProvider.USER_FRIENDS_REF.push().key!!)
                .setValue(
                    UserFriendData(
                        user_id = userId,
                        friend_id = friendId
                    )
                )
            FirebaseReferencesProvider.USER_FRIENDS_REF.child(FirebaseReferencesProvider.USER_FRIENDS_REF.push().key!!)
                .setValue(
                    UserFriendData(
                        user_id = friendId,
                        friend_id = userId
                    )
                )

        } else {
            throw FriendNotFoundException()
        }
        return friendData
    }

    suspend fun deleteFriend(userId: String, friendName: String): UserData? {
        val friendId = getFriendIdByName(friendName = friendName)
        val friendData = getUserById(userId = friendId)
        val deleteKeys =
            suspendCoroutine { continuation ->
                val result = mutableListOf<String>()
                FirebaseReferencesProvider.USER_FRIENDS_REF.get()
                    .addOnSuccessListener { snapshot ->
                        if (snapshot.exists()) {
                            for (children in snapshot.children) {
                                val data = children.getValue(UserFriendData::class.java)
                                if (data != null) {
                                    if ((data.user_id == userId && data.friend_id == friendId) || (data.user_id == friendId && data.friend_id == userId)) {
                                        result.add(children.key!!)
                                    }
                                }
                            }
                        }
                        continuation.resume(result)
                    }.addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            }
        deleteKeys.forEach {
            FirebaseReferencesProvider.USER_FRIENDS_REF.child(it).removeValue().await()
        }
        return friendData
    }

    private suspend fun getFriendIdByName(friendName: String): String =
        suspendCoroutine { continuation ->
            var result = ""
            FirebaseReferencesProvider.USERS_REF.get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        for (children in snapshot.children) {
                            val data = children.getValue(UserData::class.java)
                            if (data != null) {
                                if (data.user_login == friendName) {
                                    result = data.user_id ?: ""
                                }
                            }
                        }
                    }
                    continuation.resume(result)
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

    private suspend fun getUserById(userId: String): UserData? =
        suspendCoroutine { continuation ->
            FirebaseReferencesProvider.USERS_REF.child(userId).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.getValue(UserData::class.java))
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
}