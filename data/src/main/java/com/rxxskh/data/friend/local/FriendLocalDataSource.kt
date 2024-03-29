package com.rxxskh.data.friend.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.rxxskh.utils.SharedPreferenceProvider
import com.rxxskh.data.user.remote.model.UserRemoteData
import javax.inject.Inject

data class ListFriends(
    val list: List<UserRemoteData>
)

class FriendLocalDataSource @Inject constructor(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {

    suspend fun save(friends: List<UserRemoteData>) {
        val listFriends = ListFriends(list = friends)
        with(sharedPreferences.edit()) {
            val json: String = gson.toJson(listFriends)
            putString(SharedPreferenceProvider.FRIENDS_DATA_PREFS_KEY, json)
            apply()
        }
    }

    suspend fun get(): List<UserRemoteData> {
        val json: String? = sharedPreferences.getString(SharedPreferenceProvider.FRIENDS_DATA_PREFS_KEY, null)
        return if (json != null) {
            gson.fromJson(json, ListFriends::class.java).list
        } else {
            emptyList()
        }
    }
}